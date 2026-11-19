package com.bjoey.app.ui.settings.menu

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

/**
 * High-contrast monochrome + deep green POS design tokens for Menu Management.
 */
object MenuColors {
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
 * Menu category domain model backed by Room `menu_categories`.
 */
data class MenuCategory(
    val id: String,
    val name: String
)

/**
 * MenuItem model backed by Room `menu_items` with its resolved category.
 */
data class MenuItem(
    val id: String,
    val title: String,
    val price: Long,
    val categoryId: String,
    val categoryName: String,
    val imageUri: String? = null
)

private val indonesianNumberFormat: NumberFormat by lazy {
    (NumberFormat.getNumberInstance(Locale("in", "ID")) as DecimalFormat).apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale("in", "ID")).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        isGroupingUsed = true
        maximumFractionDigits = 0
    }
}

/**
 * Formats a numeric amount into Indonesian Rupiah with dot thousand separators (e.g. "Rp 28.000").
 */
fun formatRupiah(amount: Long): String {
    return "Rp ${indonesianNumberFormat.format(amount)}"
}

/**
 * Formats raw digit strings (e.g. "28000") into dot-grouped numbers ("28.000")
 * while maintaining accurate cursor mapping.
 */
class ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        if (digits.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val formatted = formatDigitsWithDots(digits)
        val totalDots = (digits.length - 1) / 3

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val clamped = offset.coerceIn(0, digits.length)
                val digitsAfter = digits.length - clamped
                val dotsAfter = if (digitsAfter == 0) 0 else (digitsAfter - 1) / 3
                val dotsBefore = totalDots - dotsAfter
                return (clamped + dotsBefore).coerceIn(0, formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val clamped = offset.coerceIn(0, formatted.length)
                var digitCount = 0
                for (i in 0 until clamped) {
                    if (formatted[i] != '.') {
                        digitCount++
                    }
                }
                return digitCount.coerceIn(0, digits.length)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }

    companion object {
        fun formatDigitsWithDots(digits: String): String {
            if (digits.isEmpty()) return ""
            val sb = StringBuilder(digits.length + digits.length / 3)
            var groupCounter = 0
            for (i in digits.length - 1 downTo 0) {
                if (groupCounter > 0 && groupCounter % 3 == 0) {
                    sb.append('.')
                }
                sb.append(digits[i])
                groupCounter++
            }
            return sb.reverse().toString()
        }
    }
}
