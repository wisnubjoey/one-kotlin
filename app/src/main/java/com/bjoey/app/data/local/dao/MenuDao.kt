package com.bjoey.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bjoey.app.data.local.entity.MenuCategoryEntity
import com.bjoey.app.data.local.entity.MenuItemEntity
import com.bjoey.app.data.local.entity.MenuItemWithCategory
import kotlinx.coroutines.flow.Flow

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
