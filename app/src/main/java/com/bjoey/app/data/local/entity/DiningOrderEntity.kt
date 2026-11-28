package com.bjoey.app.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

object OrderStatus {
    const val OPEN = "OPEN"
    const val PAID = "PAID"
    const val CANCELLED = "CANCELLED"
}

@Entity(
    tableName = "dining_orders",
    indices = [
        Index(value = ["tableId"]),
        Index(value = ["status"])
    ]
)
data class DiningOrderEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val orderNumber: Int,
    val tableId: String,
    val tableName: String,
    val guestCount: Int,
    val shiftSessionId: String,
    val status: String = OrderStatus.OPEN,
    val paymentMethod: String? = null,
    val subtotal: Long,
    val taxAmount: Long,
    val grandTotal: Long,
    val cashReceived: Long? = null,
    val changeAmount: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null
)

@Entity(
    tableName = "dining_order_items",
    foreignKeys = [
        ForeignKey(
            entity = DiningOrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["orderId"])]
)
data class DiningOrderItemEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val orderId: String,
    val menuItemId: String,
    val itemTitle: String,
    val unitPrice: Long,
    val quantity: Int,
    val categoryName: String
)

data class DiningOrderWithItems(
    @Embedded val order: DiningOrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<DiningOrderItemEntity>
)
