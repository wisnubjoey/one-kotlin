package com.bjoey.app.ui.settings.history;

/**
 * Order history domain model backed by Room `dining_orders` and `dining_order_items`.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b.\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0093\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\f\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\u0011\u001a\u00020\f\u0012\u0006\u0010\u0012\u001a\u00020\f\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\f\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u00a2\u0006\u0002\u0010\u0017J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\fH\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00105\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u00106\u001a\u00020\fH\u00c6\u0003J\t\u00107\u001a\u00020\fH\u00c6\u0003J\u0010\u00108\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u000f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u00c6\u0003J\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0005H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\fH\u00c6\u0003J\t\u0010A\u001a\u00020\fH\u00c6\u0003J\u00bc\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0012\u001a\u00020\f2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\f2\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u00c6\u0001\u00a2\u0006\u0002\u0010CJ\u0013\u0010D\u001a\u00020E2\b\u0010F\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010G\u001a\u00020\u0005H\u00d6\u0001J\t\u0010H\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001b\u0010\u0019R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010!R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010$R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010!R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010!R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010!R\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001eR\u0011\u0010/\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b0\u0010$R\u0011\u0010\u0012\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001e\u00a8\u0006I"}, d2 = {"Lcom/bjoey/app/ui/settings/history/OrderHistoryEntry;", "", "id", "", "orderNumber", "", "tableId", "tableName", "guestCount", "status", "paymentMethod", "subtotal", "", "taxAmount", "grandTotal", "cashReceived", "changeAmount", "createdAt", "updatedAt", "closedAt", "items", "", "Lcom/bjoey/app/ui/settings/history/OrderHistoryItem;", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;JJJLjava/lang/Long;Ljava/lang/Long;JJLjava/lang/Long;Ljava/util/List;)V", "getCashReceived", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getChangeAmount", "getClosedAt", "getCreatedAt", "()J", "displayOrderNumber", "getDisplayOrderNumber", "()Ljava/lang/String;", "getGrandTotal", "getGuestCount", "()I", "getId", "getItems", "()Ljava/util/List;", "getOrderNumber", "getPaymentMethod", "getStatus", "getSubtotal", "getTableId", "getTableName", "getTaxAmount", "totalItemQuantity", "getTotalItemQuantity", "getUpdatedAt", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;JJJLjava/lang/Long;Ljava/lang/Long;JJLjava/lang/Long;Ljava/util/List;)Lcom/bjoey/app/ui/settings/history/OrderHistoryEntry;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class OrderHistoryEntry {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    private final int orderNumber = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tableId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tableName = null;
    private final int guestCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String paymentMethod = null;
    private final long subtotal = 0L;
    private final long taxAmount = 0L;
    private final long grandTotal = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long cashReceived = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long changeAmount = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long closedAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bjoey.app.ui.settings.history.OrderHistoryItem> items = null;
    
    public OrderHistoryEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String id, int orderNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String tableId, @org.jetbrains.annotations.NotNull()
    java.lang.String tableName, int guestCount, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String paymentMethod, long subtotal, long taxAmount, long grandTotal, @org.jetbrains.annotations.Nullable()
    java.lang.Long cashReceived, @org.jetbrains.annotations.Nullable()
    java.lang.Long changeAmount, long createdAt, long updatedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long closedAt, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.ui.settings.history.OrderHistoryItem> items) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final int getOrderNumber() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTableId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTableName() {
        return null;
    }
    
    public final int getGuestCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPaymentMethod() {
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
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getClosedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.ui.settings.history.OrderHistoryItem> getItems() {
        return null;
    }
    
    public final int getTotalItemQuantity() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayOrderNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final long component10() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.bjoey.app.ui.settings.history.OrderHistoryItem> component16() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    public final long component8() {
        return 0L;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bjoey.app.ui.settings.history.OrderHistoryEntry copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, int orderNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String tableId, @org.jetbrains.annotations.NotNull()
    java.lang.String tableName, int guestCount, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String paymentMethod, long subtotal, long taxAmount, long grandTotal, @org.jetbrains.annotations.Nullable()
    java.lang.Long cashReceived, @org.jetbrains.annotations.Nullable()
    java.lang.Long changeAmount, long createdAt, long updatedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long closedAt, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.ui.settings.history.OrderHistoryItem> items) {
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