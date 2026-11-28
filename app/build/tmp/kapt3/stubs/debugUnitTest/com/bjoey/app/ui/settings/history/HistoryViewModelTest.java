package com.bjoey.app.ui.settings.history;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007H\u0007J\f\u0010\b\u001a\u00060\u0006j\u0002`\u0007H\u0007J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\b\u0010\f\u001a\u00020\u0006H\u0007J\f\u0010\r\u001a\u00060\u0006j\u0002`\u0007H\u0007J\b\u0010\u000e\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/bjoey/app/ui/settings/history/HistoryViewModelTest;", "", "()V", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "filtersAndSearch_filterByStatusOrderNumTableMenuAndPayment", "", "Lkotlinx/coroutines/test/TestResult;", "getOrderById_returnsMatchingEntryOrNull", "sampleOrders", "", "Lcom/bjoey/app/data/local/entity/DiningOrderWithItems;", "setUp", "summary_calculatesCountsAndPaidRevenueOnly", "tearDown", "FakeHistoryOrderDao", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class HistoryViewModelTest {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public HistoryViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    private final java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems> sampleOrders() {
        return null;
    }
    
    @org.junit.Test()
    public final void summary_calculatesCountsAndPaidRevenueOnly() {
    }
    
    @org.junit.Test()
    public final void filtersAndSearch_filterByStatusOrderNumTableMenuAndPayment() {
    }
    
    @org.junit.Test()
    public final void getOrderById_returnsMatchingEntryOrNull() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0003H\u0096@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010\u001bJR\u0010\u001c\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\b\u0010!\u001a\u0004\u0018\u00010\u001a2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010#J\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030%H\u0016J\u0014\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030%H\u0016R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/bjoey/app/ui/settings/history/HistoryViewModelTest$FakeHistoryOrderDao;", "Lcom/bjoey/app/data/local/dao/OrderDao;", "initialOrders", "", "Lcom/bjoey/app/data/local/entity/DiningOrderWithItems;", "(Ljava/util/List;)V", "flow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "deleteItemsByOrderId", "", "orderId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNextOrderNumber", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrder", "order", "Lcom/bjoey/app/data/local/entity/DiningOrderEntity;", "(Lcom/bjoey/app/data/local/entity/DiningOrderEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrderItems", "items", "Lcom/bjoey/app/data/local/entity/DiningOrderItemEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderCancelled", "closedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderPaid", "paymentMethod", "subtotal", "taxAmount", "grandTotal", "cashReceived", "changeAmount", "(Ljava/lang/String;Ljava/lang/String;JJJLjava/lang/Long;Ljava/lang/Long;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllOrdersWithItems", "Lkotlinx/coroutines/flow/Flow;", "observeOpenOrdersWithItems", "app_debugUnitTest"})
    static final class FakeHistoryOrderDao implements com.bjoey.app.data.local.dao.OrderDao {
        @org.jetbrains.annotations.NotNull()
        private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems>> flow = null;
        
        public FakeHistoryOrderDao(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems> initialOrders) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems>> observeOpenOrdersWithItems() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems>> observeAllOrdersWithItems() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object getNextOrderNumber(@org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertOrder(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.DiningOrderEntity order, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertOrderItems(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.DiningOrderItemEntity> items, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object deleteItemsByOrderId(@org.jetbrains.annotations.NotNull()
        java.lang.String orderId, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object markOrderPaid(@org.jetbrains.annotations.NotNull()
        java.lang.String orderId, @org.jetbrains.annotations.NotNull()
        java.lang.String paymentMethod, long subtotal, long taxAmount, long grandTotal, @org.jetbrains.annotations.Nullable()
        java.lang.Long cashReceived, @org.jetbrains.annotations.Nullable()
        java.lang.Long changeAmount, long closedAt, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object markOrderCancelled(@org.jetbrains.annotations.NotNull()
        java.lang.String orderId, long closedAt, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object saveOrderWithItems(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.DiningOrderEntity order, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.DiningOrderItemEntity> items, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}