package com.bjoey.app.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/bjoey/app/network/ShiftSyncManager;", "", "serverConfigManager", "Lcom/bjoey/app/data/ServerConfigManager;", "database", "Lcom/bjoey/app/data/AppDatabase;", "(Lcom/bjoey/app/data/ServerConfigManager;Lcom/bjoey/app/data/AppDatabase;)V", "httpClient", "Lokhttp3/OkHttpClient;", "syncShiftSession", "Lcom/bjoey/app/network/SyncResult;", "startedSession", "Lcom/bjoey/app/data/StartedShiftSession;", "(Lcom/bjoey/app/data/StartedShiftSession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class ShiftSyncManager {
    @org.jetbrains.annotations.NotNull()
    private final com.bjoey.app.data.ServerConfigManager serverConfigManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bjoey.app.data.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ShiftSyncManager";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_KEY = "pos-secret-123";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DEVICE_ID = "Tablet-POS-1";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.MediaType JSON_MEDIA_TYPE = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.network.ShiftSyncManager.Companion Companion = null;
    
    public ShiftSyncManager(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.ServerConfigManager serverConfigManager, @org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.AppDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncShiftSession(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.StartedShiftSession startedSession, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bjoey.app.network.SyncResult> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/bjoey/app/network/ShiftSyncManager$Companion;", "", "()V", "API_KEY", "", "DEVICE_ID", "JSON_MEDIA_TYPE", "Lokhttp3/MediaType;", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}