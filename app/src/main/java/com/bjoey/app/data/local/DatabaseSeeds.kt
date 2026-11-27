package com.bjoey.app.data.local

import com.bjoey.app.data.local.entity.DiningTableEntity
import com.bjoey.app.data.local.entity.MenuCategoryEntity
import com.bjoey.app.data.local.entity.MenuItemEntity
import com.bjoey.app.data.local.entity.ShiftEntity

object DatabaseSeeds {
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

    fun defaultTables(): List<DiningTableEntity> = listOf(
        DiningTableEntity(id = "table-01", name = "Meja 01", updatedAt = 6L),
        DiningTableEntity(id = "table-02", name = "Meja 02", updatedAt = 5L),
        DiningTableEntity(id = "table-03", name = "Meja 03", updatedAt = 4L),
        DiningTableEntity(id = "table-04", name = "Meja 04", updatedAt = 3L),
        DiningTableEntity(id = "table-05", name = "Meja 05", updatedAt = 2L),
        DiningTableEntity(id = "table-06", name = "Meja 06", updatedAt = 1L)
    )
}
