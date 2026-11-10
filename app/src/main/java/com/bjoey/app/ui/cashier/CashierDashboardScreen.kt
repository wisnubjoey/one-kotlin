package com.bjoey.app.ui.cashier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class untuk riwayat aksi laci kasir
 * (Di TypeScript setara: interface DrawerLog { id: string; time: string; message: string })
 */
data class DrawerLog(
    val id: Long,
    val time: String,
    val message: String
)

/**
 * ============================================================================
 * NEXT.JS / REACT ANALOGY:
 * File ini setara dengan: `app/(cashier)/dashboard/page.tsx`
 *
 * Di React:
 * Menggunakan useState, useEffect, FlatList/map, dan UI components.
 *
 * Di Jetpack Compose:
 * - LazyColumn = FlatList di React Native (efisien untuk daftar data).
 * - Scaffold = Struktur halaman umum (TopBar, Content, FAB, BottomBar).
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashierDashboardScreen(
    cashierName: String,
    onLogout: () -> Unit
) {
    // State: Apakah laci sedang terbuka
    var isDrawerOpen by remember { mutableStateOf(false) }
    
    // State: Berapa kali laci dibuka
    var openCount by remember { mutableIntStateOf(0) }

    // State: Daftar riwayat pembukaan laci
    var logs by remember {
        mutableStateOf(
            listOf(
                DrawerLog(
                    id = 1L,
                    time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
                    message = "Sesi kasir dimulai oleh $cashierName"
                )
            )
        )
    }

    val timeFormatter = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "🐼 Panda Cash Drawer",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Grup Rute: (cashier) / Dashboard",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Keluar",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Profil Kasir
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Kasir yang Bertugas:", fontSize = 13.sp)
                        Text(
                            text = cashierName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "ONLINE",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Card Status Laci Uang (Cash Drawer)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "STATUS LACI UANG (HARDWARE CASH DRAWER)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Indikator Status Visual
                    val statusColor = if (isDrawerOpen) Color(0xFFE65100) else Color(0xFF2E7D32)
                    val statusText = if (isDrawerOpen) "LACI TERBUKA (OPEN)" else "LACI TERTUTUP (LOCKED)"
                    val statusIcon = if (isDrawerOpen) Icons.Default.MeetingRoom else Icons.Default.Lock

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(statusColor)
                        )
                        Text(
                            text = statusText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = statusColor
                        )
                    }

                    Text(
                        text = "Total laci dibuka dalam sesi ini: $openCount kali",
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Tombol Kontrol Drawer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                isDrawerOpen = true
                                openCount++
                                val now = timeFormatter.format(Date())
                                logs = listOf(
                                    DrawerLog(
                                        id = System.currentTimeMillis(),
                                        time = now,
                                        message = "Perintah kick drawer dikirim (Buka Laci)"
                                    )
                                ) + logs
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(imageVector = Icons.Default.MeetingRoom, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Buka Laci (Kick)")
                        }

                        OutlinedButton(
                            onClick = {
                                if (isDrawerOpen) {
                                    isDrawerOpen = false
                                    val now = timeFormatter.format(Date())
                                    logs = listOf(
                                        DrawerLog(
                                            id = System.currentTimeMillis(),
                                            time = now,
                                            message = "Laci ditutup secara manual"
                                        )
                                    ) + logs
                                }
                            },
                            enabled = isDrawerOpen,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Tutup Laci")
                        }
                    }
                }
            }

            // Log Riwayat Aktivitas Laci (Setara FlatList di React Native)
            Text(
                text = "Riwayat Aktivitas Hardware Laci:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(logs, key = { it.id }) { log ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = log.message,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = log.time,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

