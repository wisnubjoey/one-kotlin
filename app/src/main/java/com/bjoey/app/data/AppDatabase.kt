package com.bjoey.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val pin: String,
    val isActive: Boolean = true
)

@Entity(tableName = "shift_sessions")
data class ShiftSessionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val shiftId: Int,
    val openedAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null
)

@Dao
interface ShiftDao {
    @Query("SELECT COUNT(*) FROM shifts")
    suspend fun countShifts(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShifts(shifts: List<ShiftEntity>)

    @Query("SELECT * FROM shifts WHERE pin = :pin AND isActive = 1 LIMIT 1")
    suspend fun findActiveShiftByPin(pin: String): ShiftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ShiftSessionEntity)

    @Query("UPDATE shift_sessions SET closedAt = :closedAt WHERE closedAt IS NULL")
    suspend fun closeOpenSessions(closedAt: Long = System.currentTimeMillis())
}

@Database(
    entities = [ShiftEntity::class, ShiftSessionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shiftDao(): ShiftDao

    suspend fun verifyPinAndStartSession(pin: String): ShiftEntity? = withContext(Dispatchers.IO) {
        val dao = shiftDao()
        if (dao.countShifts() == 0) {
            dao.insertShifts(defaultShifts())
        }
        val matchedShift = dao.findActiveShiftByPin(pin)
        if (matchedShift != null) {
            dao.closeOpenSessions()
            dao.insertSession(
                ShiftSessionEntity(
                    id = UUID.randomUUID().toString(),
                    shiftId = matchedShift.id,
                    openedAt = System.currentTimeMillis(),
                    closedAt = null
                )
            )
        }
        matchedShift
    }

    suspend fun lockActiveSessions() = withContext(Dispatchers.IO) {
        shiftDao().closeOpenSessions(System.currentTimeMillis())
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun defaultShifts(): List<ShiftEntity> = listOf(
            ShiftEntity(id = 1, name = "Shift Pagi", pin = "111111", isActive = true),
            ShiftEntity(id = 2, name = "Shift Malam", pin = "222222", isActive = true)
        )

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val appContext = context.applicationContext
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "panda_pos_prototype.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(appContext).shiftDao().insertShifts(defaultShifts())
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

