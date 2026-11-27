package com.bjoey.app.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u0014H\'J\u0016\u0010\u0015\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u0016"}, d2 = {"Lcom/bjoey/app/data/local/dao/TableDao;", "", "countTables", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTableById", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTableById", "Lcom/bjoey/app/data/local/entity/DiningTableEntity;", "insertTable", "table", "(Lcom/bjoey/app/data/local/entity/DiningTableEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertTables", "tables", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeTables", "Lkotlinx/coroutines/flow/Flow;", "updateTable", "app_debug"})
@androidx.room.Dao()
public abstract interface TableDao {
    
    @androidx.room.Query(value = "SELECT * FROM dining_tables ORDER BY updatedAt DESC, name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity>> observeTables();
    
    @androidx.room.Query(value = "SELECT * FROM dining_tables WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTableById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bjoey.app.data.local.entity.DiningTableEntity> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM dining_tables")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countTables(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTable(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.entity.DiningTableEntity table, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTables(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.data.local.entity.DiningTableEntity> tables, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTable(@org.jetbrains.annotations.NotNull()
    com.bjoey.app.data.local.entity.DiningTableEntity table, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM dining_tables WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTableById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}