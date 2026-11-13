package com.bjoey.app.network

import android.util.Log
import com.bjoey.app.data.AppDatabase
import com.bjoey.app.data.ServerConfigManager
import com.bjoey.app.data.StartedShiftSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

data class SyncResult(
    val isSuccess: Boolean,
    val summary: String,
    val logMessage: String,
    val statusCode: Int? = null
)

class ShiftSyncManager(
    private val serverConfigManager: ServerConfigManager,
    private val database: AppDatabase
) {
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    suspend fun syncShiftSession(startedSession: StartedShiftSession): SyncResult = withContext(Dispatchers.IO) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val baseUrl = serverConfigManager.getBaseUrl().trimEnd('/')
        val endpoint = "$baseUrl/api/pos/sync-shift"

        val payloadJson = JSONObject().apply {
            put("sessionId", startedSession.session.id)
            put("shiftName", startedSession.shift.name)
            put("openedAt", startedSession.session.openedAt)
            put("deviceId", DEVICE_ID)
        }.toString()

        try {
            val requestBody = payloadJson.toRequestBody(JSON_MEDIA_TYPE)

            val request = Request.Builder()
                .url(endpoint)
                .addHeader("x-api-key", API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            Log.d(TAG, "[$timestamp] Sending request to $endpoint with payload: $payloadJson")

            httpClient.newCall(request).execute().use { response ->
                val responseBody = response.body?.string().orEmpty().trim()
                val isSuccess = response.isSuccessful

                val log = buildString {
                    append("[$timestamp] POST $endpoint\n")
                    append("Payload: $payloadJson\n")
                    append("HTTP Status: ${response.code} ${response.message}\n")
                    if (responseBody.isNotEmpty()) {
                        append("Response: $responseBody")
                    } else {
                        append("Response: (empty body)")
                    }
                }

                if (isSuccess) {
                    database.markSessionAsSynced(startedSession.session.id)
                    Log.d(TAG, "Sync success: $log")
                    SyncResult(
                        isSuccess = true,
                        summary = "Heartbeat & Sync Sukses (${response.code})",
                        logMessage = log,
                        statusCode = response.code
                    )
                } else {
                    Log.e(TAG, "Sync HTTP error: $log")
                    SyncResult(
                        isSuccess = false,
                        summary = "Gagal: HTTP ${response.code} ${response.message}",
                        logMessage = log,
                        statusCode = response.code
                    )
                }
            }
        } catch (e: Exception) {
            val exceptionCause = e.cause?.let { " (${it::class.java.simpleName}: ${it.message})" }.orEmpty()
            val errorMessage = "${e::class.java.simpleName}: ${e.message.orEmpty()}$exceptionCause"

            val log = buildString {
                append("[$timestamp] POST $endpoint\n")
                append("Payload: $payloadJson\n")
                append("Error: $errorMessage")
            }

            Log.e(TAG, "Sync exception: $log", e)
            SyncResult(
                isSuccess = false,
                summary = "Gagal: ${e::class.java.simpleName}",
                logMessage = log,
                statusCode = null
            )
        }
    }

    companion object {
        private const val TAG = "ShiftSyncManager"
        private const val API_KEY = "pos-secret-123"
        private const val DEVICE_ID = "Tablet-POS-1"
        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    }
}
