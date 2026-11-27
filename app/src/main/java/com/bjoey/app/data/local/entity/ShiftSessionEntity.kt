package com.bjoey.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "shift_sessions")
data class ShiftSessionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val shiftId: Int,
    val openedAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null,
    val isSynced: Boolean = false
)

data class StartedShiftSession(
    val shift: ShiftEntity,
    val session: ShiftSessionEntity
)
