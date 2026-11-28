package com.bjoey.app.ui.dashboard;

/**
 * Room-backed ViewModel coordinating the Dine-In Open Bill POS workflow:
 * Table Selection -> Guest Count Input -> Menu Ordering -> Open Bill (Orange Table) -> Update / Payment / Cancel.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001:\u0001xB\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020,J\u0016\u0010P\u001a\u00020N2\u0006\u0010Q\u001a\u00020\u00102\u0006\u0010R\u001a\u000208J\u0006\u0010S\u001a\u00020NJ\u0006\u0010T\u001a\u00020NJ\u0006\u0010U\u001a\u00020NJ\u000e\u0010V\u001a\u00020N2\u0006\u0010W\u001a\u000208J%\u0010X\u001a\u00020N2\u0006\u0010Y\u001a\u00020\u00102\u0006\u0010Z\u001a\u00020\u00102\b\u0010[\u001a\u0004\u0018\u00010\\\u00a2\u0006\u0002\u0010]J\u0006\u0010^\u001a\u00020NJ\u0006\u0010_\u001a\u00020NJ\u0006\u0010`\u001a\u00020NJ\u0006\u0010a\u001a\u00020NJ\u0006\u0010b\u001a\u00020NJ\u0006\u0010c\u001a\u00020NJ\u0018\u0010d\u001a\u00020N2\u0006\u0010e\u001a\u00020\u00162\u0006\u0010f\u001a\u00020gH\u0002J\u0006\u0010h\u001a\u00020\u0013J\u0010\u0010i\u001a\u00020N2\b\u0010j\u001a\u0004\u0018\u00010\u0010J\u000e\u0010k\u001a\u00020N2\u0006\u0010l\u001a\u00020\u0010J\u000e\u0010m\u001a\u00020N2\u0006\u0010e\u001a\u00020\u0016J\u000e\u0010n\u001a\u00020N2\u0006\u0010o\u001a\u00020\u001fJ\u000e\u0010p\u001a\u00020N2\u0006\u0010l\u001a\u00020\u0010J\u0006\u0010q\u001a\u00020NJ\u0006\u0010r\u001a\u00020NJ\u0006\u0010s\u001a\u00020NJ\u0006\u0010t\u001a\u00020NJ\b\u0010u\u001a\u00020NH\u0002J\u000e\u0010v\u001a\u00020N2\u0006\u0010Y\u001a\u00020\u0010J\u000e\u0010w\u001a\u00020N2\u0006\u0010W\u001a\u000208R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0#0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0019\u0010\'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010&R#\u0010)\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010&R\u001d\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0#0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010&R\u001d\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160#0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010&R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u00130\"\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0#0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010&R\u0017\u00103\u001a\b\u0012\u0004\u0012\u00020\u00100\"\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010&R\u001d\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160#0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010&R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002080\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u00109\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\"\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010&R\u0019\u0010;\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\"\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010&R\u0017\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00130\"\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010&R\u0017\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00130\"\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010&R\u0017\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00130\"\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010&R\u0017\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00130\"\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010&R\u0017\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001d0\"\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010G\u001a\b\u0012\u0004\u0012\u00020\u001f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010&R\u0017\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00100\"\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010&R\u001d\u0010K\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160#0\"\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u0010&\u00a8\u0006y"}, d2 = {"Lcom/bjoey/app/ui/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "tableDao", "Lcom/bjoey/app/data/local/dao/TableDao;", "menuDao", "Lcom/bjoey/app/data/local/dao/MenuDao;", "orderDao", "Lcom/bjoey/app/data/local/dao/OrderDao;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lcom/bjoey/app/data/local/dao/TableDao;Lcom/bjoey/app/data/local/dao/MenuDao;Lcom/bjoey/app/data/local/dao/OrderDao;Lkotlinx/coroutines/CoroutineDispatcher;)V", "_completedReceipt", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bjoey/app/ui/dashboard/CompletedReceiptUi;", "_draftCart", "", "", "Lcom/bjoey/app/ui/dashboard/DraftCartItem;", "_isProcessingOrder", "", "_menuSearchQuery", "_pendingGuestCountTable", "Lcom/bjoey/app/ui/dashboard/TableSlotUi;", "_selectedCategoryId", "_showCancelOrderDialog", "_showDiscardDraftDialog", "_showEditGuestCountDialog", "_showPaymentDialog", "_stage", "Lcom/bjoey/app/ui/dashboard/DashboardStage;", "_tableFilter", "Lcom/bjoey/app/ui/dashboard/TableFilter;", "_tableSearchQuery", "categories", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/bjoey/app/ui/settings/menu/MenuCategory;", "getCategories", "()Lkotlinx/coroutines/flow/StateFlow;", "completedReceipt", "getCompletedReceipt", "draftCart", "getDraftCart", "filteredMenuItems", "Lcom/bjoey/app/ui/settings/menu/MenuItem;", "getFilteredMenuItems", "filteredTableSlots", "getFilteredTableSlots", "isProcessingOrder", "menuItems", "getMenuItems", "menuSearchQuery", "getMenuSearchQuery", "occupiedTableSlots", "getOccupiedTableSlots", "originalItemsSnapshot", "", "pendingGuestCountTable", "getPendingGuestCountTable", "selectedCategoryId", "getSelectedCategoryId", "showCancelOrderDialog", "getShowCancelOrderDialog", "showDiscardDraftDialog", "getShowDiscardDraftDialog", "showEditGuestCountDialog", "getShowEditGuestCountDialog", "showPaymentDialog", "getShowPaymentDialog", "stage", "getStage", "tableFilter", "getTableFilter", "tableSearchQuery", "getTableSearchQuery", "tableSlots", "getTableSlots", "addMenuItemToCart", "", "item", "adjustCartItemQuantity", "menuItemId", "delta", "clearDraftCart", "confirmCancelOpenOrder", "confirmDiscardAndBackToTables", "confirmGuestCountAndEnterMenu", "guestCount", "confirmPayment", "shiftSessionId", "paymentMethod", "cashReceived", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V", "dismissCancelOrderDialog", "dismissDiscardDraftDialog", "dismissEditGuestCountDialog", "dismissGuestCountDialog", "dismissPaymentDialog", "dismissReceiptAndReturnToTables", "enterExistingOrder", "slot", "activeOrder", "Lcom/bjoey/app/data/local/entity/DiningOrderWithItems;", "hasUnsavedChanges", "onCategorySelected", "categoryId", "onMenuSearchQueryChange", "query", "onTableClicked", "onTableFilterSelected", "filter", "onTableSearchQueryChange", "openCancelOrderDialog", "openEditGuestCountDialog", "openPaymentDialog", "requestBackToTables", "resetToTableGrid", "submitOrder", "updateActiveGuestCount", "Factory", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bjoey.app.data.local.dao.TableDao tableDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bjoey.app.data.local.dao.MenuDao menuDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bjoey.app.data.local.dao.OrderDao orderDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.dashboard.TableSlotUi>> tableSlots = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _tableSearchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> tableSearchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bjoey.app.ui.dashboard.TableFilter> _tableFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.TableFilter> tableFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.dashboard.TableSlotUi>> filteredTableSlots = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.dashboard.TableSlotUi>> occupiedTableSlots = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuCategory>> categories = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuItem>> menuItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _menuSearchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> menuSearchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedCategoryId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedCategoryId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuItem>> filteredMenuItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bjoey.app.ui.dashboard.DashboardStage> _stage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.DashboardStage> stage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bjoey.app.ui.dashboard.TableSlotUi> _pendingGuestCountTable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.TableSlotUi> pendingGuestCountTable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showEditGuestCountDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showEditGuestCountDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.bjoey.app.ui.dashboard.DraftCartItem>> _draftCart = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.bjoey.app.ui.dashboard.DraftCartItem>> draftCart = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.Map<java.lang.String, java.lang.Integer> originalItemsSnapshot;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showPaymentDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showPaymentDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showCancelOrderDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showCancelOrderDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showDiscardDraftDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showDiscardDraftDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bjoey.app.ui.dashboard.CompletedReceiptUi> _completedReceipt = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.CompletedReceiptUi> completedReceipt = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isProcessingOrder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isProcessingOrder = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.dao.TableDao tableDao, @org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.dao.MenuDao menuDao, @org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.dao.OrderDao orderDao, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.dashboard.TableSlotUi>> getTableSlots() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getTableSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.TableFilter> getTableFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.dashboard.TableSlotUi>> getFilteredTableSlots() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.dashboard.TableSlotUi>> getOccupiedTableSlots() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuCategory>> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuItem>> getMenuItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getMenuSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedCategoryId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuItem>> getFilteredMenuItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.DashboardStage> getStage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.TableSlotUi> getPendingGuestCountTable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowEditGuestCountDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.bjoey.app.ui.dashboard.DraftCartItem>> getDraftCart() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowPaymentDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowCancelOrderDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowDiscardDraftDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bjoey.app.ui.dashboard.CompletedReceiptUi> getCompletedReceipt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isProcessingOrder() {
        return null;
    }
    
    public final void onTableSearchQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onTableFilterSelected(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.ui.dashboard.TableFilter filter) {
    }
    
    public final void onMenuSearchQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onCategorySelected(@org.jetbrains.annotations.Nullable()
    java.lang.String categoryId) {
    }
    
    public final void onTableClicked(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.ui.dashboard.TableSlotUi slot) {
    }
    
    public final void dismissGuestCountDialog() {
    }
    
    public final void confirmGuestCountAndEnterMenu(int guestCount) {
    }
    
    private final void enterExistingOrder(com.bjoey.app.ui.dashboard.TableSlotUi slot, com.bjoey.app.data.local.entity.DiningOrderWithItems activeOrder) {
    }
    
    public final void openEditGuestCountDialog() {
    }
    
    public final void dismissEditGuestCountDialog() {
    }
    
    public final void updateActiveGuestCount(int guestCount) {
    }
    
    public final void addMenuItemToCart(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.ui.settings.menu.MenuItem item) {
    }
    
    public final void adjustCartItemQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String menuItemId, int delta) {
    }
    
    public final void clearDraftCart() {
    }
    
    public final boolean hasUnsavedChanges() {
        return false;
    }
    
    public final void requestBackToTables() {
    }
    
    public final void dismissDiscardDraftDialog() {
    }
    
    public final void confirmDiscardAndBackToTables() {
    }
    
    public final void submitOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String shiftSessionId) {
    }
    
    public final void openPaymentDialog() {
    }
    
    public final void dismissPaymentDialog() {
    }
    
    public final void confirmPayment(@org.jetbrains.annotations.NotNull()
    java.lang.String shiftSessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.Nullable()
    java.lang.Long cashReceived) {
    }
    
    public final void dismissReceiptAndReturnToTables() {
    }
    
    public final void openCancelOrderDialog() {
    }
    
    public final void dismissCancelOrderDialog() {
    }
    
    public final void confirmCancelOpenOrder() {
    }
    
    private final void resetToTableGrid() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ%\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\n0\rH\u0016\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/bjoey/app/ui/dashboard/DashboardViewModel$Factory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "tableDao", "Lcom/bjoey/app/data/local/dao/TableDao;", "menuDao", "Lcom/bjoey/app/data/local/dao/MenuDao;", "orderDao", "Lcom/bjoey/app/data/local/dao/OrderDao;", "(Lcom/bjoey/app/data/local/dao/TableDao;Lcom/bjoey/app/data/local/dao/MenuDao;Lcom/bjoey/app/data/local/dao/OrderDao;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
    public static final class Factory implements androidx.lifecycle.ViewModelProvider.Factory {
        @org.jetbrains.annotations.NotNull()
        private final com.bjoey.app.data.local.dao.TableDao tableDao = null;
        @org.jetbrains.annotations.NotNull()
        private final com.bjoey.app.data.local.dao.MenuDao menuDao = null;
        @org.jetbrains.annotations.NotNull()
        private final com.bjoey.app.data.local.dao.OrderDao orderDao = null;
        
        public Factory(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.dao.TableDao tableDao, @org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.dao.MenuDao menuDao, @org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.local.dao.OrderDao orderDao) {
            super();
        }
        
        @java.lang.Override()
        @kotlin.Suppress(names = {"UNCHECKED_CAST"})
        @org.jetbrains.annotations.NotNull()
        public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull()
        java.lang.Class<T> modelClass) {
            return null;
        }
    }
}