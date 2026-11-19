package com.bjoey.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bjoey.app.data.AppDatabase
import com.bjoey.app.data.ServerConfigManager
import com.bjoey.app.data.StartedShiftSession
import com.bjoey.app.network.ShiftSyncManager
import com.bjoey.app.network.SyncResult
import com.bjoey.app.ui.DashboardScreen
import com.bjoey.app.ui.HeartbeatMonitorScreen
import com.bjoey.app.ui.PinLoginScreen
import com.bjoey.app.ui.SyncLogItem
import com.bjoey.app.ui.settings.SettingsScreen
import com.bjoey.app.ui.settings.menu.MenuNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)
        val serverConfigManager = ServerConfigManager(applicationContext)
        val shiftSyncManager = ShiftSyncManager(
            serverConfigManager = serverConfigManager,
            database = database
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var activeSession by remember { mutableStateOf<StartedShiftSession?>(null) }
                    var baseUrl by remember { mutableStateOf(serverConfigManager.getBaseUrl()) }
                    var lockSessionCounter by remember { mutableIntStateOf(0) }
                    val coroutineScope = rememberCoroutineScope()
                    val lifecycleOwner = LocalLifecycleOwner.current

                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                            database.openHelper.writableDatabase
                        }
                    }

                    DisposableEffect(lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_STOP) {
                                activeSession = null
                                lockSessionCounter++
                                coroutineScope.launch {
                                    database.lockActiveSessions()
                                }
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }

                    val currentSession = activeSession
                    if (currentSession == null) {
                        key(lockSessionCounter) {
                            PinLoginScreen(
                                baseUrl = baseUrl,
                                onSaveBaseUrl = { updatedUrl ->
                                    serverConfigManager.setBaseUrl(updatedUrl)
                                    baseUrl = serverConfigManager.getBaseUrl()
                                },
                                onValidatePin = { enteredPin ->
                                    database.verifyPinAndStartSession(enteredPin)
                                },
                                onLoginSuccess = { startedSession ->
                                    activeSession = startedSession
                                }
                            )
                        }
                    } else {
                        key(currentSession.session.id) {
                            var showMonitorScreen by remember { mutableStateOf(false) }
                            var showSettingsScreen by remember { mutableStateOf(false) }
                            var showMenuScreen by remember { mutableStateOf(false) }
                            var isOnline by remember { mutableStateOf<Boolean?>(null) }
                            var lastSyncTime by remember { mutableStateOf("-") }
                            var countdown by remember { mutableIntStateOf(10) }
                            // Auto-sync heartbeat is PAUSED by default
                            var isPaused by remember { mutableStateOf(true) }
                            var isSyncing by remember { mutableStateOf(false) }
                            var logHistory by remember { mutableStateOf<List<SyncLogItem>>(emptyList()) }

                            fun performSync() {
                                if (isSyncing) return
                                isSyncing = true
                                coroutineScope.launch {
                                    val result: SyncResult = shiftSyncManager.syncShiftSession(currentSession)
                                    val currentClock = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

                                    isOnline = result.isSuccess
                                    if (result.isSuccess) {
                                        lastSyncTime = currentClock
                                    }

                                    val newLog = SyncLogItem(
                                        timestamp = currentClock,
                                        isSuccess = result.isSuccess,
                                        summary = result.summary,
                                        detail = result.logMessage
                                    )
                                    logHistory = (listOf(newLog) + logHistory).take(10)
                                    isSyncing = false
                                }
                            }

                            LaunchedEffect(currentSession.session.id) {
                                if (!isPaused) {
                                    performSync()
                                }
                            }

                            LaunchedEffect(currentSession.session.id, isPaused) {
                                if (isPaused) return@LaunchedEffect
                                while (true) {
                                    delay(1000L)
                                    if (countdown > 1) {
                                        countdown--
                                    } else {
                                        countdown = 10
                                        performSync()
                                    }
                                }
                            }

                            val handleLockClick = {
                                activeSession = null
                                lockSessionCounter++
                                coroutineScope.launch {
                                    database.lockActiveSessions()
                                }
                                Unit
                            }

                            when {
                                showMenuScreen -> {
                                    MenuNavigation(
                                        menuDao = database.menuDao(),
                                        onBackClick = { showMenuScreen = false }
                                    )
                                }
                                showSettingsScreen -> {
                                    SettingsScreen(
                                        onBackClick = { showSettingsScreen = false },
                                        onMenuClick = { showMenuScreen = true }
                                    )
                                }
                                showMonitorScreen -> {
                                    HeartbeatMonitorScreen(
                                        session = currentSession,
                                        baseUrl = baseUrl,
                                        isOnline = isOnline,
                                        lastSyncTime = lastSyncTime,
                                        countdown = countdown,
                                        isPaused = isPaused,
                                        isSyncing = isSyncing,
                                        logHistory = logHistory,
                                        onTogglePause = { checked ->
                                            isPaused = checked
                                            if (!checked) {
                                                countdown = 10
                                                performSync()
                                            }
                                        },
                                        onManualSync = {
                                            countdown = 10
                                            performSync()
                                        },
                                        onSaveBaseUrl = { updatedUrl ->
                                            serverConfigManager.setBaseUrl(updatedUrl)
                                            baseUrl = serverConfigManager.getBaseUrl()
                                        },
                                        onBackClick = { showMonitorScreen = false },
                                        onLockClick = handleLockClick
                                    )
                                }
                                else -> {
                                    DashboardScreen(
                                        session = currentSession,
                                        isOnline = isOnline,
                                        isPaused = isPaused,
                                        onOpenMonitorClick = { showMonitorScreen = true },
                                        onOpenSettingsClick = { showSettingsScreen = true },
                                        onOpenMenuClick = { showMenuScreen = true },
                                        onLockClick = handleLockClick
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
