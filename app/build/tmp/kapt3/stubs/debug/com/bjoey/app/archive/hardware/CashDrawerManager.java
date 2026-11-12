package com.bjoey.app.archive.hardware;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\n2\b\b\u0002\u0010\u0019\u001a\u00020\nR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/bjoey/app/archive/hardware/CashDrawerManager;", "", "()V", "ESC_POS_KICK_PIN2", "", "getESC_POS_KICK_PIN2", "()[B", "ESC_POS_KICK_PIN5", "getESC_POS_KICK_PIN5", "TAG", "", "autoKickOnPay", "", "getAutoKickOnPay", "()Z", "setAutoKickOnPay", "(Z)V", "soundFeedbackEnabled", "getSoundFeedbackEnabled", "setSoundFeedbackEnabled", "toneGenerator", "Landroid/media/ToneGenerator;", "kickDrawer", "Lcom/bjoey/app/archive/hardware/DrawerEvent;", "source", "note", "app_debug"})
public final class CashDrawerManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CashDrawerManager";
    @org.jetbrains.annotations.NotNull()
    private static final byte[] ESC_POS_KICK_PIN2 = {(byte)27, (byte)112, (byte)0, (byte)25, (byte)-6};
    @org.jetbrains.annotations.NotNull()
    private static final byte[] ESC_POS_KICK_PIN5 = {(byte)27, (byte)112, (byte)1, (byte)25, (byte)-6};
    private static boolean autoKickOnPay = true;
    private static boolean soundFeedbackEnabled = true;
    @org.jetbrains.annotations.Nullable()
    private static android.media.ToneGenerator toneGenerator;
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.archive.hardware.CashDrawerManager INSTANCE = null;
    
    private CashDrawerManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] getESC_POS_KICK_PIN2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] getESC_POS_KICK_PIN5() {
        return null;
    }
    
    public final boolean getAutoKickOnPay() {
        return false;
    }
    
    public final void setAutoKickOnPay(boolean p0) {
    }
    
    public final boolean getSoundFeedbackEnabled() {
        return false;
    }
    
    public final void setSoundFeedbackEnabled(boolean p0) {
    }
    
    /**
     * Membuka laci uang (mengirim pulsa listrik ke solenoid laci).
     */
    @org.jetbrains.annotations.NotNull()
    public final com.bjoey.app.archive.hardware.DrawerEvent kickDrawer(@org.jetbrains.annotations.NotNull()
    java.lang.String source, @org.jetbrains.annotations.NotNull()
    java.lang.String note) {
        return null;
    }
}