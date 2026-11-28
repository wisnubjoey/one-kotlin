package com.bjoey.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\"\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\f\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0011J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\t\u0010(\u001a\u00020\fH\u00c6\u0003J\t\u0010)\u001a\u00020\fH\u00c6\u0003J\t\u0010*\u001a\u00020\fH\u00c6\u0003J\u0010\u0010+\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J|\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\fH\u00c6\u0001\u00a2\u0006\u0002\u0010-J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0003H\u00d6\u0001J\t\u00102\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017\u00a8\u00063"}, d2 = {"Lcom/bjoey/app/ui/dashboard/CompletedReceiptUi;", "", "orderNumber", "", "tableName", "", "guestCount", "paymentMethod", "items", "", "Lcom/bjoey/app/ui/dashboard/DraftCartItem;", "subtotal", "", "taxAmount", "grandTotal", "cashReceived", "changeAmount", "(ILjava/lang/String;ILjava/lang/String;Ljava/util/List;JJJLjava/lang/Long;Ljava/lang/Long;)V", "getCashReceived", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getChangeAmount", "getGrandTotal", "()J", "getGuestCount", "()I", "getItems", "()Ljava/util/List;", "getOrderNumber", "getPaymentMethod", "()Ljava/lang/String;", "getSubtotal", "getTableName", "getTaxAmount", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ILjava/lang/String;ILjava/lang/String;Ljava/util/List;JJJLjava/lang/Long;Ljava/lang/Long;)Lcom/bjoey/app/ui/dashboard/CompletedReceiptUi;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class CompletedReceiptUi {
    private final int orderNumber = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tableName = null;
    private final int guestCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String paymentMethod = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bjoey.app.ui.dashboard.DraftCartItem> items = null;
    private final long subtotal = 0L;
    private final long taxAmount = 0L;
    private final long grandTotal = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long cashReceived = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long changeAmount = null;
    
    public CompletedReceiptUi(int orderNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String tableName, int guestCount, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.ui.dashboard.DraftCartItem> items, long subtotal, long taxAmount, long grandTotal, @org.jetbrains.annotations.Nullable()
    java.lang.Long cashReceived, @org.jetbrains.annotations.Nullable()
    java.lang.Long changeAmount) {
        super();
    }
    
    public final int getOrderNumber() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTableName() {
        return null;
    }
    
    public final int getGuestCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPaymentMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.ui.dashboard.DraftCartItem> getItems() {
        return null;
    }
    
    public final long getSubtotal() {
        return 0L;
    }
    
    public final long getTaxAmount() {
        return 0L;
    }
    
    public final long getGrandTotal() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCashReceived() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getChangeAmount() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.ui.dashboard.DraftCartItem> component5() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bjoey.app.ui.dashboard.CompletedReceiptUi copy(int orderNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String tableName, int guestCount, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.ui.dashboard.DraftCartItem> items, long subtotal, long taxAmount, long grandTotal, @org.jetbrains.annotations.Nullable()
    java.lang.Long cashReceived, @org.jetbrains.annotations.Nullable()
    java.lang.Long changeAmount) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}