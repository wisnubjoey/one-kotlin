package com.bjoey.app.ui.settings.history

import androidx.compose.ui.graphics.Color
import com.bjoey.app.data.local.entity.DiningOrderWithItems
import com.bjoey.app.data.local.entity.OrderStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * High-contrast monochrome + deep green POS design tokens for Order History.
 */
object HistoryColors {
    val ScreenBackground = Color(0xFFF9FAFB)
    val CardSurface = Color(0xFFFFFFFF)
    val BorderDefault = Color(0xFFE5E7EB)
    val BorderFocused = Color(0xFF111111)
    val TextPrimary = Color(0xFF111111)
    val TextSecondary = Color(0xFF6B7280)
    val PrimaryAccent = Color(0xFF14532D)
    val BadgeBackground = Color(0xFFF3F4F6)

    val PaidBg = Color(0xFFECFDF5)
    val PaidBorder = Color(0xFFA7F3D0)
    val PaidText = Color(0xFF14532D)

    val OpenBg = Color(0xFFFFF7ED)
    val OpenBorder = Color(0xFFEA580C)
    val OpenText = Color(0xFF9A3412)

    val CancelledBg = Color(0xFFFEF2F2)
    val CancelledBorder = Color(0xFFFECACA)
    val CancelledText = Color(0xFFDC2626)
}

enum class HistoryStatusFilter(val label: String) {
    ALL("Semua"),
    PAID("Lunas"),
    OPEN("Open Bill"),
    CANCELLED("Dibatalkan")
}

/**
 * Snapshot line item inside a recorded POS order.
 */
data class OrderHistoryItem(
    val id: String,
    val menuItemId: String,
    val itemTitle: String,
    val unitPrice: Long,
    val quantity: Int,
    val categoryName: String
) {
    val lineTotal: Long get() = unitPrice * quantity
}

/**
 * Order history domain model backed by Room `dining_orders` and `dining_order_items`.
 */
data class OrderHistoryEntry(
    val id: String,
    val orderNumber: Int,
    val tableId: String,
    val tableName: String,
    val guestCount: Int,
    val status: String,
    val paymentMethod: String?,
    val subtotal: Long,
    val taxAmount: Long,
    val grandTotal: Long,
    val cashReceived: Long?,
    val changeAmount: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val closedAt: Long?,
    val items: List<OrderHistoryItem>
) {
    val totalItemQuantity: Int get() = items.sumOf { it.quantity }
    val displayOrderNumber: String get() = formatHistoryOrderNumber(orderNumber)
}

data class HistorySummary(
    val totalOrders: Int = 0,
    val paidCount: Int = 0,
    val openCount: Int = 0,
    val cancelledCount: Int = 0,
    val totalPaidRevenue: Long = 0L
)

fun DiningOrderWithItems.toHistoryEntry(): OrderHistoryEntry {
    return OrderHistoryEntry(
        id = order.id,
        orderNumber = order.orderNumber,
        tableId = order.tableId,
        tableName = order.tableName,
        guestCount = order.guestCount,
        status = order.status,
        paymentMethod = order.paymentMethod,
        subtotal = order.subtotal,
        taxAmount = order.taxAmount,
        grandTotal = order.grandTotal,
        cashReceived = order.cashReceived,
        changeAmount = order.changeAmount,
        createdAt = order.createdAt,
        updatedAt = order.updatedAt,
        closedAt = order.closedAt,
        items = items.map { item ->
            OrderHistoryItem(
                id = item.id,
                menuItemId = item.menuItemId,
                itemTitle = item.itemTitle,
                unitPrice = item.unitPrice,
                quantity = item.quantity,
                categoryName = item.categoryName
            )
        }
    )
}

fun formatHistoryOrderNumber(orderNumber: Int): String {
    return "#ORD-${orderNumber.toString().padStart(3, '0')}"
}

fun formatHistoryTimestamp(epochMillis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("in", "ID"))
    return formatter.format(Date(epochMillis))
}

fun orderStatusLabel(status: String): String = when (status) {
    OrderStatus.PAID -> "LUNAS"
    OrderStatus.OPEN -> "OPEN BILL"
    OrderStatus.CANCELLED -> "DIBATALKAN"
    else -> status
}
