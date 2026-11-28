package com.bjoey.app.ui.settings.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjoey.app.data.local.entity.OrderStatus
import com.bjoey.app.ui.settings.menu.formatRupiah

@Composable
fun HistoryListScreen(
    orders: List<OrderHistoryEntry>,
    summary: HistorySummary,
    selectedStatusFilter: HistoryStatusFilter,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onStatusFilterSelected: (HistoryStatusFilter) -> Unit,
    onOrderClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    if (onBackClick != null) {
        BackHandler(onBack = onBackClick)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HistoryColors.ScreenBackground)
    ) {
        HistoryListHeader(
            totalCount = summary.totalOrders,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onBackClick = onBackClick
        )

        HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

        HistorySummaryStrip(summary = summary)

        HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

        StatusFilterStrip(
            summary = summary,
            selectedFilter = selectedStatusFilter,
            onFilterSelected = onStatusFilterSelected
        )

        HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

        if (orders.isEmpty()) {
            val isFiltered = searchQuery.isNotBlank() || selectedStatusFilter != HistoryStatusFilter.ALL
            HistoryEmptyState(
                isFiltered = isFiltered,
                onResetFilters = {
                    onSearchQueryChange("")
                    onStatusFilterSelected(HistoryStatusFilter.ALL)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = orders,
                    key = { it.id }
                ) { entry ->
                    HistoryOrderCard(
                        entry = entry,
                        onClick = { onOrderClick(entry.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryListHeader(
    totalCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackClick: (() -> Unit)?
) {
    Surface(
        color = HistoryColors.CardSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (onBackClick != null) {
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
                }

                Text(
                    text = "History",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryColors.TextPrimary
                )

                Surface(
                    shape = CircleShape,
                    color = HistoryColors.BadgeBackground,
                    border = BorderStroke(1.dp, HistoryColors.BorderDefault)
                ) {
                    Text(
                        text = "$totalCount Order",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HistoryColors.TextPrimary,
                        style = TextStyle(fontFeatureSettings = "tnum"),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }
            }

            HistorySearchField(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HistorySearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = "Cari #ORD, nama meja, menu, atau metode bayar...",
                color = HistoryColors.TextSecondary,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Cari",
                tint = HistoryColors.TextSecondary
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hapus pencarian",
                        tint = HistoryColors.TextSecondary
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = HistoryColors.BorderFocused,
            unfocusedBorderColor = HistoryColors.BorderDefault,
            focusedContainerColor = HistoryColors.CardSurface,
            unfocusedContainerColor = HistoryColors.CardSurface,
            focusedTextColor = HistoryColors.TextPrimary,
            unfocusedTextColor = HistoryColors.TextPrimary,
            cursorColor = HistoryColors.BorderFocused
        )
    )
}

@Composable
private fun HistorySummaryStrip(summary: HistorySummary) {
    Surface(
        color = HistoryColors.CardSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryMetricCard(
                label = "Total Omzet (Lunas)",
                value = formatRupiah(summary.totalPaidRevenue),
                accentColor = HistoryColors.PrimaryAccent,
                modifier = Modifier.weight(1.4f)
            )
            SummaryMetricCard(
                label = "Lunas",
                value = "${summary.paidCount} Order",
                accentColor = HistoryColors.PaidText,
                modifier = Modifier.weight(1f)
            )
            SummaryMetricCard(
                label = "Open Bill",
                value = "${summary.openCount} Meja",
                accentColor = HistoryColors.OpenText,
                modifier = Modifier.weight(1f)
            )
            SummaryMetricCard(
                label = "Dibatalkan",
                value = "${summary.cancelledCount} Order",
                accentColor = HistoryColors.CancelledText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SummaryMetricCard(
    label: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(HistoryColors.ScreenBackground)
            .border(1.dp, HistoryColors.BorderDefault, RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = HistoryColors.TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            style = TextStyle(fontFeatureSettings = "tnum"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun StatusFilterStrip(
    summary: HistorySummary,
    selectedFilter: HistoryStatusFilter,
    onFilterSelected: (HistoryStatusFilter) -> Unit
) {
    Surface(
        color = HistoryColors.CardSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(HistoryStatusFilter.entries) { filter ->
                val count = when (filter) {
                    HistoryStatusFilter.ALL -> summary.totalOrders
                    HistoryStatusFilter.PAID -> summary.paidCount
                    HistoryStatusFilter.OPEN -> summary.openCount
                    HistoryStatusFilter.CANCELLED -> summary.cancelledCount
                }
                val selected = selectedFilter == filter
                Surface(
                    shape = CircleShape,
                    color = if (selected) HistoryColors.PrimaryAccent else HistoryColors.CardSurface,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selected) HistoryColors.PrimaryAccent else HistoryColors.BorderDefault
                    ),
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onFilterSelected(filter) }
                ) {
                    Text(
                        text = "${filter.label} ($count)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selected) Color.White else HistoryColors.TextPrimary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryOrderCard(
    entry: OrderHistoryEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(12.dp)
    val itemSummaryText = if (entry.items.isEmpty()) {
        "Tidak ada item"
    } else {
        entry.items.joinToString(", ") { "${it.quantity}× ${it.itemTitle}" }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .clickable(onClick = onClick),
        shape = cardShape,
        color = HistoryColors.CardSurface,
        border = BorderStroke(1.dp, HistoryColors.BorderDefault)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
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
                        text = entry.displayOrderNumber,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = HistoryColors.TextPrimary
                    )
                    Text(
                        text = "•",
                        color = HistoryColors.TextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = entry.tableName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = HistoryColors.TextPrimary
                    )
                }

                OrderStatusBadge(status = entry.status)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${entry.guestCount} Orang • ${entry.totalItemQuantity} Item",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HistoryColors.TextSecondary
                )
                Text(
                    text = formatHistoryTimestamp(entry.closedAt ?: entry.createdAt),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = HistoryColors.TextSecondary
                )
            }

            Text(
                text = itemSummaryText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = HistoryColors.TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            HorizontalDivider(thickness = 1.dp, color = HistoryColors.BorderDefault)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ReceiptLong,
                        contentDescription = null,
                        tint = HistoryColors.TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = entry.paymentMethod ?: if (entry.status == OrderStatus.OPEN) "Belum Bayar" else "-",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HistoryColors.TextSecondary
                    )
                }

                Text(
                    text = formatRupiah(entry.grandTotal),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HistoryColors.TextPrimary,
                    style = TextStyle(fontFeatureSettings = "tnum")
                )
            }
        }
    }
}

@Composable
internal fun OrderStatusBadge(status: String) {
    val (bg, border, text) = when (status) {
        OrderStatus.PAID -> Triple(HistoryColors.PaidBg, HistoryColors.PaidBorder, HistoryColors.PaidText)
        OrderStatus.OPEN -> Triple(HistoryColors.OpenBg, HistoryColors.OpenBorder, HistoryColors.OpenText)
        OrderStatus.CANCELLED -> Triple(HistoryColors.CancelledBg, HistoryColors.CancelledBorder, HistoryColors.CancelledText)
        else -> Triple(HistoryColors.BadgeBackground, HistoryColors.BorderDefault, HistoryColors.TextPrimary)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = orderStatusLabel(status),
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp,
            color = text
        )
    }
}

@Composable
private fun HistoryEmptyState(
    isFiltered: Boolean,
    onResetFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = HistoryColors.CardSurface,
                border = BorderStroke(1.dp, HistoryColors.BorderDefault),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isFiltered) Icons.Outlined.Search else Icons.Outlined.History,
                        contentDescription = null,
                        tint = HistoryColors.TextSecondary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isFiltered) "Riwayat order tidak ditemukan" else "Belum ada riwayat order",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = HistoryColors.TextPrimary
            )

            Text(
                text = if (isFiltered) {
                    "Coba ubah filter status atau kata kunci pencarian."
                } else {
                    "Order Dine-In yang Anda buat di POS kasir akan tercatat di sini."
                },
                fontSize = 14.sp,
                color = HistoryColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            if (isFiltered) {
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(
                    onClick = onResetFilters,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, HistoryColors.BorderDefault)
                ) {
                    Text(
                        text = "Reset Filter",
                        color = HistoryColors.TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
