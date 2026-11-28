package com.bjoey.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bjoey.app.data.local.dao.MenuDao
import com.bjoey.app.data.local.dao.OrderDao
import com.bjoey.app.data.local.dao.TableDao
import com.bjoey.app.data.local.entity.DiningOrderEntity
import com.bjoey.app.data.local.entity.DiningOrderItemEntity
import com.bjoey.app.data.local.entity.DiningOrderWithItems
import com.bjoey.app.data.local.entity.OrderStatus
import com.bjoey.app.ui.settings.menu.MenuCategory
import com.bjoey.app.ui.settings.menu.MenuItem
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
import kotlin.math.roundToLong

enum class TableFilter {
    ALL,
    AVAILABLE,
    OCCUPIED
}

data class TableSlotUi(
    val id: String,
    val name: String,
    val activeOrder: DiningOrderWithItems?
) {
    val isOccupied: Boolean get() = activeOrder != null
}

data class DraftCartItem(
    val menuItemId: String,
    val title: String,
    val unitPrice: Long,
    val categoryName: String,
    val quantity: Int
)

data class OrderTotals(
    val totalQty: Int,
    val subtotal: Long,
    val taxAmount: Long,
    val grandTotal: Long
)

data class CompletedReceiptUi(
    val orderNumber: Int,
    val tableName: String,
    val guestCount: Int,
    val paymentMethod: String,
    val items: List<DraftCartItem>,
    val subtotal: Long,
    val taxAmount: Long,
    val grandTotal: Long,
    val cashReceived: Long?,
    val changeAmount: Long?
)

sealed interface DashboardStage {
    data object TableGrid : DashboardStage

    data class MenuOrder(
        val tableId: String,
        val tableName: String,
        val guestCount: Int,
        val existingOrderId: String?,
        val existingOrderNumber: Int?,
        val originalGuestCount: Int?,
        val originalCreatedAt: Long?
    ) : DashboardStage {
        val isExistingOrder: Boolean get() = existingOrderId != null
    }
}

fun calculateOrderTotals(items: Collection<DraftCartItem>): OrderTotals {
    val totalQty = items.sumOf { it.quantity }
    val subtotal = items.sumOf { it.unitPrice * it.quantity }
    val taxAmount = (subtotal * 0.11).roundToLong()
    val grandTotal = subtotal + taxAmount
    return OrderTotals(
        totalQty = totalQty,
        subtotal = subtotal,
        taxAmount = taxAmount,
        grandTotal = grandTotal
    )
}

fun formatOrderNumber(orderNumber: Int): String {
    return "#ORD-${orderNumber.toString().padStart(3, '0')}"
}

/**
 * Room-backed ViewModel coordinating the Dine-In Open Bill POS workflow:
 * Table Selection -> Guest Count Input -> Menu Ordering -> Open Bill (Orange Table) -> Update / Payment / Cancel.
 */
class DashboardViewModel(
    private val tableDao: TableDao,
    private val menuDao: MenuDao,
    private val orderDao: OrderDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val tableSlots: StateFlow<List<TableSlotUi>> = combine(
        tableDao.observeTables(),
        orderDao.observeOpenOrdersWithItems()
    ) { tables, openOrders ->
        val openOrderByTableId = openOrders.associateBy { it.order.tableId }
        tables.map { table ->
            TableSlotUi(
                id = table.id,
                name = table.name,
                activeOrder = openOrderByTableId[table.id]
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    private val _tableSearchQuery = MutableStateFlow("")
    val tableSearchQuery: StateFlow<String> = _tableSearchQuery.asStateFlow()

    private val _tableFilter = MutableStateFlow(TableFilter.ALL)
    val tableFilter: StateFlow<TableFilter> = _tableFilter.asStateFlow()

    val filteredTableSlots: StateFlow<List<TableSlotUi>> = combine(
        tableSlots,
        _tableSearchQuery,
        _tableFilter
    ) { slots, query, filter ->
        val trimmed = query.trim()
        slots.filter { slot ->
            val matchesFilter = when (filter) {
                TableFilter.ALL -> true
                TableFilter.AVAILABLE -> !slot.isOccupied
                TableFilter.OCCUPIED -> slot.isOccupied
            }
            val matchesQuery = trimmed.isEmpty() || slot.name.contains(trimmed, ignoreCase = true)
            matchesFilter && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    val occupiedTableSlots: StateFlow<List<TableSlotUi>> = tableSlots
        .map { slots -> slots.filter { it.isOccupied } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    val categories: StateFlow<List<MenuCategory>> = menuDao.observeCategories()
        .map { entities -> entities.map { MenuCategory(id = it.id, name = it.name) } }
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
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val _menuSearchQuery = MutableStateFlow("")
    val menuSearchQuery: StateFlow<String> = _menuSearchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    val filteredMenuItems: StateFlow<List<MenuItem>> = combine(
        menuItems,
        _menuSearchQuery,
        _selectedCategoryId
    ) { items, query, categoryId ->
        val trimmed = query.trim()
        items.filter { item ->
            val matchesCategory = categoryId == null || item.categoryId == categoryId
            val matchesQuery = trimmed.isEmpty() ||
                item.title.contains(trimmed, ignoreCase = true) ||
                item.categoryName.contains(trimmed, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    private val _stage = MutableStateFlow<DashboardStage>(DashboardStage.TableGrid)
    val stage: StateFlow<DashboardStage> = _stage.asStateFlow()

    private val _pendingGuestCountTable = MutableStateFlow<TableSlotUi?>(null)
    val pendingGuestCountTable: StateFlow<TableSlotUi?> = _pendingGuestCountTable.asStateFlow()

    private val _showEditGuestCountDialog = MutableStateFlow(false)
    val showEditGuestCountDialog: StateFlow<Boolean> = _showEditGuestCountDialog.asStateFlow()

    private val _draftCart = MutableStateFlow<Map<String, DraftCartItem>>(emptyMap())
    val draftCart: StateFlow<Map<String, DraftCartItem>> = _draftCart.asStateFlow()

    private var originalItemsSnapshot: Map<String, Int> = emptyMap()

    private val _showPaymentDialog = MutableStateFlow(false)
    val showPaymentDialog: StateFlow<Boolean> = _showPaymentDialog.asStateFlow()

    private val _showCancelOrderDialog = MutableStateFlow(false)
    val showCancelOrderDialog: StateFlow<Boolean> = _showCancelOrderDialog.asStateFlow()

    private val _showDiscardDraftDialog = MutableStateFlow(false)
    val showDiscardDraftDialog: StateFlow<Boolean> = _showDiscardDraftDialog.asStateFlow()

    private val _completedReceipt = MutableStateFlow<CompletedReceiptUi?>(null)
    val completedReceipt: StateFlow<CompletedReceiptUi?> = _completedReceipt.asStateFlow()

    private val _isProcessingOrder = MutableStateFlow(false)
    val isProcessingOrder: StateFlow<Boolean> = _isProcessingOrder.asStateFlow()

    fun onTableSearchQueryChange(query: String) {
        _tableSearchQuery.value = query
    }

    fun onTableFilterSelected(filter: TableFilter) {
        _tableFilter.value = filter
    }

    fun onMenuSearchQueryChange(query: String) {
        _menuSearchQuery.value = query
    }

    fun onCategorySelected(categoryId: String?) {
        _selectedCategoryId.value = categoryId
    }

    fun onTableClicked(slot: TableSlotUi) {
        val activeOrder = slot.activeOrder
        if (activeOrder == null) {
            _pendingGuestCountTable.value = slot
        } else {
            enterExistingOrder(slot, activeOrder)
        }
    }

    fun dismissGuestCountDialog() {
        _pendingGuestCountTable.value = null
    }

    fun confirmGuestCountAndEnterMenu(guestCount: Int) {
        val table = _pendingGuestCountTable.value ?: return
        if (guestCount < 1) return

        _pendingGuestCountTable.value = null
        _menuSearchQuery.value = ""
        _selectedCategoryId.value = null
        _draftCart.value = emptyMap()
        originalItemsSnapshot = emptyMap()
        _stage.value = DashboardStage.MenuOrder(
            tableId = table.id,
            tableName = table.name,
            guestCount = guestCount,
            existingOrderId = null,
            existingOrderNumber = null,
            originalGuestCount = null,
            originalCreatedAt = null
        )
    }

    private fun enterExistingOrder(slot: TableSlotUi, activeOrder: DiningOrderWithItems) {
        val loadedCart = activeOrder.items.associate { orderItem ->
            orderItem.menuItemId to DraftCartItem(
                menuItemId = orderItem.menuItemId,
                title = orderItem.itemTitle,
                unitPrice = orderItem.unitPrice,
                categoryName = orderItem.categoryName,
                quantity = orderItem.quantity
            )
        }
        originalItemsSnapshot = loadedCart.mapValues { it.value.quantity }
        _draftCart.value = loadedCart
        _menuSearchQuery.value = ""
        _selectedCategoryId.value = null
        _stage.value = DashboardStage.MenuOrder(
            tableId = slot.id,
            tableName = slot.name,
            guestCount = activeOrder.order.guestCount,
            existingOrderId = activeOrder.order.id,
            existingOrderNumber = activeOrder.order.orderNumber,
            originalGuestCount = activeOrder.order.guestCount,
            originalCreatedAt = activeOrder.order.createdAt
        )
    }

    fun openEditGuestCountDialog() {
        if (_stage.value is DashboardStage.MenuOrder) {
            _showEditGuestCountDialog.value = true
        }
    }

    fun dismissEditGuestCountDialog() {
        _showEditGuestCountDialog.value = false
    }

    fun updateActiveGuestCount(guestCount: Int) {
        val current = _stage.value as? DashboardStage.MenuOrder ?: return
        if (guestCount < 1) return
        _stage.value = current.copy(guestCount = guestCount)
        _showEditGuestCountDialog.value = false
    }

    fun addMenuItemToCart(item: MenuItem) {
        val current = _draftCart.value
        val existing = current[item.id]
        val updatedItem = if (existing != null) {
            existing.copy(quantity = existing.quantity + 1)
        } else {
            DraftCartItem(
                menuItemId = item.id,
                title = item.title,
                unitPrice = item.price,
                categoryName = item.categoryName,
                quantity = 1
            )
        }
        _draftCart.value = current + (item.id to updatedItem)
    }

    fun adjustCartItemQuantity(menuItemId: String, delta: Int) {
        val current = _draftCart.value
        val existing = current[menuItemId] ?: return
        val nextQty = existing.quantity + delta
        _draftCart.value = if (nextQty <= 0) {
            current - menuItemId
        } else {
            current + (menuItemId to existing.copy(quantity = nextQty))
        }
    }

    fun clearDraftCart() {
        _draftCart.value = emptyMap()
    }

    fun hasUnsavedChanges(): Boolean {
        val currentStage = _stage.value as? DashboardStage.MenuOrder ?: return false
        val currentSnapshot = _draftCart.value.mapValues { it.value.quantity }
        return if (currentStage.isExistingOrder) {
            currentSnapshot != originalItemsSnapshot || currentStage.guestCount != currentStage.originalGuestCount
        } else {
            currentSnapshot.isNotEmpty()
        }
    }

    fun requestBackToTables() {
        if (_stage.value !is DashboardStage.MenuOrder) return
        if (hasUnsavedChanges()) {
            _showDiscardDraftDialog.value = true
        } else {
            confirmDiscardAndBackToTables()
        }
    }

    fun dismissDiscardDraftDialog() {
        _showDiscardDraftDialog.value = false
    }

    fun confirmDiscardAndBackToTables() {
        _showDiscardDraftDialog.value = false
        resetToTableGrid()
    }

    fun submitOrder(shiftSessionId: String) {
        val currentStage = _stage.value as? DashboardStage.MenuOrder ?: return
        val cartItems = _draftCart.value.values.toList()
        if (cartItems.isEmpty() || _isProcessingOrder.value) return

        _isProcessingOrder.value = true
        viewModelScope.launch {
            try {
                withContext(ioDispatcher) {
                    val now = System.currentTimeMillis()
                    val orderId = currentStage.existingOrderId ?: UUID.randomUUID().toString()
                    val orderNumber = currentStage.existingOrderNumber ?: orderDao.getNextOrderNumber()
                    val totals = calculateOrderTotals(cartItems)

                    val orderEntity = DiningOrderEntity(
                        id = orderId,
                        orderNumber = orderNumber,
                        tableId = currentStage.tableId,
                        tableName = currentStage.tableName,
                        guestCount = currentStage.guestCount,
                        shiftSessionId = shiftSessionId,
                        status = OrderStatus.OPEN,
                        paymentMethod = null,
                        subtotal = totals.subtotal,
                        taxAmount = totals.taxAmount,
                        grandTotal = totals.grandTotal,
                        cashReceived = null,
                        changeAmount = null,
                        createdAt = currentStage.originalCreatedAt ?: now,
                        updatedAt = now,
                        closedAt = null
                    )

                    val itemEntities = cartItems.map { item ->
                        DiningOrderItemEntity(
                            id = UUID.randomUUID().toString(),
                            orderId = orderId,
                            menuItemId = item.menuItemId,
                            itemTitle = item.title,
                            unitPrice = item.unitPrice,
                            quantity = item.quantity,
                            categoryName = item.categoryName
                        )
                    }

                    orderDao.saveOrderWithItems(orderEntity, itemEntities)
                }
                resetToTableGrid()
            } finally {
                _isProcessingOrder.value = false
            }
        }
    }

    fun openPaymentDialog() {
        if (_stage.value is DashboardStage.MenuOrder && _draftCart.value.isNotEmpty()) {
            _showPaymentDialog.value = true
        }
    }

    fun dismissPaymentDialog() {
        _showPaymentDialog.value = false
    }

    fun confirmPayment(
        shiftSessionId: String,
        paymentMethod: String,
        cashReceived: Long?
    ) {
        val currentStage = _stage.value as? DashboardStage.MenuOrder ?: return
        val cartItems = _draftCart.value.values.toList()
        if (cartItems.isEmpty() || _isProcessingOrder.value) return

        val totals = calculateOrderTotals(cartItems)
        val effectiveCashReceived: Long?
        val changeAmount: Long?
        if (paymentMethod == "Cash") {
            val received = cashReceived ?: return
            if (received < totals.grandTotal) return
            effectiveCashReceived = received
            changeAmount = received - totals.grandTotal
        } else {
            effectiveCashReceived = null
            changeAmount = null
        }

        _isProcessingOrder.value = true
        viewModelScope.launch {
            try {
                val resolvedOrderNumber = withContext(ioDispatcher) {
                    val now = System.currentTimeMillis()
                    val orderId = currentStage.existingOrderId ?: UUID.randomUUID().toString()
                    val orderNumber = currentStage.existingOrderNumber ?: orderDao.getNextOrderNumber()

                    val orderEntity = DiningOrderEntity(
                        id = orderId,
                        orderNumber = orderNumber,
                        tableId = currentStage.tableId,
                        tableName = currentStage.tableName,
                        guestCount = currentStage.guestCount,
                        shiftSessionId = shiftSessionId,
                        status = OrderStatus.PAID,
                        paymentMethod = paymentMethod,
                        subtotal = totals.subtotal,
                        taxAmount = totals.taxAmount,
                        grandTotal = totals.grandTotal,
                        cashReceived = effectiveCashReceived,
                        changeAmount = changeAmount,
                        createdAt = currentStage.originalCreatedAt ?: now,
                        updatedAt = now,
                        closedAt = now
                    )

                    val itemEntities = cartItems.map { item ->
                        DiningOrderItemEntity(
                            id = UUID.randomUUID().toString(),
                            orderId = orderId,
                            menuItemId = item.menuItemId,
                            itemTitle = item.title,
                            unitPrice = item.unitPrice,
                            quantity = item.quantity,
                            categoryName = item.categoryName
                        )
                    }

                    orderDao.saveOrderWithItems(orderEntity, itemEntities)
                    orderNumber
                }

                _showPaymentDialog.value = false
                _completedReceipt.value = CompletedReceiptUi(
                    orderNumber = resolvedOrderNumber,
                    tableName = currentStage.tableName,
                    guestCount = currentStage.guestCount,
                    paymentMethod = paymentMethod,
                    items = cartItems,
                    subtotal = totals.subtotal,
                    taxAmount = totals.taxAmount,
                    grandTotal = totals.grandTotal,
                    cashReceived = effectiveCashReceived,
                    changeAmount = changeAmount
                )
            } finally {
                _isProcessingOrder.value = false
            }
        }
    }

    fun dismissReceiptAndReturnToTables() {
        _completedReceipt.value = null
        resetToTableGrid()
    }

    fun openCancelOrderDialog() {
        val currentStage = _stage.value as? DashboardStage.MenuOrder ?: return
        if (currentStage.isExistingOrder) {
            _showCancelOrderDialog.value = true
        }
    }

    fun dismissCancelOrderDialog() {
        _showCancelOrderDialog.value = false
    }

    fun confirmCancelOpenOrder() {
        val currentStage = _stage.value as? DashboardStage.MenuOrder ?: return
        val orderId = currentStage.existingOrderId ?: return
        if (_isProcessingOrder.value) return

        _isProcessingOrder.value = true
        viewModelScope.launch {
            try {
                withContext(ioDispatcher) {
                    orderDao.markOrderCancelled(orderId, System.currentTimeMillis())
                }
                _showCancelOrderDialog.value = false
                resetToTableGrid()
            } finally {
                _isProcessingOrder.value = false
            }
        }
    }

    private fun resetToTableGrid() {
        _draftCart.value = emptyMap()
        originalItemsSnapshot = emptyMap()
        _menuSearchQuery.value = ""
        _selectedCategoryId.value = null
        _showEditGuestCountDialog.value = false
        _showPaymentDialog.value = false
        _showCancelOrderDialog.value = false
        _showDiscardDraftDialog.value = false
        _stage.value = DashboardStage.TableGrid
    }

    class Factory(
        private val tableDao: TableDao,
        private val menuDao: MenuDao,
        private val orderDao: OrderDao
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(
                    tableDao = tableDao,
                    menuDao = menuDao,
                    orderDao = orderDao
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
