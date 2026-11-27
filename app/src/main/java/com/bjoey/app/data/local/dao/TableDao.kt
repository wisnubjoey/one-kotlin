package com.bjoey.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bjoey.app.data.local.entity.DiningTableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {
    @Query("SELECT * FROM dining_tables ORDER BY updatedAt DESC, name ASC")
    fun observeTables(): Flow<List<DiningTableEntity>>

    @Query("SELECT * FROM dining_tables WHERE id = :id LIMIT 1")
    suspend fun getTableById(id: String): DiningTableEntity?

    @Query("SELECT COUNT(*) FROM dining_tables")
    suspend fun countTables(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(table: DiningTableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTables(tables: List<DiningTableEntity>)

    @Update
    suspend fun updateTable(table: DiningTableEntity)

    @Query("DELETE FROM dining_tables WHERE id = :id")
    suspend fun deleteTableById(id: String)
}
