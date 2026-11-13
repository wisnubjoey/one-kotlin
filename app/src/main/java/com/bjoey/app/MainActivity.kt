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
import com.bjoey.app.ui.DashboardScreen
import com.bjoey.app.ui.PinLoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                        DashboardScreen(
                            session = currentSession,
                            baseUrl = baseUrl,
                            shiftSyncManager = shiftSyncManager,
                            onSaveBaseUrl = { updatedUrl ->
                                serverConfigManager.setBaseUrl(updatedUrl)
                                baseUrl = serverConfigManager.getBaseUrl()
                            },
                            onLockClick = {
                                activeSession = null
                                lockSessionCounter++
                                coroutineScope.launch {
                                    database.lockActiveSessions()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
