package com.bjoey.app.ui.settings.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bjoey.app.data.MenuCategoryEntity
import com.bjoey.app.data.MenuDao
import com.bjoey.app.data.MenuItemEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Room-backed ViewModel managing POS menu categories, menu items, and live filtering.
 */
class MenuViewModel(
    private val menuDao: MenuDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val categories: StateFlow<List<MenuCategory>> = menuDao.observeCategories()
        .map { entities ->
            entities.map { MenuCategory(id = it.id, name = it.name) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    val menuItems: StateFlow<List<MenuItem>> = menuDao.observeMenuItemsWithCategory()
        .map { rows ->
            rows.map { row ->
                MenuItem(
                    id = row.item.id,
                    title = row.item.title,
                    price = row.item.price,
                    categoryId = row.item.categoryId,
                    categoryName = row.category?.name ?: "Tanpa Kategori",
                    imageUri = row.item.imageUri
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    val filteredMenuItems: StateFlow<List<MenuItem>> = combine(
        menuItems,
        _searchQuery,
        _selectedCategoryId
    ) { items, query, categoryId ->
        val trimmed = query.trim()
        items.filter { item ->
            val matchesCategory = categoryId == null || item.categoryId == categoryId
            val matchesQuery = trimmed.isEmpty() || item.title.contains(trimmed, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(categoryId: String?) {
        _selectedCategoryId.value = categoryId
    }

    fun getMenuItemById(id: String): MenuItem? {
        return menuItems.value.firstOrNull { it.id == id }
    }

    fun addCategory(name: String, onCreated: (MenuCategory) -> Unit = {}) {
        val cleanName = name.trim()
        if (cleanName.isEmpty()) return

        val existing = categories.value.firstOrNull { it.name.equals(cleanName, ignoreCase = true) }
        if (existing != null) {
            onCreated(existing)
            return
        }

        val entity = MenuCategoryEntity(
            id = UUID.randomUUID().toString(),
            name = cleanName,
            createdAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            withContext(ioDispatcher) {
                menuDao.insertCategory(entity)
            }
            onCreated(MenuCategory(id = entity.id, name = entity.name))
        }
    }

    fun addMenuItem(title: String, price: Long, categoryId: String, imageUri: String?) {
        val cleanTitle = title.trim()
        if (cleanTitle.isEmpty() || price <= 0L || categoryId.isBlank()) return

        val entity = MenuItemEntity(
            id = UUID.randomUUID().toString(),
            title = cleanTitle,
            price = price,
            categoryId = categoryId,
            imageUri = imageUri,
            updatedAt = System.currentTimeMillis()
        )
        viewModelScope.launch(ioDispatcher) {
            menuDao.insertMenuItem(entity)
        }
    }

    fun updateMenuItem(id: String, title: String, price: Long, categoryId: String, imageUri: String?) {
        val cleanTitle = title.trim()
        if (cleanTitle.isEmpty() || price <= 0L || categoryId.isBlank()) return

        val entity = MenuItemEntity(
            id = id,
            title = cleanTitle,
            price = price,
            categoryId = categoryId,
            imageUri = imageUri,
            updatedAt = System.currentTimeMillis()
        )
        viewModelScope.launch(ioDispatcher) {
            menuDao.updateMenuItem(entity)
        }
    }

    fun deleteMenuItem(id: String) {
        viewModelScope.launch(ioDispatcher) {
            menuDao.deleteMenuItemById(id)
        }
    }

    class Factory(private val menuDao: MenuDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                return MenuViewModel(menuDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
