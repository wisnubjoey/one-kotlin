package com.bjoey.app.ui.settings.table

import com.bjoey.app.data.local.DatabaseSeeds
import com.bjoey.app.data.local.dao.TableDao
import com.bjoey.app.data.local.entity.DiningTableEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
class TableViewModelTest {

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
    fun initialState_loadsSeededTables() = runTest {
        val fakeDao = FakeTableDao(
            initialTables = DatabaseSeeds.defaultTables()
        )
        val viewModel = TableViewModel(fakeDao, testDispatcher)
        val tablesJob = backgroundScope.launch(testDispatcher) { viewModel.tables.collect {} }

        assertEquals(6, viewModel.tables.value.size)
        assertEquals("Meja 01", viewModel.tables.value.first().name)

        tablesJob.cancel()
    }

    @Test
    fun searchFilter_filtersTablesByName() = runTest {
        val fakeDao = FakeTableDao(
            initialTables = DatabaseSeeds.defaultTables()
        )
        val viewModel = TableViewModel(fakeDao, testDispatcher)
        val filteredJob = backgroundScope.launch(testDispatcher) { viewModel.filteredTables.collect {} }

        viewModel.onSearchQueryChange("03")
        assertEquals(
            listOf("Meja 03"),
            viewModel.filteredTables.value.map { it.name }
        )

        viewModel.onSearchQueryChange("meja")
        assertEquals(6, viewModel.filteredTables.value.size)

        viewModel.onSearchQueryChange("tidak ada")
        assertEquals(0, viewModel.filteredTables.value.size)

        viewModel.onSearchQueryChange("")
        assertEquals(6, viewModel.filteredTables.value.size)

        filteredJob.cancel()
    }

    @Test
    fun tableCrud_updatesRoomDaoState() = runTest {
        val fakeDao = FakeTableDao(
            initialTables = DatabaseSeeds.defaultTables()
        )
        val viewModel = TableViewModel(fakeDao, testDispatcher)
        val tablesJob = backgroundScope.launch(testDispatcher) { viewModel.tables.collect {} }

        // Blank names should be ignored
        viewModel.addTable("   ")
        assertEquals(6, viewModel.tables.value.size)

        // Create
        viewModel.addTable("  Meja VIP  ")
        assertEquals(7, viewModel.tables.value.size)
        val added = viewModel.tables.value.firstOrNull { it.name == "Meja VIP" }
        assertNotNull(added)

        // Read & Update
        viewModel.updateTable(id = added!!.id, name = "  Meja Outdoor 1 ")
        val updated = viewModel.getTableById(added.id)
        assertNotNull(updated)
        assertEquals("Meja Outdoor 1", updated?.name)

        // Delete
        viewModel.deleteTable(added.id)
        assertEquals(6, viewModel.tables.value.size)
        assertNull(viewModel.getTableById(added.id))

        tablesJob.cancel()
    }
}

private class FakeTableDao(
    initialTables: List<DiningTableEntity> = emptyList()
) : TableDao {
    private val tablesFlow = MutableStateFlow(initialTables)

    override fun observeTables(): Flow<List<DiningTableEntity>> = tablesFlow

    override suspend fun getTableById(id: String): DiningTableEntity? =
        tablesFlow.value.firstOrNull { it.id == id }

    override suspend fun countTables(): Int = tablesFlow.value.size

    override suspend fun insertTable(table: DiningTableEntity) {
        tablesFlow.value = listOf(table) + tablesFlow.value.filterNot { it.id == table.id }
    }

    override suspend fun insertTables(tables: List<DiningTableEntity>) {
        tablesFlow.value = tables
    }

    override suspend fun updateTable(table: DiningTableEntity) {
        tablesFlow.value = tablesFlow.value.map { if (it.id == table.id) table else it }
    }

    override suspend fun deleteTableById(id: String) {
        tablesFlow.value = tablesFlow.value.filterNot { it.id == id }
    }
}
