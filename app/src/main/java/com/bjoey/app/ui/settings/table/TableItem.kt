package com.bjoey.app.ui.settings.table

import androidx.compose.ui.graphics.Color

/**
 * High-contrast monochrome + deep green POS design tokens for Table Management.
 */
object TableColors {
    val ScreenBackground = Color(0xFFF9FAFB)
    val CardSurface = Color(0xFFFFFFFF)
    val BorderDefault = Color(0xFFE5E7EB)
    val BorderFocused = Color(0xFF111111)
    val TextPrimary = Color(0xFF111111)
    val TextSecondary = Color(0xFF6B7280)
    val PrimaryAccent = Color(0xFF14532D)
    val Destructive = Color(0xFFDC2626)
    val PlaceholderBackground = Color(0xFFF3F4F6)
    val BadgeBackground = Color(0xFFF3F4F6)
}

/**
 * Table domain model backed by Room `dining_tables`.
 */
data class TableItem(
    val id: String,
    val name: String
)

/**
 * Normalizes a table name by trimming leading and trailing whitespace.
 */
fun normalizeTableName(raw: String): String = raw.trim()

/**
 * Returns true if the normalized table name is non-empty.
 */
fun isValidTableName(raw: String): Boolean = normalizeTableName(raw).isNotEmpty()
