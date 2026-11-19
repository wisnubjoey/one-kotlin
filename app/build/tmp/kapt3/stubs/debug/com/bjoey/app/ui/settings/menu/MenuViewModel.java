package com.bjoey.app.ui.settings.menu;

/**
 * Room-backed ViewModel managing POS menu categories, menu items, and live filtering.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0001,B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\t2\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001b0\u001eJ(\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\tJ\u000e\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\tJ\u0010\u0010\'\u001a\u0004\u0018\u00010\u00122\u0006\u0010&\u001a\u00020\tJ\u0010\u0010(\u001a\u00020\u001b2\b\u0010#\u001a\u0004\u0018\u00010\tJ\u000e\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\tJ0\u0010+\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\t2\u0006\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\tR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0019\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010\u00a8\u0006-"}, d2 = {"Lcom/bjoey/app/ui/settings/menu/MenuViewModel;", "Landroidx/lifecycle/ViewModel;", "menuDao", "Lcom/bjoey/app/data/MenuDao;", "ioDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lcom/bjoey/app/data/MenuDao;Lkotlinx/coroutines/CoroutineDispatcher;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedCategoryId", "categories", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/bjoey/app/ui/settings/menu/MenuCategory;", "getCategories", "()Lkotlinx/coroutines/flow/StateFlow;", "filteredMenuItems", "Lcom/bjoey/app/ui/settings/menu/MenuItem;", "getFilteredMenuItems", "menuItems", "getMenuItems", "searchQuery", "getSearchQuery", "selectedCategoryId", "getSelectedCategoryId", "addCategory", "", "name", "onCreated", "Lkotlin/Function1;", "addMenuItem", "title", "price", "", "categoryId", "imageUri", "deleteMenuItem", "id", "getMenuItemById", "onCategorySelected", "onSearchQueryChange", "query", "updateMenuItem", "Factory", "app_debug"})
public final class MenuViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bjoey.app.data.MenuDao menuDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineDispatcher ioDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuCategory>> categories = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuItem>> menuItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedCategoryId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedCategoryId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bjoey.app.ui.settings.menu.MenuItem>> filteredMenuItems = null;
    
    public MenuViewModel(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.MenuDao menuDao, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher ioDispatcher) {
        super();
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
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
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
    
    public final void onSearchQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onCategorySelected(@org.jetbrains.annotations.Nullable()
    java.lang.String categoryId) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bjoey.app.ui.settings.menu.MenuItem getMenuItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    public final void addCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.bjoey.app.ui.settings.menu.MenuCategory, kotlin.Unit> onCreated) {
    }
    
    public final void addMenuItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, long price, @org.jetbrains.annotations.NotNull()
    java.lang.String categoryId, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUri) {
    }
    
    public final void updateMenuItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, long price, @org.jetbrains.annotations.NotNull()
    java.lang.String categoryId, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUri) {
    }
    
    public final void deleteMenuItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u0002H\u0006\"\b\b\u0000\u0010\u0006*\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00060\tH\u0016\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/bjoey/app/ui/settings/menu/MenuViewModel$Factory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "menuDao", "Lcom/bjoey/app/data/MenuDao;", "(Lcom/bjoey/app/data/MenuDao;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
    public static final class Factory implements androidx.lifecycle.ViewModelProvider.Factory {
        @org.jetbrains.annotations.NotNull()
        private final com.bjoey.app.data.MenuDao menuDao = null;
        
        public Factory(@org.jetbrains.annotations.NotNull()
        com.bjoey.app.data.MenuDao menuDao) {
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