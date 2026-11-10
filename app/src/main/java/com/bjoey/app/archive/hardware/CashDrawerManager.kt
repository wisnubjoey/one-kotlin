package com.bjoey.app.archive.hardware

import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ============================================================================
 * [ARCHIVE] PANDA CASH DRAWER HARDWARE DRIVER & SIMULATION MANAGER
 * ============================================================================
 * Pada hardware POS sesungguhnya, laci uang (cash drawer) dikendalikan melalui:
 * 1. Port RJ11/RJ12 yang dicolokkan ke Printer Thermal (Epson/Star/POS58/POS80).
 * 2. Modul USB Cash Drawer Trigger (seperti Panda POS USB Trigger Box).
 *
 * Standar perintah yang dikirim adalah perintah ESC/POS:
 * - ESC p m t1 t2: 0x1B 0x70 [pin] [on_time] [off_time]
 * ============================================================================
 */
data class DrawerEvent(
    val id: Long = System.currentTimeMillis(),
    val timestamp: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
    val source: String,
    val note: String
)

object CashDrawerManager {
    private const val TAG = "CashDrawerManager"

    // Perintah byte standar industri ESC/POS untuk membuka laci kasir (Pin 2 / Pin 5)
    val ESC_POS_KICK_PIN2 = byteArrayOf(0x1B, 0x70, 0x00, 0x19, 0xFA.toByte())
    val ESC_POS_KICK_PIN5 = byteArrayOf(0x1B, 0x70, 0x01, 0x19, 0xFA.toByte())

    // Konfigurasi
    var autoKickOnPay: Boolean = true
    var soundFeedbackEnabled: Boolean = true

    private var toneGenerator: ToneGenerator? = try {
        ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)
    } catch (e: Exception) {
        null
    }

    /**
     * Membuka laci uang (mengirim pulsa listrik ke solenoid laci).
     */
    fun kickDrawer(source: String = "MANUAL_TEST", note: String = "Laci dibuka"): DrawerEvent {
        Log.i(TAG, "⚡ Mengirim sinyal pulsa pembuka laci kasir [ESC p 0 25 250] - Sumber: $source")

        if (soundFeedbackEnabled) {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 120)
            } catch (e: Exception) {
                Log.w(TAG, "Audio feedback tidak tersedia: ${e.message}")
            }
        }

        return DrawerEvent(
            source = source,
            note = note
        )
    }
}

