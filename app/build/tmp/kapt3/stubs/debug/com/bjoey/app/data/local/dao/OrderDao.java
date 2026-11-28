package com.bjoey.app.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0016JR\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0014\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\u00100 H\'J\u0014\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\u00100 H\'J$\u0010#\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0097@\u00a2\u0006\u0002\u0010$\u00a8\u0006%"}, d2 = {"Lcom/bjoey/app/data/local/dao/OrderDao;", "", "deleteItemsByOrderId", "", "orderId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNextOrderNumber", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrder", "order", "Lcom/bjoey/app/data/local/entity/DiningOrderEntity;", "(Lcom/bjoey/app/data/local/entity/DiningOrderEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrderItems", "items", "", "Lcom/bjoey/app/data/local/entity/DiningOrderItemEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderCancelled", "closedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderPaid", "paymentMethod", "subtotal", "taxAmount", "grandTotal", "cashReceived", "changeAmount", "(Ljava/lang/String;Ljava/lang/String;JJJLjava/lang/Long;Ljava/lang/Long;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllOrdersWithItems", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bjoey/app/data/local/entity/DiningOrderWithItems;", "observeOpenOrdersWithItems", "saveOrderWithItems", "(Lcom/bjoey/app/data/local/entity/DiningOrderEntity;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface OrderDao {
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM dining_orders WHERE status = \'OPEN\' ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems>> observeOpenOrdersWithItems();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM dining_orders ORDER BY createdAt DESC, orderNumber DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems>> observeAllOrdersWithItems();
    
    @androidx.room.Query(value = "SELECT COALESCE(MAX(orderNumber), 0) + 1 FROM dining_orders")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNextOrderNumber(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrder(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.entity.DiningOrderEntity order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrderItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.local.entity.DiningOrderItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM dining_order_items WHERE orderId = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItemsByOrderId(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveOrderWithItems(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.entity.DiningOrderEntity order, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.local.entity.DiningOrderItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE dining_orders\n        SET status = \'PAID\',\n            paymentMethod = :paymentMethod,\n            subtotal = :subtotal,\n            taxAmount = :taxAmount,\n            grandTotal = :grandTotal,\n            cashReceived = :cashReceived,\n            changeAmount = :changeAmount,\n            updatedAt = :closedAt,\n            closedAt = :closedAt\n        WHERE id = :orderId\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markOrderPaid(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, long subtotal, long taxAmount, long grandTotal, @org.jetbrains.annotations.Nullable()
    java.lang.Long cashReceived, @org.jetbrains.annotations.Nullable()
    java.lang.Long changeAmount, long closedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE dining_orders\n        SET status = \'CANCELLED\',\n            updatedAt = :closedAt,\n            closedAt = :closedAt\n        WHERE id = :orderId\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markOrderCancelled(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, long closedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object saveOrderWithItems(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.dao.OrderDao $this, @org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.DiningOrderEntity order, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.DiningOrderItemEntity> items, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}