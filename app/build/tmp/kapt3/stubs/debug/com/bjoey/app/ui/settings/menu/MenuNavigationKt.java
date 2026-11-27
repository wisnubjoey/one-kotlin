package com.bjoey.app.ui.settings.menu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a6\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u000eH\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"ARG_ITEM_ID", "", "MENU_FORM_BASE_ROUTE", "MENU_FORM_ROUTE", "MENU_LIST_ROUTE", "MenuNavigation", "", "menuDao", "Lcom/bjoey/app/data/local/dao/MenuDao;", "modifier", "Landroidx/compose/ui/Modifier;", "viewModel", "Lcom/bjoey/app/ui/settings/menu/MenuViewModel;", "onBackClick", "Lkotlin/Function0;", "app_debug"})
public final class MenuNavigationKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MENU_LIST_ROUTE = "menu_list";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MENU_FORM_BASE_ROUTE = "menu_form";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_ITEM_ID = "itemId";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MENU_FORM_ROUTE = "menu_form?itemId={itemId}";
    
    @androidx.compose.runtime.Composable()
    public static final void MenuNavigation(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.dao.MenuDao menuDao, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.bjoey.app.ui.settings.menu.MenuViewModel viewModel, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick) {
    }
}