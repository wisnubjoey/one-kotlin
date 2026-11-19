package com.bjoey.app.ui.settings.menu

import androidx.compose.ui.text.AnnotatedString
import org.junit.Assert.assertEquals
import org.junit.Test

class MenuItemHelpersTest {

    @Test
    fun formatRupiah_formatsWithDotThousandSeparators() {
        assertEquals("Rp 0", formatRupiah(0L))
        assertEquals("Rp 500", formatRupiah(500L))
        assertEquals("Rp 25.000", formatRupiah(25_000L))
        assertEquals("Rp 28.000", formatRupiah(28_000L))
        assertEquals("Rp 1.250.000", formatRupiah(1_250_000L))
    }

    @Test
    fun thousandSeparatorTransformation_formatsDigitsAndMapsOffsets() {
        val transformation = ThousandSeparatorTransformation()

        val emptyResult = transformation.filter(AnnotatedString(""))
        assertEquals("", emptyResult.text.text)

        val result = transformation.filter(AnnotatedString("28000"))
        assertEquals("28.000", result.text.text)
        assertEquals(0, result.offsetMapping.originalToTransformed(0))
        assertEquals(1, result.offsetMapping.originalToTransformed(1))
        assertEquals(3, result.offsetMapping.originalToTransformed(2))
        assertEquals(6, result.offsetMapping.originalToTransformed(5))

        assertEquals(0, result.offsetMapping.transformedToOriginal(0))
        assertEquals(2, result.offsetMapping.transformedToOriginal(2))
        assertEquals(2, result.offsetMapping.transformedToOriginal(3))
        assertEquals(5, result.offsetMapping.transformedToOriginal(6))

        val millionResult = transformation.filter(AnnotatedString("1250000"))
        assertEquals("1.250.000", millionResult.text.text)
        assertEquals(9, millionResult.offsetMapping.originalToTransformed(7))
        assertEquals(7, millionResult.offsetMapping.transformedToOriginal(9))
    }
}
