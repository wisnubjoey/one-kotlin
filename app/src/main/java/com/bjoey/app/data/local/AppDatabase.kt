package com.bjoey.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bjoey.app.data.local.dao.MenuDao
import com.bjoey.app.data.local.dao.ShiftDao
import com.bjoey.app.data.local.dao.TableDao
import com.bjoey.app.data.local.entity.DiningTableEntity
import com.bjoey.app.data.local.entity.MenuCategoryEntity
import com.bjoey.app.data.local.entity.MenuItemEntity
import com.bjoey.app.data.local.entity.ShiftEntity
import com.bjoey.app.data.local.entity.ShiftSessionEntity
import com.bjoey.app.data.local.entity.StartedShiftSession
import com.bjoey.app.data.local.migration.DatabaseMigrations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Database(
    entities = [
        ShiftEntity::class,
        ShiftSessionEntity::class,
        MenuCategoryEntity::class,
        MenuItemEntity::class,
        DiningTableEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shiftDao(): ShiftDao
    abstract fun menuDao(): MenuDao
    abstract fun tableDao(): TableDao

    suspend fun verifyPinAndStartSession(pin: String): StartedShiftSession? = withContext(Dispatchers.IO) {
        val dao = shiftDao()
        if (dao.countShifts() == 0) {
            dao.insertShifts(DatabaseSeeds.defaultShifts())
        }
        val matchedShift = dao.findActiveShiftByPin(pin) ?: return@withContext null
        dao.closeOpenSessions()
        val session = ShiftSessionEntity(
            id = UUID.randomUUID().toString(),
            shiftId = matchedShift.id,
            openedAt = System.currentTimeMillis(),
            closedAt = null,
            isSynced = false
        )
        dao.insertSession(session)
        StartedShiftSession(shift = matchedShift, session = session)
    }

    suspend fun markSessionAsSynced(sessionId: String) = withContext(Dispatchers.IO) {
        shiftDao().markSessionAsSynced(sessionId)
    }

    suspend fun lockActiveSessions() = withContext(Dispatchers.IO) {
        shiftDao().closeOpenSessions(System.currentTimeMillis())
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val appContext = context.applicationContext
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "panda_pos_prototype.db"
                )
                    .addMigrations(*DatabaseMigrations.ALL)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getInstance(appContext)
                                database.shiftDao().insertShifts(DatabaseSeeds.defaultShifts())
                                val menuDao = database.menuDao()
                                if (menuDao.countCategories() == 0) {
                                    menuDao.insertCategories(DatabaseSeeds.defaultMenuCategories())
                                }
                                if (menuDao.countMenuItems() == 0) {
                                    menuDao.insertMenuItems(DatabaseSeeds.defaultMenuItems())
                                }
                                val tableDao = database.tableDao()
                                if (tableDao.countTables() == 0) {
                                    tableDao.insertTables(DatabaseSeeds.defaultTables())
                                }
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
