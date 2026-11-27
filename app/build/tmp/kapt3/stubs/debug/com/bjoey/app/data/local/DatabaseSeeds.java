package com.bjoey.app.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bJ\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/bjoey/app/data/local/DatabaseSeeds;", "", "()V", "CAT_COFFEE_ID", "", "CAT_NON_COFFEE_ID", "CAT_PASTRY_ID", "defaultMenuCategories", "", "Lcom/bjoey/app/data/local/entity/MenuCategoryEntity;", "defaultMenuItems", "Lcom/bjoey/app/data/local/entity/MenuItemEntity;", "defaultShifts", "Lcom/bjoey/app/data/local/entity/ShiftEntity;", "defaultTables", "Lcom/bjoey/app/data/local/entity/DiningTableEntity;", "app_debug"})
public final class DatabaseSeeds {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAT_COFFEE_ID = "cat-coffee";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAT_NON_COFFEE_ID = "cat-non-coffee";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAT_PASTRY_ID = "cat-pastry";
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.data.local.DatabaseSeeds INSTANCE = null;
    
    private DatabaseSeeds() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.data.local.entity.ShiftEntity> defaultShifts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.data.local.entity.MenuCategoryEntity> defaultMenuCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.data.local.entity.MenuItemEntity> defaultMenuItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity> defaultTables() {
        return null;
    }
}