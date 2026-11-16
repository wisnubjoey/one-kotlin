package com.bjoey.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u00a7\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"HeartbeatMonitorScreen", "", "session", "Lcom/bjoey/app/data/StartedShiftSession;", "baseUrl", "", "isOnline", "", "lastSyncTime", "countdown", "", "isPaused", "isSyncing", "logHistory", "", "Lcom/bjoey/app/ui/SyncLogItem;", "onTogglePause", "Lkotlin/Function1;", "onManualSync", "Lkotlin/Function0;", "onSaveBaseUrl", "onBackClick", "onLockClick", "(Lcom/bjoey/app/data/StartedShiftSession;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;IZZLjava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "app_debug"})
public final class HeartbeatMonitorScreenKt {
    
    /**
     * Dedicated screen for monitoring server heartbeat connection, manual sync,
     * pause simulation (PAUSED by default), and live sync log console.
     */
    @androidx.compose.runtime.Composable()
    public static final void HeartbeatMonitorScreen(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.StartedShiftSession session, @org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean isOnline, @org.jetbrains.annotations.NotNull()
    java.lang.String lastSyncTime, int countdown, boolean isPaused, boolean isSyncing, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.ui.SyncLogItem> logHistory, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onTogglePause, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onManualSync, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSaveBaseUrl, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLockClick) {
    }
}