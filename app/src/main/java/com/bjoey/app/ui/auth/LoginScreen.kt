package com.bjoey.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ============================================================================
 * NEXT.JS / REACT ANALOGY:
 * File ini setara dengan: `app/(auth)/login/page.tsx`
 *
 * Di React/Next.js:
 * const [cashierName, setCashierName] = useState("Bjoey")
 *
 * Di Kotlin Jetpack Compose:
 * var cashierName by remember { mutableStateOf("Bjoey") }
 * ('by' adalah Kotlin property delegate, membuat variabel reaktif secara otomatis)
 * ============================================================================
 */
@Composable
fun LoginScreen(
    onLoginSuccess: (cashierName: String) -> Unit
) {
    // State reaktif (setara useState)
    var cashierName by remember { mutableStateOf("Kasir 1") }
    var pin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 420.dp)
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header / Logo
                Text(
                    text = "🐼 Panda Cash Drawer",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Grup Rute: (auth) / Login Kasir",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Input Nama Kasir
                OutlinedTextField(
                    value = cashierName,
                    onValueChange = {
                        cashierName = it
                        errorMessage = ""
                    },
                    label = { Text("Nama Kasir") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Kasir")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Input PIN
                OutlinedTextField(
                    value = pin,
                    onValueChange = {
                        pin = it
                        errorMessage = ""
                    },
                    label = { Text("PIN Kasir (4 Digit)") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "PIN")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Pesan Error jika input kosong
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tombol Submit / Masuk
                Button(
                    onClick = {
                        if (cashierName.isBlank()) {
                            errorMessage = "Silakan masukkan nama kasir!"
                        } else if (pin.length < 4) {
                            errorMessage = "PIN minimal 4 angka (contoh: 1234)"
                        } else {
                            onLoginSuccess(cashierName)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Buka Sesi Kasir", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

