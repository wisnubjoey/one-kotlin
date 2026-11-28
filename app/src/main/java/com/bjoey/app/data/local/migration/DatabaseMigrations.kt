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

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `dining_orders` (
                    `id` TEXT NOT NULL,
                    `orderNumber` INTEGER NOT NULL,
                    `tableId` TEXT NOT NULL,
                    `tableName` TEXT NOT NULL,
                    `guestCount` INTEGER NOT NULL,
                    `shiftSessionId` TEXT NOT NULL,
                    `status` TEXT NOT NULL,
                    `paymentMethod` TEXT,
                    `subtotal` INTEGER NOT NULL,
                    `taxAmount` INTEGER NOT NULL,
                    `grandTotal` INTEGER NOT NULL,
                    `cashReceived` INTEGER,
                    `changeAmount` INTEGER,
                    `createdAt` INTEGER NOT NULL,
                    `updatedAt` INTEGER NOT NULL,
                    `closedAt` INTEGER,
                    PRIMARY KEY(`id`)
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_dining_orders_tableId` ON `dining_orders` (`tableId`)"
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_dining_orders_status` ON `dining_orders` (`status`)"
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `dining_order_items` (
                    `id` TEXT NOT NULL,
                    `orderId` TEXT NOT NULL,
                    `menuItemId` TEXT NOT NULL,
                    `itemTitle` TEXT NOT NULL,
                    `unitPrice` INTEGER NOT NULL,
                    `quantity` INTEGER NOT NULL,
                    `categoryName` TEXT NOT NULL,
                    PRIMARY KEY(`id`),
                    FOREIGN KEY(`orderId`) REFERENCES `dining_orders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                )
                """.trimIndent()
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_dining_order_items_orderId` ON `dining_order_items` (`orderId`)"
            )
        }
    }

    val ALL: Array<Migration> = arrayOf(
        MIGRATION_2_3,
        MIGRATION_3_4,
        MIGRATION_4_5
    )
}
