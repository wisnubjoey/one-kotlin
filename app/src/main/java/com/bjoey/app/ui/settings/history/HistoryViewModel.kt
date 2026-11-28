package com.bjoey.app.ui.settings.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bjoey.app.data.local.dao.OrderDao
import com.bjoey.app.data.local.entity.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Room-backed ViewModel managing POS order history, summary stats, and live filtering.
 */
class HistoryViewModel(
    orderDao: OrderDao
) : ViewModel() {

    val orders: StateFlow<List<OrderHistoryEntry>> = orderDao.observeAllOrdersWithItems()
        .map { rows -> rows.map { it.toHistoryEntry() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    val summary: StateFlow<HistorySummary> = orders
        .map { list ->
            val paidOrders = list.filter { it.status == OrderStatus.PAID }
            HistorySummary(
                totalOrders = list.size,
                paidCount = paidOrders.size,
                openCount = list.count { it.status == OrderStatus.OPEN },
                cancelledCount = list.count { it.status == OrderStatus.CANCELLED },
                totalPaidRevenue = paidOrders.sumOf { it.grandTotal }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistorySummary()
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedStatusFilter = MutableStateFlow(HistoryStatusFilter.ALL)
    val selectedStatusFilter: StateFlow<HistoryStatusFilter> = _selectedStatusFilter.asStateFlow()

    val filteredOrders: StateFlow<List<OrderHistoryEntry>> = combine(
        orders,
        _searchQuery,
        _selectedStatusFilter
    ) { allOrders, query, statusFilter ->
        val trimmed = query.trim()
        allOrders.filter { entry ->
            val matchesStatus = when (statusFilter) {
                HistoryStatusFilter.ALL -> true
                HistoryStatusFilter.PAID -> entry.status == OrderStatus.PAID
                HistoryStatusFilter.OPEN -> entry.status == OrderStatus.OPEN
                HistoryStatusFilter.CANCELLED -> entry.status == OrderStatus.CANCELLED
            }
            val matchesQuery = trimmed.isEmpty() ||
                entry.displayOrderNumber.contains(trimmed, ignoreCase = true) ||
                entry.tableName.contains(trimmed, ignoreCase = true) ||
                (entry.paymentMethod?.contains(trimmed, ignoreCase = true) == true) ||
                entry.items.any { it.itemTitle.contains(trimmed, ignoreCase = true) }

            matchesStatus && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onStatusFilterSelected(filter: HistoryStatusFilter) {
        _selectedStatusFilter.value = filter
    }

    fun getOrderById(id: String): OrderHistoryEntry? {
        return orders.value.firstOrNull { it.id == id }
    }

    class Factory(private val orderDao: OrderDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                return HistoryViewModel(orderDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
