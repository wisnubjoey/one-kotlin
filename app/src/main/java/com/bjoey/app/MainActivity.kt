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
import com.bjoey.app.data.ShiftEntity
import com.bjoey.app.ui.HomeScreen
import com.bjoey.app.ui.PinLoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var activeShift by remember { mutableStateOf<ShiftEntity?>(null) }
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
                                activeShift = null
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

                    val currentShift = activeShift
                    if (currentShift == null) {
                        key(lockSessionCounter) {
                            PinLoginScreen(
                                onValidatePin = { enteredPin ->
                                    database.verifyPinAndStartSession(enteredPin)
                                },
                                onLoginSuccess = { matchedShift ->
                                    activeShift = matchedShift
                                }
                            )
                        }
                    } else {
                        HomeScreen(
                            shiftName = currentShift.name,
                            onLockClick = {
                                activeShift = null
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
