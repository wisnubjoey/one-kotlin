package com.bjoey.app.ui.dashboard

import com.bjoey.app.data.local.DatabaseSeeds
import com.bjoey.app.data.local.dao.MenuDao
import com.bjoey.app.data.local.dao.OrderDao
import com.bjoey.app.data.local.dao.TableDao
import com.bjoey.app.data.local.entity.DiningOrderEntity
import com.bjoey.app.data.local.entity.DiningOrderItemEntity
import com.bjoey.app.data.local.entity.DiningOrderWithItems
import com.bjoey.app.data.local.entity.DiningTableEntity
import com.bjoey.app.data.local.entity.MenuCategoryEntity
import com.bjoey.app.data.local.entity.MenuItemEntity
import com.bjoey.app.data.local.entity.MenuItemWithCategory
import com.bjoey.app.data.local.entity.OrderStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): Triple<DashboardViewModel, FakeOrderDao, FakeMenuDao> {
        val tableDao = FakeTableDao(DatabaseSeeds.defaultTables())
        val menuDao = FakeMenuDao(
            initialCategories = DatabaseSeeds.defaultMenuCategories(),
            initialItems = DatabaseSeeds.defaultMenuItems()
        )
        val orderDao = FakeOrderDao()
        val viewModel = DashboardViewModel(
            tableDao = tableDao,
            menuDao = menuDao,
            orderDao = orderDao,
            ioDispatcher = testDispatcher
        )
        return Triple(viewModel, orderDao, menuDao)
    }

    @Test
    fun dineInNewOrder_opensGuestDialog_createsOpenBill_andTurnsTableOccupied() = runTest {
        val (vm, orderDao, _) = createViewModel()
        val jobs = listOf(
            backgroundScope.launch(testDispatcher) { vm.tableSlots.collect {} },
            backgroundScope.launch(testDispatcher) { vm.filteredTableSlots.collect {} },
            backgroundScope.launch(testDispatcher) { vm.occupiedTableSlots.collect {} },
            backgroundScope.launch(testDispatcher) { vm.menuItems.collect {} },
            backgroundScope.launch(testDispatcher) { vm.filteredMenuItems.collect {} }
        )

        assertTrue(vm.stage.value is DashboardStage.TableGrid)
        assertEquals(6, vm.tableSlots.value.size)
        assertTrue(vm.tableSlots.value.none { it.isOccupied })

        // Tap available table
        val table1 = vm.tableSlots.value.first { it.id == "table-01" }
        vm.onTableClicked(table1)
        assertEquals("table-01", vm.pendingGuestCountTable.value?.id)

        // Confirm guest count -> enters MenuOrder
        vm.confirmGuestCountAndEnterMenu(guestCount = 4)
        assertNull(vm.pendingGuestCountTable.value)
        val menuStage = vm.stage.value as DashboardStage.MenuOrder
        assertEquals("table-01", menuStage.tableId)
        assertEquals(4, menuStage.guestCount)
        assertFalse(menuStage.isExistingOrder)

        // Add menu item (Iced Americano: 25,000 * 2 = 50,000 + 11% tax 5,500 = 55,500)
        val americano = vm.menuItems.value.first { it.id == "menu-01" }
        vm.addMenuItemToCart(americano)
        vm.addMenuItemToCart(americano)
        val totals = calculateOrderTotals(vm.draftCart.value.values)
        assertEquals(2, totals.totalQty)
        assertEquals(50_000L, totals.subtotal)
        assertEquals(5_500L, totals.taxAmount)
        assertEquals(55_500L, totals.grandTotal)

        // Submit ORDER -> returns to TableGrid and table turns occupied (orange)
        vm.submitOrder(shiftSessionId = "session-1")
        assertTrue(vm.stage.value is DashboardStage.TableGrid)
        assertTrue(vm.draftCart.value.isEmpty())

        val updatedTable1 = vm.tableSlots.value.first { it.id == "table-01" }
        assertTrue(updatedTable1.isOccupied)
        assertEquals(1, updatedTable1.activeOrder?.order?.orderNumber)
        assertEquals(4, updatedTable1.activeOrder?.order?.guestCount)
        assertEquals(55_500L, updatedTable1.activeOrder?.order?.grandTotal)
        assertEquals(1, orderDao.allOrders().size)

        jobs.forEach { it.cancel() }
    }

    @Test
    fun occupiedTable_loadsExistingOrder_updatesOrder_andPaysWithCashChange() = runTest {
        val (vm, orderDao, _) = createViewModel()
        val jobs = listOf(
            backgroundScope.launch(testDispatcher) { vm.tableSlots.collect {} },
            backgroundScope.launch(testDispatcher) { vm.menuItems.collect {} }
        )

        // Open table-02 with 2 guests & 1 Caffe Latte (28,000)
        val table2 = vm.tableSlots.value.first { it.id == "table-02" }
        vm.onTableClicked(table2)
        vm.confirmGuestCountAndEnterMenu(guestCount = 2)
        val latte = vm.menuItems.value.first { it.id == "menu-02" }
        vm.addMenuItemToCart(latte)
        vm.submitOrder(shiftSessionId = "session-1")

        // Tap occupied (orange) table-02 -> directly opens MenuOrder with existing items
        val occupiedTable2 = vm.tableSlots.value.first { it.id == "table-02" }
        assertTrue(occupiedTable2.isOccupied)
        vm.onTableClicked(occupiedTable2)

        assertNull(vm.pendingGuestCountTable.value)
        val existingStage = vm.stage.value as DashboardStage.MenuOrder
        assertTrue(existingStage.isExistingOrder)
        assertEquals(2, existingStage.guestCount)
        assertEquals(1, vm.draftCart.value["menu-02"]?.quantity)
        assertFalse(vm.hasUnsavedChanges())

        // Add Butter Croissant (24,000) and update guest count to 3 -> unsaved changes true
        val croissant = vm.menuItems.value.first { it.id == "menu-05" }
        vm.addMenuItemToCart(croissant)
        vm.updateActiveGuestCount(3)
        assertTrue(vm.hasUnsavedChanges())

        // Update Order -> remains orange with updated total (28,000 + 24,000 = 52,000 + 5,720 = 57,720)
        vm.submitOrder(shiftSessionId = "session-1")
        assertTrue(vm.stage.value is DashboardStage.TableGrid)
        val reOccupiedTable2 = vm.tableSlots.value.first { it.id == "table-02" }
        assertTrue(reOccupiedTable2.isOccupied)
        assertEquals(3, reOccupiedTable2.activeOrder?.order?.guestCount)
        assertEquals(57_720L, reOccupiedTable2.activeOrder?.order?.grandTotal)

        // Tap orange table-02 again and pay with Cash Rp 100,000
        vm.onTableClicked(reOccupiedTable2)
        vm.openPaymentDialog()
        assertTrue(vm.showPaymentDialog.value)

        // Insufficient cash should be ignored
        vm.confirmPayment(shiftSessionId = "session-1", paymentMethod = "Cash", cashReceived = 50_000L)
        assertNotNull(vm.showPaymentDialog.value)
        assertNull(vm.completedReceipt.value)

        // Sufficient cash -> order PAID, receipt shown, table becomes available after dismiss
        vm.confirmPayment(shiftSessionId = "session-1", paymentMethod = "Cash", cashReceived = 100_000L)
        assertFalse(vm.showPaymentDialog.value)
        val receipt = vm.completedReceipt.value
        assertNotNull(receipt)
        assertEquals(57_720L, receipt?.grandTotal)
        assertEquals(100_000L, receipt?.cashReceived)
        assertEquals(42_280L, receipt?.changeAmount)
        assertEquals("Cash", receipt?.paymentMethod)

        vm.dismissReceiptAndReturnToTables()
        assertNull(vm.completedReceipt.value)
        assertTrue(vm.stage.value is DashboardStage.TableGrid)
        assertFalse(vm.tableSlots.value.first { it.id == "table-02" }.isOccupied)
        assertEquals(OrderStatus.PAID, orderDao.allOrders().first().order.status)

        jobs.forEach { it.cancel() }
    }

    @Test
    fun cancelOpenOrder_marksOrderCancelled_andFreesTable() = runTest {
        val (vm, orderDao, _) = createViewModel()
        val jobs = listOf(
            backgroundScope.launch(testDispatcher) { vm.tableSlots.collect {} },
            backgroundScope.launch(testDispatcher) { vm.menuItems.collect {} }
        )

        val table3 = vm.tableSlots.value.first { it.id == "table-03" }
        vm.onTableClicked(table3)
        vm.confirmGuestCountAndEnterMenu(1)
        vm.addMenuItemToCart(vm.menuItems.value.first())
        vm.submitOrder("session-1")

        val occupiedTable3 = vm.tableSlots.value.first { it.id == "table-03" }
        assertTrue(occupiedTable3.isOccupied)

        vm.onTableClicked(occupiedTable3)
        vm.openCancelOrderDialog()
        assertTrue(vm.showCancelOrderDialog.value)

        vm.confirmCancelOpenOrder()
        assertFalse(vm.showCancelOrderDialog.value)
        assertTrue(vm.stage.value is DashboardStage.TableGrid)
        assertFalse(vm.tableSlots.value.first { it.id == "table-03" }.isOccupied)
        assertEquals(OrderStatus.CANCELLED, orderDao.allOrders().first().order.status)

        jobs.forEach { it.cancel() }
    }

    private class FakeTableDao(initialTables: List<DiningTableEntity>) : TableDao {
        private val state = MutableStateFlow(initialTables)

        override fun observeTables(): Flow<List<DiningTableEntity>> = state
        override suspend fun getTableById(id: String): DiningTableEntity? = state.value.firstOrNull { it.id == id }
        override suspend fun countTables(): Int = state.value.size
        override suspend fun insertTable(table: DiningTableEntity) {
            state.value = state.value.filterNot { it.id == table.id } + table
        }
        override suspend fun insertTables(tables: List<DiningTableEntity>) {
            state.value = tables
        }
        override suspend fun updateTable(table: DiningTableEntity) {
            insertTable(table)
        }
        override suspend fun deleteTableById(id: String) {
            state.value = state.value.filterNot { it.id == id }
        }
    }

    private class FakeMenuDao(
        initialCategories: List<MenuCategoryEntity>,
        initialItems: List<MenuItemEntity>
    ) : MenuDao {
        private val categoriesFlow = MutableStateFlow(initialCategories)
        private val itemsFlow = MutableStateFlow(initialItems)

        override fun observeCategories(): Flow<List<MenuCategoryEntity>> = categoriesFlow
        override suspend fun countCategories(): Int = categoriesFlow.value.size
        override suspend fun insertCategory(category: MenuCategoryEntity) {
            categoriesFlow.value = categoriesFlow.value + category
        }
        override suspend fun insertCategories(categories: List<MenuCategoryEntity>) {
            categoriesFlow.value = categories
        }
        override suspend fun deleteCategoryById(categoryId: String) {
            categoriesFlow.value = categoriesFlow.value.filterNot { it.id == categoryId }
        }
        override fun observeMenuItemsWithCategory(): Flow<List<MenuItemWithCategory>> {
            return itemsFlow.map { items ->
                val catMap = categoriesFlow.value.associateBy { it.id }
                items.map { item ->
                    MenuItemWithCategory(item = item, category = catMap[item.categoryId])
                }
            }
        }
        override suspend fun getMenuItemById(id: String): MenuItemEntity? = itemsFlow.value.firstOrNull { it.id == id }
        override suspend fun countMenuItems(): Int = itemsFlow.value.size
        override suspend fun insertMenuItem(item: MenuItemEntity) {
            itemsFlow.value = itemsFlow.value + item
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

    private class FakeOrderDao : OrderDao {
        private val orders = MutableStateFlow<List<DiningOrderWithItems>>(emptyList())

        fun allOrders(): List<DiningOrderWithItems> = orders.value

        override fun observeOpenOrdersWithItems(): Flow<List<DiningOrderWithItems>> {
            return orders.map { list -> list.filter { it.order.status == OrderStatus.OPEN } }
        }

        override fun observeAllOrdersWithItems(): Flow<List<DiningOrderWithItems>> = orders

        override suspend fun getNextOrderNumber(): Int {
            return (orders.value.maxOfOrNull { it.order.orderNumber } ?: 0) + 1
        }

        override suspend fun insertOrder(order: DiningOrderEntity) {
            val existingItems = orders.value.firstOrNull { it.order.id == order.id }?.items ?: emptyList()
            val updated = DiningOrderWithItems(order = order, items = existingItems)
            orders.value = orders.value.filterNot { it.order.id == order.id } + updated
        }

        override suspend fun insertOrderItems(items: List<DiningOrderItemEntity>) {
            val orderId = items.firstOrNull()?.orderId ?: return
            orders.value = orders.value.map { entry ->
                if (entry.order.id == orderId) entry.copy(items = items) else entry
            }
        }

        override suspend fun deleteItemsByOrderId(orderId: String) {
            orders.value = orders.value.map { entry ->
                if (entry.order.id == orderId) entry.copy(items = emptyList()) else entry
            }
        }

        override suspend fun markOrderPaid(
            orderId: String,
            paymentMethod: String,
            subtotal: Long,
            taxAmount: Long,
            grandTotal: Long,
            cashReceived: Long?,
            changeAmount: Long?,
            closedAt: Long
        ) {
            orders.value = orders.value.map { entry ->
                if (entry.order.id == orderId) {
                    entry.copy(
                        order = entry.order.copy(
                            status = OrderStatus.PAID,
                            paymentMethod = paymentMethod,
                            subtotal = subtotal,
                            taxAmount = taxAmount,
                            grandTotal = grandTotal,
                            cashReceived = cashReceived,
                            changeAmount = changeAmount,
                            updatedAt = closedAt,
                            closedAt = closedAt
                        )
                    )
                } else {
                    entry
                }
            }
        }

        override suspend fun markOrderCancelled(orderId: String, closedAt: Long) {
            orders.value = orders.value.map { entry ->
                if (entry.order.id == orderId) {
                    entry.copy(
                        order = entry.order.copy(
                            status = OrderStatus.CANCELLED,
                            updatedAt = closedAt,
                            closedAt = closedAt
                        )
                    )
                } else {
                    entry
                }
            }
        }
    }
}
