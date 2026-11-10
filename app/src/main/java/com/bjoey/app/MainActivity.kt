package com.bjoey.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bjoey.app.ui.AppNavHost

/**
 * ============================================================================
 * NEXT.JS / REACT NATIVE ANALOGY:
 * File ini adalah Root Entry Point (Setara dengan `App.tsx` di Expo / `_app.tsx`).
 *
 * Di sini Android Activity menginisialisasi Compose UI melalui `setContent { ... }`
 * dan merender `AppNavHost` yang menjadi pengatur seluruh rute aplikasi.
 * ============================================================================
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}
