package com.bjoey.app.ui.settings.history

import com.bjoey.app.data.local.dao.OrderDao
import com.bjoey.app.data.local.entity.DiningOrderEntity
import com.bjoey.app.data.local.entity.DiningOrderItemEntity
import com.bjoey.app.data.local.entity.DiningOrderWithItems
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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun sampleOrders(): List<DiningOrderWithItems> {
        val paidOrder = DiningOrderWithItems(
            order = DiningOrderEntity(
                id = "ord-1",
                orderNumber = 1,
                tableId = "table-01",
                tableName = "Meja 01",
                guestCount = 2,
                shiftSessionId = "session-1",
                status = OrderStatus.PAID,
                paymentMethod = "Cash",
                subtotal = 50_000L,
                taxAmount = 5_500L,
                grandTotal = 55_500L,
                cashReceived = 100_000L,
                changeAmount = 44_500L,
                createdAt = 1_000L,
                updatedAt = 2_000L,
                closedAt = 2_000L
            ),
            items = listOf(
                DiningOrderItemEntity(
                    id = "oi-1",
                    orderId = "ord-1",
                    menuItemId = "menu-01",
                    itemTitle = "Iced Americano",
                    unitPrice = 25_000L,
                    quantity = 2,
                    categoryName = "Coffee"
                )
            )
        )

        val openOrder = DiningOrderWithItems(
            order = DiningOrderEntity(
                id = "ord-2",
                orderNumber = 2,
                tableId = "table-02",
                tableName = "Meja 02",
                guestCount = 4,
                shiftSessionId = "session-1",
                status = OrderStatus.OPEN,
                paymentMethod = null,
                subtotal = 32_000L,
                taxAmount = 3_520L,
                grandTotal = 35_520L,
                createdAt = 3_000L,
                updatedAt = 3_000L
            ),
            items = listOf(
                DiningOrderItemEntity(
                    id = "oi-2",
                    orderId = "ord-2",
                    menuItemId = "menu-03",
                    itemTitle = "Iced Matcha Latte",
                    unitPrice = 32_000L,
                    quantity = 1,
                    categoryName = "Non-Coffee"
                )
            )
        )

        val cancelledOrder = DiningOrderWithItems(
            order = DiningOrderEntity(
                id = "ord-3",
                orderNumber = 3,
                tableId = "table-03",
                tableName = "Meja VIP",
                guestCount = 3,
                shiftSessionId = "session-1",
                status = OrderStatus.CANCELLED,
                paymentMethod = null,
                subtotal = 24_000L,
                taxAmount = 2_640L,
                grandTotal = 26_640L,
                createdAt = 4_000L,
                updatedAt = 5_000L,
                closedAt = 5_000L
            ),
            items = listOf(
                DiningOrderItemEntity(
                    id = "oi-3",
                    orderId = "ord-3",
                    menuItemId = "menu-05",
                    itemTitle = "Butter Croissant",
                    unitPrice = 24_000L,
                    quantity = 1,
                    categoryName = "Pastry"
                )
            )
        )

        return listOf(cancelledOrder, openOrder, paidOrder)
    }

    @Test
    fun summary_calculatesCountsAndPaidRevenueOnly() = runTest {
        val viewModel = HistoryViewModel(FakeHistoryOrderDao(sampleOrders()))
        val ordersJob = backgroundScope.launch(testDispatcher) { viewModel.orders.collect {} }
        val summaryJob = backgroundScope.launch(testDispatcher) { viewModel.summary.collect {} }

        assertEquals(3, viewModel.orders.value.size)
        assertEquals(3, viewModel.summary.value.totalOrders)
        assertEquals(1, viewModel.summary.value.paidCount)
        assertEquals(1, viewModel.summary.value.openCount)
        assertEquals(1, viewModel.summary.value.cancelledCount)
        assertEquals(55_500L, viewModel.summary.value.totalPaidRevenue)

        ordersJob.cancel()
        summaryJob.cancel()
    }

    @Test
    fun filtersAndSearch_filterByStatusOrderNumTableMenuAndPayment() = runTest {
        val viewModel = HistoryViewModel(FakeHistoryOrderDao(sampleOrders()))
        val filteredJob = backgroundScope.launch(testDispatcher) { viewModel.filteredOrders.collect {} }

        // Status filter
        viewModel.onStatusFilterSelected(HistoryStatusFilter.PAID)
        assertEquals(listOf("ord-1"), viewModel.filteredOrders.value.map { it.id })

        viewModel.onStatusFilterSelected(HistoryStatusFilter.OPEN)
        assertEquals(listOf("ord-2"), viewModel.filteredOrders.value.map { it.id })

        viewModel.onStatusFilterSelected(HistoryStatusFilter.CANCELLED)
        assertEquals(listOf("ord-3"), viewModel.filteredOrders.value.map { it.id })

        // Reset to ALL and test search queries
        viewModel.onStatusFilterSelected(HistoryStatusFilter.ALL)

        viewModel.onSearchQueryChange("ORD-002")
        assertEquals(listOf("ord-2"), viewModel.filteredOrders.value.map { it.id })

        viewModel.onSearchQueryChange("Meja VIP")
        assertEquals(listOf("ord-3"), viewModel.filteredOrders.value.map { it.id })

        viewModel.onSearchQueryChange("Americano")
        assertEquals(listOf("ord-1"), viewModel.filteredOrders.value.map { it.id })

        viewModel.onSearchQueryChange("cash")
        assertEquals(listOf("ord-1"), viewModel.filteredOrders.value.map { it.id })

        viewModel.onSearchQueryChange("")
        assertEquals(3, viewModel.filteredOrders.value.size)

        filteredJob.cancel()
    }

    @Test
    fun getOrderById_returnsMatchingEntryOrNull() = runTest {
        val viewModel = HistoryViewModel(FakeHistoryOrderDao(sampleOrders()))
        val ordersJob = backgroundScope.launch(testDispatcher) { viewModel.orders.collect {} }

        val entry = viewModel.getOrderById("ord-1")
        assertNotNull(entry)
        assertEquals("#ORD-001", entry?.displayOrderNumber)
        assertEquals(44_500L, entry?.changeAmount)
        assertNull(viewModel.getOrderById("missing-id"))

        ordersJob.cancel()
    }

    private class FakeHistoryOrderDao(
        initialOrders: List<DiningOrderWithItems>
    ) : OrderDao {
        private val flow = MutableStateFlow(initialOrders)

        override fun observeOpenOrdersWithItems(): Flow<List<DiningOrderWithItems>> {
            return flow.map { list -> list.filter { it.order.status == OrderStatus.OPEN } }
        }

        override fun observeAllOrdersWithItems(): Flow<List<DiningOrderWithItems>> = flow

        override suspend fun getNextOrderNumber(): Int = 1
        override suspend fun insertOrder(order: DiningOrderEntity) {}
        override suspend fun insertOrderItems(items: List<DiningOrderItemEntity>) {}
        override suspend fun deleteItemsByOrderId(orderId: String) {}
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
        }

        override suspend fun markOrderCancelled(orderId: String, closedAt: Long) {}
    }
}
