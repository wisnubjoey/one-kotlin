package com.bjoey.app.data

import android.content.Context
import android.content.SharedPreferences

class ServerConfigManager(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getBaseUrl(): String {
        val raw = prefs.getString(KEY_BASE_URL, DEFAULT_BASE_URL)?.trim().orEmpty()
        return if (raw.isEmpty()) DEFAULT_BASE_URL else normalizeUrl(raw)
    }

    fun setBaseUrl(url: String) {
        val cleaned = url.trim()
        val normalized = if (cleaned.isEmpty()) DEFAULT_BASE_URL else normalizeUrl(cleaned)
        prefs.edit()
            .putString(KEY_BASE_URL, normalized)
            .apply()
    }

    private fun normalizeUrl(url: String): String {
        var result = url.trim().trimEnd('/')
        if (!result.startsWith("http://", ignoreCase = true) &&
            !result.startsWith("https://", ignoreCase = true)
        ) {
            result = "https://$result"
        }
        // Cegah duplikasi endpoint jika pengguna memasukkan URL yang sudah memiliki path /api atau /api/pos/sync-shift
        while (result.endsWith("/api/pos/sync-shift", ignoreCase = true) ||
            result.endsWith("/api", ignoreCase = true)
        ) {
            if (result.endsWith("/api/pos/sync-shift", ignoreCase = true)) {
                result = result.substring(0, result.length - "/api/pos/sync-shift".length).trimEnd('/')
            } else if (result.endsWith("/api", ignoreCase = true)) {
                result = result.substring(0, result.length - "/api".length).trimEnd('/')
            }
        }
        return result
    }

    companion object {
        private const val PREFS_NAME = "pos_server_config"
        private const val KEY_BASE_URL = "base_url"
        const val DEFAULT_BASE_URL = "https://example.trycloudflare.com"
    }
}
