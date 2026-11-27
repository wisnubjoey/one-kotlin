package com.bjoey.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bjoey.app.data.local.entity.ShiftEntity
import com.bjoey.app.data.local.entity.ShiftSessionEntity

@Dao
interface ShiftDao {
    @Query("SELECT COUNT(*) FROM shifts")
    suspend fun countShifts(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShifts(shifts: List<ShiftEntity>)

    @Query("SELECT * FROM shifts WHERE pin = :pin AND isActive = 1 LIMIT 1")
    suspend fun findActiveShiftByPin(pin: String): ShiftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ShiftSessionEntity)

    @Query("UPDATE shift_sessions SET isSynced = 1 WHERE id = :sessionId")
    suspend fun markSessionAsSynced(sessionId: String)

    @Query("UPDATE shift_sessions SET closedAt = :closedAt WHERE closedAt IS NULL")
    suspend fun closeOpenSessions(closedAt: Long = System.currentTimeMillis())
}
