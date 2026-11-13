package com.bjoey.app.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PauseCircleOutline
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.outlined.SensorsOff
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjoey.app.data.StartedShiftSession
import com.bjoey.app.network.ShiftSyncManager
import com.bjoey.app.network.SyncResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class SyncLogItem(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: String,
    val isSuccess: Boolean,
    val summary: String,
    val detail: String
)

@Composable
fun DashboardScreen(
    session: StartedShiftSession,
    baseUrl: String,
    shiftSyncManager: ShiftSyncManager,
    onSaveBaseUrl: (String) -> Unit,
    onLockClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    var isOnline by remember { mutableStateOf<Boolean?>(null) }
    var lastSyncTime by remember { mutableStateOf("-") }
    var countdown by remember { mutableIntStateOf(10) }
    var isPaused by remember { mutableStateOf(false) }
    var isSyncing by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var isCopied by remember { mutableStateOf(false) }

    var logHistory by remember { mutableStateOf<List<SyncLogItem>>(emptyList()) }

    fun performSync() {
        if (isSyncing) return
        isSyncing = true
        coroutineScope.launch {
            val result: SyncResult = shiftSyncManager.syncShiftSession(session)
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

    // Trigger sync pertama kali kasir masuk Dashboard
    LaunchedEffect(Unit) {
        performSync()
    }

    // Loop interval 10 detik untuk Auto-Sync & Heartbeat (hanya berjalan jika !isPaused)
    LaunchedEffect(isPaused) {
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

    if (showSettingsDialog) {
        ServerUrlSettingsDialog(
            currentUrl = baseUrl,
            onDismiss = { showSettingsDialog = false },
            onSave = { updatedUrl ->
                onSaveBaseUrl(updatedUrl)
                showSettingsDialog = false
                countdown = 10
                performSync()
            }
        )
    }

    // Animasi pulsing dot hijau untuk status ONLINE
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(20.dp)
    ) {
        // 1. Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFC02C12)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "POS",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Dashboard Live Monitor",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFFEE2E2))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = session.shift.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF991B1B)
                            )
                        }
                    }

                    Text(
                        text = "Session: ${session.session.id.take(8)}... | Endpoint: $baseUrl",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(
                    onClick = { showSettingsDialog = true },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Pengaturan Server URL",
                        tint = Color(0xFF4B5563),
                        modifier = Modifier.size(20.dp)
                    )
                }

                OutlinedButton(
                    onClick = onLockClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFC02C12)
                    ),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Kunci / Keluar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // 2. Konten Utama: 2 Kolom (Kiri: Status & Simulasi, Kanan: Live Log Console)
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kolom Kiri: Status & Kontrol Testing
            Column(
                modifier = Modifier
                    .weight(1.1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Card 1: Kartu Status Koneksi (Live Sync Monitor)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "STATUS KONEKSI KE SERVER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9CA3AF),
                            letterSpacing = 0.5.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Status Badge Besar
                        val isConnected = isOnline == true
                        val badgeBg = if (isConnected) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                        val badgeBorder = if (isConnected) Color(0xFF86EFAC) else Color(0xFFFCA5A5)
                        val badgeTextColor = if (isConnected) Color(0xFF15803D) else Color(0xFFB91C1C)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(badgeBg)
                                .border(1.dp, badgeBorder, RoundedCornerShape(14.dp))
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Indikator Titik
                                    if (isConnected) {
                                        Box(
                                            modifier = Modifier
                                                .scale(pulseScale)
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF10B981).copy(alpha = pulseAlpha))
                                                .border(2.dp, Color(0xFF059669), CircleShape)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFEF4444))
                                                .border(2.dp, Color(0xFFB91C1C), CircleShape)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = if (isConnected) "ONLINE • TERHUBUNG" else "OFFLINE • GAGAL SYNC",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = badgeTextColor
                                        )
                                        Text(
                                            text = if (isConnected) "Heartbeat terkirim secara berkala ke Next.js" else "Server timeout atau tunnel tidak merespons",
                                            fontSize = 12.sp,
                                            color = badgeTextColor.copy(alpha = 0.85f)
                                        )
                                    }
                                }

                                if (isSyncing) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.5.dp,
                                        color = badgeTextColor
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (isConnected) Icons.Outlined.CheckCircle else Icons.Outlined.ErrorOutline,
                                        contentDescription = null,
                                        tint = badgeTextColor,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Info Detail Waktu
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Terakhir sync:",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B7280)
                                )
                                Text(
                                    text = lastSyncTime,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF111827),
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Siklus Heartbeat:",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B7280)
                                )
                                val countdownText = if (isPaused) {
                                    "Auto-sync dijeda"
                                } else if (isSyncing) {
                                    "Sinkronisasi..."
                                } else {
                                    "Sync dalam: ${countdown}d"
                                }
                                Text(
                                    text = countdownText,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPaused) Color(0xFFD97706) else Color(0xFFC02C12),
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }

                // Card 2: Tombol Simulasi Testing (Untuk Prototype)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "SIMULASI & KONTROL TESTING",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9CA3AF),
                            letterSpacing = 0.5.sp
                        )

                        // Toggle Pause / Simulasi Putus Koneksi
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isPaused) Color(0xFFFEF3C7) else Color(0xFFF3F4F6))
                                .border(
                                    1.dp,
                                    if (isPaused) Color(0xFFFCD34D) else Color(0xFFE5E7EB),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (isPaused) Icons.Outlined.SensorsOff else Icons.Outlined.Sensors,
                                    contentDescription = null,
                                    tint = if (isPaused) Color(0xFFB45309) else Color(0xFF4B5563),
                                    modifier = Modifier.size(24.dp)
                                )
                                Column {
                                    Text(
                                        text = "Simulasi Putus Koneksi (Pause Auto-Sync)",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isPaused) Color(0xFF92400E) else Color(0xFF1F2937)
                                    )
                                    Text(
                                        text = if (isPaused) "Jeda aktif (tunggu 30d di Next.js untuk deteksi offline)" else "Auto-sync normal berjalan tiap 10 detik",
                                        fontSize = 11.sp,
                                        color = if (isPaused) Color(0xFFB45309) else Color(0xFF6B7280)
                                    )
                                }
                            }

                            Switch(
                                checked = isPaused,
                                onCheckedChange = { checked ->
                                    isPaused = checked
                                    if (!checked) {
                                        countdown = 10
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFFD97706)
                                )
                            )
                        }

                        // Tombol Sync Sekarang
                        Button(
                            onClick = {
                                countdown = 10
                                performSync()
                            },
                            enabled = !isSyncing,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1F2937),
                                disabledContainerColor = Color(0xFF9CA3AF)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = "Sync Sekarang",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isSyncing) "Mengirim Sesi Shift..." else "Sync Sekarang (Manual Trigger)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Kolom Kanan: Mini Log Console (10 Riwayat Terakhir)
            Card(
                modifier = Modifier
                    .weight(1.3f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Header Log Console
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "LIVE SYNC LOG CONSOLE",
                                color = Color(0xFF9CA3AF),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 0.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF374151))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${logHistory.size}/10",
                                    color = Color(0xFFE5E7EB),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        TextButton(
                            onClick = {
                                if (logHistory.isNotEmpty()) {
                                    val fullLog = logHistory.joinToString("\n---\n") { item ->
                                        "[${item.timestamp}] ${item.summary}\n${item.detail}"
                                    }
                                    clipboardManager.setText(AnnotatedString(fullLog))
                                    coroutineScope.launch {
                                        isCopied = true
                                        delay(2000)
                                        isCopied = false
                                    }
                                }
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = if (isCopied) Icons.Default.Check else Icons.Outlined.ContentCopy,
                                contentDescription = "Salin Log",
                                tint = if (isCopied) Color(0xFF4ADE80) else Color(0xFF9CA3AF),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isCopied) "Tersalin!" else "Salin Semua Log",
                                color = if (isCopied) Color(0xFF4ADE80) else Color(0xFF9CA3AF),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (logHistory.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF1F2937))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Menunggu aktivitas sinkronisasi pertama...",
                                color = Color(0xFF6B7280),
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF1F2937))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(logHistory, key = { it.id }) { log ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFF111827).copy(alpha = 0.6f))
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Status Badge Bulat
                                    Box(
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (log.isSuccess) Color(0xFF10B981) else Color(0xFFEF4444))
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "[${log.timestamp}]",
                                                color = Color(0xFF9CA3AF),
                                                fontSize = 11.sp,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = if (log.isSuccess) "HTTP 200" else "FAILED",
                                                color = if (log.isSuccess) Color(0xFF4ADE80) else Color(0xFFF87171),
                                                fontSize = 11.sp,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(2.dp))

                                        Text(
                                            text = log.summary,
                                            color = Color(0xFFF3F4F6),
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Medium
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
}

