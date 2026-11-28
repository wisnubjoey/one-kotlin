package com.bjoey.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0003\u0011\u0012\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007H\u0007J\u001a\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\tH\u0002J\f\u0010\r\u001a\u00060\u0006j\u0002`\u0007H\u0007J\f\u0010\u000e\u001a\u00060\u0006j\u0002`\u0007H\u0007J\b\u0010\u000f\u001a\u00020\u0006H\u0007J\b\u0010\u0010\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/bjoey/app/ui/dashboard/DashboardViewModelTest;", "", "()V", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "cancelOpenOrder_marksOrderCancelled_andFreesTable", "", "Lkotlinx/coroutines/test/TestResult;", "createViewModel", "Lkotlin/Triple;", "Lcom/bjoey/app/ui/dashboard/DashboardViewModel;", "Lcom/bjoey/app/ui/dashboard/DashboardViewModelTest$FakeOrderDao;", "Lcom/bjoey/app/ui/dashboard/DashboardViewModelTest$FakeMenuDao;", "dineInNewOrder_opensGuestDialog_createsOpenBill_andTurnsTableOccupied", "occupiedTable_loadsExistingOrder_updatesOrder_andPaysWithCashChange", "setUp", "tearDown", "FakeMenuDao", "FakeOrderDao", "FakeTableDao", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class DashboardViewModelTest {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public DashboardViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    private final kotlin.Triple<com.bjoey.app.ui.dashboard.DashboardViewModel, com.bjoey.app.ui.dashboard.DashboardViewModelTest.FakeOrderDao, com.bjoey.app.ui.dashboard.DashboardViewModelTest.FakeMenuDao> createViewModel() {
        return null;
    }
    
    @org.junit.Test()
    public final void dineInNewOrder_opensGuestDialog_createsOpenBill_andTurnsTableOccupied() {
    }
    
    @org.junit.Test()
    public final void occupiedTable_loadsExistingOrder_updatesOrder_andPaysWithCashChange() {
    }
    
    @org.junit.Test()
    public final void cancelOpenOrder_marksOrderCancelled_andFreesTable() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u000b\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0017\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0096@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0004H\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u001c\u0010 \u001a\u00020\u00102\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u0096@\u00a2\u0006\u0002\u0010\u0019J\u0014\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030#H\u0016J\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u00030#H\u0016J\u0016\u0010&\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u001fR\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00030\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/bjoey/app/ui/dashboard/DashboardViewModelTest$FakeMenuDao;", "Lcom/bjoey/app/data/local/dao/MenuDao;", "initialCategories", "", "Lcom/bjoey/app/data/local/entity/MenuCategoryEntity;", "initialItems", "Lcom/bjoey/app/data/local/entity/MenuItemEntity;", "(Ljava/util/List;Ljava/util/List;)V", "categoriesFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "itemsFlow", "countCategories", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "countMenuItems", "deleteCategoryById", "", "categoryId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMenuItemById", "id", "getMenuItemById", "insertCategories", "categories", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCategory", "category", "(Lcom/bjoey/app/data/local/entity/MenuCategoryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertMenuItem", "item", "(Lcom/bjoey/app/data/local/entity/MenuItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertMenuItems", "items", "observeCategories", "Lkotlinx/coroutines/flow/Flow;", "observeMenuItemsWithCategory", "Lcom/bjoey/app/data/local/entity/MenuItemWithCategory;", "updateMenuItem", "app_debugUnitTest"})
    static final class FakeMenuDao implements com.bjoey.app.data.local.dao.MenuDao {
        @org.jetbrains.annotations.NotNull()
        private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.local.entity.MenuCategoryEntity>> categoriesFlow = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.local.entity.MenuItemEntity>> itemsFlow = null;
        
        public FakeMenuDao(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.MenuCategoryEntity> initialCategories, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.MenuItemEntity> initialItems) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.MenuCategoryEntity>> observeCategories() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object countCategories(@org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertCategory(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.MenuCategoryEntity category, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertCategories(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.MenuCategoryEntity> categories, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object deleteCategoryById(@org.jetbrains.annotations.NotNull()
        java.lang.String categoryId, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.MenuItemWithCategory>> observeMenuItemsWithCategory() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object getMenuItemById(@org.jetbrains.annotations.NotNull()
        java.lang.String id, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super com.bjoey.app.data.local.entity.MenuItemEntity> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object countMenuItems(@org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertMenuItem(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.MenuItemEntity item, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertMenuItems(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.MenuItemEntity> items, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object updateMenuItem(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.MenuItemEntity item, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object deleteMenuItemById(@org.jetbrains.annotations.NotNull()
        java.lang.String id, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0005H\u0096@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010\u001bJR\u0010\u001c\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\b\u0010!\u001a\u0004\u0018\u00010\u001a2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010#J\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050%H\u0016J\u0014\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050%H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/bjoey/app/ui/dashboard/DashboardViewModelTest$FakeOrderDao;", "Lcom/bjoey/app/data/local/dao/OrderDao;", "()V", "orders", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/bjoey/app/data/local/entity/DiningOrderWithItems;", "allOrders", "deleteItemsByOrderId", "", "orderId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNextOrderNumber", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrder", "order", "Lcom/bjoey/app/data/local/entity/DiningOrderEntity;", "(Lcom/bjoey/app/data/local/entity/DiningOrderEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrderItems", "items", "Lcom/bjoey/app/data/local/entity/DiningOrderItemEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderCancelled", "closedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderPaid", "paymentMethod", "subtotal", "taxAmount", "grandTotal", "cashReceived", "changeAmount", "(Ljava/lang/String;Ljava/lang/String;JJJLjava/lang/Long;Ljava/lang/Long;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAllOrdersWithItems", "Lkotlinx/coroutines/flow/Flow;", "observeOpenOrdersWithItems", "app_debugUnitTest"})
    static final class FakeOrderDao implements com.bjoey.app.data.local.dao.OrderDao {
        @org.jetbrains.annotations.NotNull()
        private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems>> orders = null;
        
        public FakeOrderDao() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bjoey.app.data.local.entity.DiningOrderWithItems> allOrders() {
            return null;
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\tH\u0096@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0004H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\u00020\f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0096@\u00a2\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0018H\u0016J\u0016\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0004H\u0096@\u00a2\u0006\u0002\u0010\u0013R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/bjoey/app/ui/dashboard/DashboardViewModelTest$FakeTableDao;", "Lcom/bjoey/app/data/local/dao/TableDao;", "initialTables", "", "Lcom/bjoey/app/data/local/entity/DiningTableEntity;", "(Ljava/util/List;)V", "state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "countTables", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTableById", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTableById", "insertTable", "table", "(Lcom/bjoey/app/data/local/entity/DiningTableEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertTables", "tables", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeTables", "Lkotlinx/coroutines/flow/Flow;", "updateTable", "app_debugUnitTest"})
    static final class FakeTableDao implements com.bjoey.app.data.local.dao.TableDao {
        @org.jetbrains.annotations.NotNull()
        private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity>> state = null;
        
        public FakeTableDao(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity> initialTables) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity>> observeTables() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object getTableById(@org.jetbrains.annotations.NotNull()
        java.lang.String id, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super com.bjoey.app.data.local.entity.DiningTableEntity> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object countTables(@org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertTable(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.DiningTableEntity table, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object insertTables(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity> tables, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object updateTable(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.entity.DiningTableEntity table, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.Object deleteTableById(@org.jetbrains.annotations.NotNull()
        java.lang.String id, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}