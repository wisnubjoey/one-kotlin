package com.bjoey.app.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    BackHandler(onBack = onBackClick)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(24.dp)
    ) {
        // Header Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                modifier = Modifier.height(44.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Kembali ke POS",
                    tint = Color(0xFF111827),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kembali",
                    color = Color(0xFF111827),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }

        // 1 Row Horizontal (4 Card Slots)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 1024.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsCardButton(
                    title = "Menu",
                    subtitle = "Atur item, kategori & harga",
                    icon = Icons.Outlined.RestaurantMenu,
                    onClick = onMenuClick,
                    modifier = Modifier.weight(1f)
                )
                SettingsCardButton(
                    title = "History",
                    subtitle = "Riwayat transaksi & log",
                    icon = Icons.Outlined.History,
                    onClick = onHistoryClick,
                    modifier = Modifier.weight(1f)
                )
                BlankSettingsCard(modifier = Modifier.weight(1f))
                BlankSettingsCard(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SettingsCardButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .height(160.dp)
            .clip(cardShape)
            .background(Color(0xFF18181B))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0x3309090B),
                        Color(0xE609090B)
                    )
                )
            )
            .border(1.dp, Color(0xFF27272A), cardShape)
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.14f),
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                letterSpacing = (-0.2).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color(0xFFD4D4D8)
            )
        }
    }
}

@Composable
private fun BlankSettingsCard(
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .height(160.dp)
            .clip(cardShape)
            .background(Color(0xFFF1F5F9))
            .border(1.dp, Color(0xFFE2E8F0), cardShape)
    )
}
