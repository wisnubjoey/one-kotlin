package com.bjoey.app.ui.settings.menu

import com.bjoey.app.data.AppDatabase
import com.bjoey.app.data.MenuCategoryEntity
import com.bjoey.app.data.MenuDao
import com.bjoey.app.data.MenuItemEntity
import com.bjoey.app.data.MenuItemWithCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MenuViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_loadsSeededCategoriesAndItems() = runTest {
        val fakeDao = FakeMenuDao(
            initialCategories = AppDatabase.defaultMenuCategories(),
            initialItems = AppDatabase.defaultMenuItems()
        )
        val viewModel = MenuViewModel(fakeDao, testDispatcher)
        val itemsJob = backgroundScope.launch(testDispatcher) { viewModel.menuItems.collect {} }
        val categoriesJob = backgroundScope.launch(testDispatcher) { viewModel.categories.collect {} }

        assertEquals(3, viewModel.categories.value.size)
        assertEquals(8, viewModel.menuItems.value.size)

        itemsJob.cancel()
        categoriesJob.cancel()
    }

    @Test
    fun categoryAndSearchFilters_filterMenuItems() = runTest {
        val fakeDao = FakeMenuDao(
            initialCategories = AppDatabase.defaultMenuCategories(),
            initialItems = AppDatabase.defaultMenuItems()
        )
        val viewModel = MenuViewModel(fakeDao, testDispatcher)
        val filteredJob = backgroundScope.launch(testDispatcher) { viewModel.filteredMenuItems.collect {} }

        viewModel.onCategorySelected("cat-pastry")
        assertEquals(
            listOf("Butter Croissant", "Pain au Chocolat"),
            viewModel.filteredMenuItems.value.map { it.title }
        )

        viewModel.onSearchQueryChange("croissant")
        assertEquals(
            listOf("Butter Croissant"),
            viewModel.filteredMenuItems.value.map { it.title }
        )

        viewModel.onCategorySelected(null)
        viewModel.onSearchQueryChange("matcha")
        assertEquals(
            listOf("Iced Matcha Latte"),
            viewModel.filteredMenuItems.value.map { it.title }
        )

        viewModel.onSearchQueryChange("")
        assertEquals(8, viewModel.filteredMenuItems.value.size)

        filteredJob.cancel()
    }

    @Test
    fun addCategoryAndMenuCrud_updatesRoomDaoState() = runTest {
        val fakeDao = FakeMenuDao(
            initialCategories = AppDatabase.defaultMenuCategories(),
            initialItems = AppDatabase.defaultMenuItems()
        )
        val viewModel = MenuViewModel(fakeDao, testDispatcher)
        val itemsJob = backgroundScope.launch(testDispatcher) { viewModel.menuItems.collect {} }
        val categoriesJob = backgroundScope.launch(testDispatcher) { viewModel.categories.collect {} }

        var createdCategory: MenuCategory? = null
        viewModel.addCategory("Snacks") { createdCategory = it }
        assertNotNull(createdCategory)
        assertEquals(4, viewModel.categories.value.size)

        viewModel.addMenuItem(
            title = "French Fries",
            price = 22_000L,
            categoryId = createdCategory!!.id,
            imageUri = "content://media/fries"
        )
        assertEquals(9, viewModel.menuItems.value.size)
        val added = viewModel.menuItems.value.first { it.title == "French Fries" }
        assertEquals("Snacks", added.categoryName)
        assertEquals(22_000L, added.price)
        assertEquals("content://media/fries", added.imageUri)

        viewModel.updateMenuItem(
            id = added.id,
            title = "Truffle Fries",
            price = 32_000L,
            categoryId = createdCategory!!.id,
            imageUri = null
        )
        val updated = viewModel.getMenuItemById(added.id)
        assertNotNull(updated)
        assertEquals("Truffle Fries", updated?.title)
        assertEquals(32_000L, updated?.price)
        assertNull(updated?.imageUri)

        viewModel.deleteMenuItem(added.id)
        assertEquals(8, viewModel.menuItems.value.size)
        assertNull(viewModel.getMenuItemById(added.id))

        itemsJob.cancel()
        categoriesJob.cancel()
    }
}

private class FakeMenuDao(
    initialCategories: List<MenuCategoryEntity> = emptyList(),
    initialItems: List<MenuItemEntity> = emptyList()
) : MenuDao {
    private val categoriesFlow = MutableStateFlow(initialCategories)
    private val itemsFlow = MutableStateFlow(initialItems)

    override fun observeCategories(): Flow<List<MenuCategoryEntity>> = categoriesFlow

    override suspend fun countCategories(): Int = categoriesFlow.value.size

    override suspend fun insertCategory(category: MenuCategoryEntity) {
        categoriesFlow.value = (categoriesFlow.value.filterNot { it.id == category.id } + category)
            .sortedBy { it.name }
    }

    override suspend fun insertCategories(categories: List<MenuCategoryEntity>) {
        categories.forEach { insertCategory(it) }
    }

    override suspend fun deleteCategoryById(categoryId: String) {
        categoriesFlow.value = categoriesFlow.value.filterNot { it.id == categoryId }
    }

    override fun observeMenuItemsWithCategory(): Flow<List<MenuItemWithCategory>> {
        return combine(itemsFlow, categoriesFlow) { items, categories ->
            val categoryById = categories.associateBy { it.id }
            items.map { item ->
                MenuItemWithCategory(
                    item = item,
                    category = categoryById[item.categoryId]
                )
            }
        }
    }

    override suspend fun getMenuItemById(id: String): MenuItemEntity? =
        itemsFlow.value.firstOrNull { it.id == id }

    override suspend fun countMenuItems(): Int = itemsFlow.value.size

    override suspend fun insertMenuItem(item: MenuItemEntity) {
        itemsFlow.value = listOf(item) + itemsFlow.value.filterNot { it.id == item.id }
    }

    override suspend fun insertMenuItems(items: List<MenuItemEntity>) {
        itemsFlow.value = items
    }

    override suspend fun updateMenuItem(item: MenuItemEntity) {
        itemsFlow.value = itemsFlow.value.map { if (it.id == item.id) item else it }
    }

    override suspend fun deleteMenuItemById(id: String) {
        itemsFlow.value = itemsFlow.value.filterNot { it.id == id }
    }
}
