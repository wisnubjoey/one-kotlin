package com.bjoey.app.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bjoey.app.data.local.DatabaseSeeds

object DatabaseMigrations {

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

            DatabaseSeeds.defaultMenuCategories().forEach { category ->
                db.execSQL(
                    "INSERT OR IGNORE INTO `menu_categories` (`id`, `name`, `createdAt`) VALUES (?, ?, ?)",
                    arrayOf(category.id, category.name, category.createdAt)
                )
            }
            DatabaseSeeds.defaultMenuItems().forEach { item ->
                db.execSQL(
                    "INSERT OR IGNORE INTO `menu_items` (`id`, `title`, `price`, `categoryId`, `imageUri`, `updatedAt`) VALUES (?, ?, ?, ?, ?, ?)",
                    arrayOf(item.id, item.title, item.price, item.categoryId, item.imageUri, item.updatedAt)
                )
            }
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `dining_tables` (
                    `id` TEXT NOT NULL,
                    `name` TEXT NOT NULL,
                    `updatedAt` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )

            DatabaseSeeds.defaultTables().forEach { table ->
                db.execSQL(
                    "INSERT OR IGNORE INTO `dining_tables` (`id`, `name`, `updatedAt`) VALUES (?, ?, ?)",
                    arrayOf(table.id, table.name, table.updatedAt)
                )
            }
        }
    }

    val ALL: Array<Migration> = arrayOf(
        MIGRATION_2_3,
        MIGRATION_3_4
    )
}
