package com.bjoey.app.ui.settings.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjoey.app.data.local.entity.OrderStatus
import com.bjoey.app.ui.settings.menu.formatRupiah

@Composable
fun HistoryDetailScreen(
    entry: OrderHistoryEntry?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackClick)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HistoryColors.ScreenBackground)
    ) {
        Surface(
            color = HistoryColors.CardSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = HistoryColors.TextPrimary
                    )
                }

                Text(
                    text = entry?.displayOrderNumber ?: "Detail Order",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryColors.TextPrimary,
                    modifier = Modifier.weight(1f)
                )

                if (entry != null) {
                    OrderStatusBadge(status = entry.status)
                }
            }
        }

        HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

        if (entry == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data order tidak ditemukan.",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HistoryColors.TextSecondary
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 720.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Order Info Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HistoryColors.CardSurface,
                        border = BorderStroke(1.dp, HistoryColors.BorderDefault),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Informasi Pesanan",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = HistoryColors.TextPrimary
                            )
                            HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

                            DetailRow(label = "Nomor Order", value = entry.displayOrderNumber)
                            DetailRow(label = "Meja", value = "${entry.tableName} (${entry.guestCount} Orang)")
                            DetailRow(label = "Waktu Pesan", value = formatHistoryTimestamp(entry.createdAt))
                            entry.closedAt?.let { closedAt ->
                                val closedLabel = if (entry.status == OrderStatus.CANCELLED) {
                                    "Waktu Dibatalkan"
                                } else {
                                    "Waktu Bayar"
                                }
                                DetailRow(label = closedLabel, value = formatHistoryTimestamp(closedAt))
                            }
                            DetailRow(
                                label = "Metode Pembayaran",
                                value = entry.paymentMethod ?: if (entry.status == OrderStatus.OPEN) "Belum Bayar (Open Bill)" else "-"
                            )
                        }
                    }

                    // Order Items Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HistoryColors.CardSurface,
                        border = BorderStroke(1.dp, HistoryColors.BorderDefault),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Rincian Menu",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HistoryColors.TextPrimary
                                )
                                Text(
                                    text = "${entry.totalItemQuantity} Item",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = HistoryColors.TextSecondary
                                )
                            }

                            HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

                            entry.items.forEachIndexed { index, item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 12.dp),
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = "${item.quantity}× ${item.itemTitle}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = HistoryColors.TextPrimary
                                        )
                                        Text(
                                            text = "${item.categoryName} • ${formatRupiah(item.unitPrice)} / pcs",
                                            fontSize = 12.sp,
                                            color = HistoryColors.TextSecondary
                                        )
                                    }

                                    Text(
                                        text = formatRupiah(item.lineTotal),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HistoryColors.TextPrimary,
                                        style = TextStyle(fontFeatureSettings = "tnum")
                                    )
                                }

                                if (index < entry.items.lastIndex) {
                                    HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)
                                }
                            }
                        }
                    }

                    // Payment Summary Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HistoryColors.CardSurface,
                        border = BorderStroke(1.dp, HistoryColors.BorderDefault),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Ringkasan Pembayaran",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = HistoryColors.TextPrimary
                            )
                            HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

                            DetailRow(label = "Subtotal", value = formatRupiah(entry.subtotal))
                            DetailRow(label = "Tax (11% PPN)", value = formatRupiah(entry.taxAmount))

                            HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderFocused)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "TOTAL TAGIHAN",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HistoryColors.TextPrimary
                                )
                                Text(
                                    text = formatRupiah(entry.grandTotal),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HistoryColors.TextPrimary,
                                    style = TextStyle(fontFeatureSettings = "tnum")
                                )
                            }

                            if (entry.cashReceived != null && entry.changeAmount != null) {
                                HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)
                                DetailRow(label = "Tunai Diterima", value = formatRupiah(entry.cashReceived))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Kembalian",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HistoryColors.PrimaryAccent
                                    )
                                    Text(
                                        text = formatRupiah(entry.changeAmount),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = HistoryColors.PrimaryAccent,
                                        style = TextStyle(fontFeatureSettings = "tnum")
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

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = HistoryColors.TextSecondary
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = HistoryColors.TextPrimary,
            style = TextStyle(fontFeatureSettings = "tnum")
        )
    }
}
