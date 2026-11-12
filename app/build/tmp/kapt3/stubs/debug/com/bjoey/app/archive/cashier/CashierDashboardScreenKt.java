package com.bjoey.app.archive.cashier;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000z\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\r\u001a2\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u001d0!2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001d0$H\u0003\u001a\u001e\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\t2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u001d0$H\u0007\u001a$\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\t2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0!H\u0003\u001ab\u0010+\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u00182\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001d0$2B\u0010.\u001a>\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020100\u00a2\u0006\f\b2\u0012\b\b3\u0012\u0004\b\b(4\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b2\u0012\b\b3\u0012\u0004\b\b(5\u0012\u0004\u0012\u00020\u001d0/H\u0003\u001a.\u00106\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\t2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001d0$H\u0003\u001a\u00ae\u0001\u0010=\u001a\u00020\u001d2\u0006\u0010>\u001a\u00020\"2\u0006\u0010?\u001a\u00020\t2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0!2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00072\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020C2\u0006\u0010E\u001a\u00020C2\u0006\u0010F\u001a\u00020;2\b\u0010G\u001a\u0004\u0018\u00010\t2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u001d0$2\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020I\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u001d0/2\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020I\u0012\u0004\u0012\u00020\u001d0!2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u001d0$H\u0003\u001a\u001e\u0010L\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u00182\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001d0$H\u0003\u001a:\u0010M\u001a\u00020\u001d2\u0006\u0010N\u001a\u00020\t2\u0006\u0010&\u001a\u00020\t2\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0!2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020\u001d0$H\u0003\u001a$\u0010Q\u001a\u00020\u001d2\u0006\u0010R\u001a\u00020\t2\u0012\u0010S\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0!H\u0003\u001a\u000e\u0010T\u001a\u00020\t2\u0006\u0010U\u001a\u00020C\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0005\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"#\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0010\u0010\f\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\r\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u000e\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u000f\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0010\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0011\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0012\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0013\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000b\"\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000b\u00a8\u0006V"}, d2 = {"AccentGreen", "Landroidx/compose/ui/graphics/Color;", "J", "AccentSoft", "BorderHairline", "BorderHairlineStrong", "CATEGORIES", "", "Lkotlin/Pair;", "", "getCATEGORIES", "()Ljava/util/List;", "Neutral100", "Neutral200", "Neutral400", "Neutral50", "Neutral500", "Neutral600", "Neutral700", "Neutral900", "POS_MOD_GROUPS", "Lcom/bjoey/app/archive/cashier/ModifierGroup;", "getPOS_MOD_GROUPS", "POS_PRODUCTS", "Lcom/bjoey/app/archive/cashier/Product;", "getPOS_PRODUCTS", "POS_SUGAR_OPTIONS", "getPOS_SUGAR_OPTIONS", "CartLineItem", "", "line", "Lcom/bjoey/app/archive/cashier/CartLine;", "onQtyChange", "Lkotlin/Function1;", "", "onRemove", "Lkotlin/Function0;", "CashierDashboardScreen", "cashierName", "onLogout", "CategoryTabsView", "activeCat", "onCategorySelect", "ModifierSheetContent", "product", "onClose", "onAddToCart", "Lkotlin/Function2;", "", "Lcom/bjoey/app/archive/cashier/ModifierOption;", "Lkotlin/ParameterName;", "name", "selectedMods", "qty", "NavButton", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "isActive", "", "onClick", "OrderPanelView", "totalItems", "orderType", "onOrderTypeChange", "cart", "subtotal", "", "tax", "total", "isPaying", "paidMessage", "onClearCart", "", "onRemoveItem", "onPayNow", "ProductCard", "SidebarView", "activeNav", "onNavClick", "onAvatarClick", "TopBarView", "searchQuery", "onSearchQueryChange", "fmtPrice", "amount", "app_debug"})
public final class CashierDashboardScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.bjoey.app.archive.cashier.Product> POS_PRODUCTS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.bjoey.app.archive.cashier.ModifierGroup> POS_MOD_GROUPS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> POS_SUGAR_OPTIONS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> CATEGORIES = null;
    private static final long BorderHairline = 0L;
    private static final long BorderHairlineStrong = 0L;
    private static final long Neutral50 = 0L;
    private static final long Neutral100 = 0L;
    private static final long Neutral200 = 0L;
    private static final long Neutral400 = 0L;
    private static final long Neutral500 = 0L;
    private static final long Neutral600 = 0L;
    private static final long Neutral700 = 0L;
    private static final long Neutral900 = 0L;
    private static final long AccentGreen = 0L;
    private static final long AccentSoft = 0L;
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.bjoey.app.archive.cashier.Product> getPOS_PRODUCTS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.bjoey.app.archive.cashier.ModifierGroup> getPOS_MOD_GROUPS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<java.lang.String> getPOS_SUGAR_OPTIONS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getCATEGORIES() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String fmtPrice(double amount) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CashierDashboardScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String cashierName, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SidebarView(java.lang.String activeNav, java.lang.String cashierName, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAvatarClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NavButton(java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon, boolean isActive, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TopBarView(java.lang.String searchQuery, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearchQueryChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CategoryTabsView(java.lang.String activeCat, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCategorySelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProductCard(com.bjoey.app.archive.cashier.Product product, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OrderPanelView(int totalItems, java.lang.String orderType, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOrderTypeChange, java.util.List<com.bjoey.app.archive.cashier.CartLine> cart, double subtotal, double tax, double total, boolean isPaying, java.lang.String paidMessage, kotlin.jvm.functions.Function0<kotlin.Unit> onClearCart, kotlin.jvm.functions.Function2<? super java.lang.Long, ? super java.lang.Integer, kotlin.Unit> onQtyChange, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onRemoveItem, kotlin.jvm.functions.Function0<kotlin.Unit> onPayNow) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CartLineItem(com.bjoey.app.archive.cashier.CartLine line, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onQtyChange, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ModifierSheetContent(com.bjoey.app.archive.cashier.Product product, kotlin.jvm.functions.Function0<kotlin.Unit> onClose, kotlin.jvm.functions.Function2<? super java.util.Map<java.lang.String, com.bjoey.app.archive.cashier.ModifierOption>, ? super java.lang.Integer, kotlin.Unit> onAddToCart) {
    }
}