package com.bjoey.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\u0012"}, d2 = {"Lcom/bjoey/app/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "lockActiveSessions", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markSessionAsSynced", "sessionId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "menuDao", "Lcom/bjoey/app/data/MenuDao;", "shiftDao", "Lcom/bjoey/app/data/ShiftDao;", "verifyPinAndStartSession", "Lcom/bjoey/app/data/StartedShiftSession;", "pin", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.bjoey.app.data.ShiftEntity.class, com.bjoey.app.data.ShiftSessionEntity.class, com.bjoey.app.data.MenuCategoryEntity.class, com.bjoey.app.data.MenuItemEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.bjoey.app.data.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAT_COFFEE_ID = "cat-coffee";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAT_NON_COFFEE_ID = "cat-non-coffee";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAT_PASTRY_ID = "cat-pastry";
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_2_3 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bjoey.app.data.ShiftDao shiftDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bjoey.app.data.MenuDao menuDao();
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object verifyPinAndStartSession(@org.jetbrains.annotations.NotNull()
    java.lang.String pin, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bjoey.app.data.StartedShiftSession> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markSessionAsSynced(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object lockActiveSessions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eJ\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eJ\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2 = {"Lcom/bjoey/app/data/AppDatabase$Companion;", "", "()V", "CAT_COFFEE_ID", "", "CAT_NON_COFFEE_ID", "CAT_PASTRY_ID", "INSTANCE", "Lcom/bjoey/app/data/AppDatabase;", "MIGRATION_2_3", "Landroidx/room/migration/Migration;", "getMIGRATION_2_3", "()Landroidx/room/migration/Migration;", "defaultMenuCategories", "", "Lcom/bjoey/app/data/MenuCategoryEntity;", "defaultMenuItems", "Lcom/bjoey/app/data/MenuItemEntity;", "defaultShifts", "Lcom/bjoey/app/data/ShiftEntity;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bjoey.app.data.ShiftEntity> defaultShifts() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bjoey.app.data.MenuCategoryEntity> defaultMenuCategories() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bjoey.app.data.MenuItemEntity> defaultMenuItems() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.room.migration.Migration getMIGRATION_2_3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bjoey.app.data.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}