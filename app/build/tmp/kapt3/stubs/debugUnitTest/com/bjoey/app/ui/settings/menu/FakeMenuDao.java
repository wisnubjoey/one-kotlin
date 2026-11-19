package com.bjoey.app.ui.settings.menu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u000b\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0017\u001a\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0096@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0004H\u0096@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u001c\u0010 \u001a\u00020\u00102\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u0096@\u00a2\u0006\u0002\u0010\u0019J\u0014\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030#H\u0016J\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u00030#H\u0016J\u0016\u0010&\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u001fR\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00030\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/bjoey/app/ui/settings/menu/FakeMenuDao;", "Lcom/bjoey/app/data/MenuDao;", "initialCategories", "", "Lcom/bjoey/app/data/MenuCategoryEntity;", "initialItems", "Lcom/bjoey/app/data/MenuItemEntity;", "(Ljava/util/List;Ljava/util/List;)V", "categoriesFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "itemsFlow", "countCategories", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "countMenuItems", "deleteCategoryById", "", "categoryId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMenuItemById", "id", "getMenuItemById", "insertCategories", "categories", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCategory", "category", "(Lcom/bjoey/app/data/MenuCategoryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertMenuItem", "item", "(Lcom/bjoey/app/data/MenuItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertMenuItems", "items", "observeCategories", "Lkotlinx/coroutines/flow/Flow;", "observeMenuItemsWithCategory", "Lcom/bjoey/app/data/MenuItemWithCategory;", "updateMenuItem", "app_debugUnitTest"})
final class FakeMenuDao implements com.bjoey.app.data.MenuDao {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.MenuCategoryEntity>> categoriesFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bjoey.app.data.MenuItemEntity>> itemsFlow = null;
    
    public FakeMenuDao(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.MenuCategoryEntity> initialCategories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.MenuItemEntity> initialItems) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.MenuCategoryEntity>> observeCategories() {
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
    com.bjoey.app.data.MenuCategoryEntity category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertCategories(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.MenuCategoryEntity> categories, @org.jetbrains.annotations.NotNull()
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
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.MenuItemWithCategory>> observeMenuItemsWithCategory() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getMenuItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bjoey.app.data.MenuItemEntity> $completion) {
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
    com.bjoey.app.data.MenuItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertMenuItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.MenuItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateMenuItem(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.MenuItemEntity item, @org.jetbrains.annotations.NotNull()
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
    
    public FakeMenuDao() {
        super();
    }
}