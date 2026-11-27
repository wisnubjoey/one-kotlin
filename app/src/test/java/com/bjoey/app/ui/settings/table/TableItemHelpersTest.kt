package com.bjoey.app.ui.settings.table

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TableItemHelpersTest {

    @Test
    fun normalizeTableName_trimsWhitespace() {
        assertEquals("Meja 01", normalizeTableName("  Meja 01  "))
        assertEquals("", normalizeTableName("   "))
    }

    @Test
    fun isValidTableName_rejectsBlankStrings() {
        assertFalse(isValidTableName(""))
        assertFalse(isValidTableName("   "))
        assertTrue(isValidTableName("Meja 01"))
        assertTrue(isValidTableName(" A1 "))
    }
}
