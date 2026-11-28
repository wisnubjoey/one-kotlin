package com.bjoey.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bjoey.app.data.local.entity.DiningOrderEntity
import com.bjoey.app.data.local.entity.DiningOrderItemEntity
import com.bjoey.app.data.local.entity.DiningOrderWithItems
import com.bjoey.app.data.local.entity.OrderStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Transaction
    @Query("SELECT * FROM dining_orders WHERE status = '${OrderStatus.OPEN}' ORDER BY updatedAt DESC")
    fun observeOpenOrdersWithItems(): Flow<List<DiningOrderWithItems>>

    @Transaction
    @Query("SELECT * FROM dining_orders ORDER BY createdAt DESC, orderNumber DESC")
    fun observeAllOrdersWithItems(): Flow<List<DiningOrderWithItems>>

    @Query("SELECT COALESCE(MAX(orderNumber), 0) + 1 FROM dining_orders")
    suspend fun getNextOrderNumber(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: DiningOrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<DiningOrderItemEntity>)

    @Query("DELETE FROM dining_order_items WHERE orderId = :orderId")
    suspend fun deleteItemsByOrderId(orderId: String)

    @Transaction
    suspend fun saveOrderWithItems(order: DiningOrderEntity, items: List<DiningOrderItemEntity>) {
        insertOrder(order)
        deleteItemsByOrderId(order.id)
        if (items.isNotEmpty()) {
            insertOrderItems(items)
        }
    }

    @Query(
        """
        UPDATE dining_orders
        SET status = '${OrderStatus.PAID}',
            paymentMethod = :paymentMethod,
            subtotal = :subtotal,
            taxAmount = :taxAmount,
            grandTotal = :grandTotal,
            cashReceived = :cashReceived,
            changeAmount = :changeAmount,
            updatedAt = :closedAt,
            closedAt = :closedAt
        WHERE id = :orderId
        """
    )
    suspend fun markOrderPaid(
        orderId: String,
        paymentMethod: String,
        subtotal: Long,
        taxAmount: Long,
        grandTotal: Long,
        cashReceived: Long?,
        changeAmount: Long?,
        closedAt: Long
    )

    @Query(
        """
        UPDATE dining_orders
        SET status = '${OrderStatus.CANCELLED}',
            updatedAt = :closedAt,
            closedAt = :closedAt
        WHERE id = :orderId
        """
    )
    suspend fun markOrderCancelled(orderId: String, closedAt: Long)
}
