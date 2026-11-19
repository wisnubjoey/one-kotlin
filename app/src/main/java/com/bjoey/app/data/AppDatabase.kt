package com.bjoey.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    val closedAt: Long? = null,
    val isSynced: Boolean = false
)

data class StartedShiftSession(
    val shift: ShiftEntity,
    val session: ShiftSessionEntity
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

    @Query("UPDATE shift_sessions SET isSynced = 1 WHERE id = :sessionId")
    suspend fun markSessionAsSynced(sessionId: String)

    @Query("UPDATE shift_sessions SET closedAt = :closedAt WHERE closedAt IS NULL")
    suspend fun closeOpenSessions(closedAt: Long = System.currentTimeMillis())
}

@Entity(tableName = "menu_categories")
data class MenuCategoryEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "menu_items",
    foreignKeys = [
        ForeignKey(
            entity = MenuCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class MenuItemEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val price: Long,
    val categoryId: String,
    val imageUri: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

data class MenuItemWithCategory(
    @Embedded val item: MenuItemEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: MenuCategoryEntity?
)

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_categories ORDER BY name ASC")
    fun observeCategories(): Flow<List<MenuCategoryEntity>>

    @Query("SELECT COUNT(*) FROM menu_categories")
    suspend fun countCategories(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: MenuCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<MenuCategoryEntity>)

    @Query("DELETE FROM menu_categories WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: String)

    @Transaction
    @Query("SELECT * FROM menu_items ORDER BY updatedAt DESC, title ASC")
    fun observeMenuItemsWithCategory(): Flow<List<MenuItemWithCategory>>

    @Query("SELECT * FROM menu_items WHERE id = :id LIMIT 1")
    suspend fun getMenuItemById(id: String): MenuItemEntity?

    @Query("SELECT COUNT(*) FROM menu_items")
    suspend fun countMenuItems(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItem(item: MenuItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(items: List<MenuItemEntity>)

    @Update
    suspend fun updateMenuItem(item: MenuItemEntity)

    @Query("DELETE FROM menu_items WHERE id = :id")
    suspend fun deleteMenuItemById(id: String)
}

@Database(
    entities = [
        ShiftEntity::class,
        ShiftSessionEntity::class,
        MenuCategoryEntity::class,
        MenuItemEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shiftDao(): ShiftDao
    abstract fun menuDao(): MenuDao

    suspend fun verifyPinAndStartSession(pin: String): StartedShiftSession? = withContext(Dispatchers.IO) {
        val dao = shiftDao()
        if (dao.countShifts() == 0) {
            dao.insertShifts(defaultShifts())
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

        private const val CAT_COFFEE_ID = "cat-coffee"
        private const val CAT_NON_COFFEE_ID = "cat-non-coffee"
        private const val CAT_PASTRY_ID = "cat-pastry"

        fun defaultShifts(): List<ShiftEntity> = listOf(
            ShiftEntity(id = 1, name = "Shift Pagi", pin = "111111", isActive = true),
            ShiftEntity(id = 2, name = "Shift Malam", pin = "222222", isActive = true)
        )

        fun defaultMenuCategories(): List<MenuCategoryEntity> = listOf(
            MenuCategoryEntity(id = CAT_COFFEE_ID, name = "Coffee", createdAt = 1L),
            MenuCategoryEntity(id = CAT_NON_COFFEE_ID, name = "Non-Coffee", createdAt = 2L),
            MenuCategoryEntity(id = CAT_PASTRY_ID, name = "Pastry", createdAt = 3L)
        )

        fun defaultMenuItems(): List<MenuItemEntity> = listOf(
            MenuItemEntity(id = "menu-01", title = "Iced Americano", price = 25_000L, categoryId = CAT_COFFEE_ID, updatedAt = 8L),
            MenuItemEntity(id = "menu-02", title = "Caffe Latte", price = 28_000L, categoryId = CAT_COFFEE_ID, updatedAt = 7L),
            MenuItemEntity(id = "menu-03", title = "Iced Matcha Latte", price = 32_000L, categoryId = CAT_NON_COFFEE_ID, updatedAt = 6L),
            MenuItemEntity(id = "menu-04", title = "Caramel Macchiato", price = 35_000L, categoryId = CAT_COFFEE_ID, updatedAt = 5L),
            MenuItemEntity(id = "menu-05", title = "Butter Croissant", price = 24_000L, categoryId = CAT_PASTRY_ID, updatedAt = 4L),
            MenuItemEntity(id = "menu-06", title = "Pain au Chocolat", price = 26_000L, categoryId = CAT_PASTRY_ID, updatedAt = 3L),
            MenuItemEntity(id = "menu-07", title = "Cold Brew", price = 30_000L, categoryId = CAT_COFFEE_ID, updatedAt = 2L),
            MenuItemEntity(id = "menu-08", title = "Espresso", price = 20_000L, categoryId = CAT_COFFEE_ID, updatedAt = 1L)
        )

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `menu_categories` (
                        `id` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `menu_items` (
                        `id` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `price` INTEGER NOT NULL,
                        `categoryId` TEXT NOT NULL,
                        `imageUri` TEXT,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`),
                        FOREIGN KEY(`categoryId`) REFERENCES `menu_categories`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_menu_items_categoryId` ON `menu_items` (`categoryId`)"
                )

                defaultMenuCategories().forEach { category ->
                    db.execSQL(
                        "INSERT OR IGNORE INTO `menu_categories` (`id`, `name`, `createdAt`) VALUES (?, ?, ?)",
                        arrayOf(category.id, category.name, category.createdAt)
                    )
                }
                defaultMenuItems().forEach { item ->
                    db.execSQL(
                        "INSERT OR IGNORE INTO `menu_items` (`id`, `title`, `price`, `categoryId`, `imageUri`, `updatedAt`) VALUES (?, ?, ?, ?, ?, ?)",
                        arrayOf(item.id, item.title, item.price, item.categoryId, item.imageUri, item.updatedAt)
                    )
                }
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val appContext = context.applicationContext
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "panda_pos_prototype.db"
                )
                    .addMigrations(MIGRATION_2_3)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getInstance(appContext)
                                database.shiftDao().insertShifts(defaultShifts())
                                val menuDao = database.menuDao()
                                if (menuDao.countCategories() == 0) {
                                    menuDao.insertCategories(defaultMenuCategories())
                                }
                                if (menuDao.countMenuItems() == 0) {
                                    menuDao.insertMenuItems(defaultMenuItems())
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
