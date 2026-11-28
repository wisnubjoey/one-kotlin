package com.bjoey.app.ui

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.TableRestaurant
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bjoey.app.data.local.dao.MenuDao
import com.bjoey.app.data.local.dao.OrderDao
import com.bjoey.app.data.local.dao.TableDao
import com.bjoey.app.data.local.entity.StartedShiftSession
import com.bjoey.app.ui.dashboard.CompletedReceiptUi
import com.bjoey.app.ui.dashboard.DashboardStage
import com.bjoey.app.ui.dashboard.DashboardViewModel
import com.bjoey.app.ui.dashboard.DraftCartItem
import com.bjoey.app.ui.dashboard.OrderTotals
import com.bjoey.app.ui.dashboard.TableFilter
import com.bjoey.app.ui.dashboard.TableSlotUi
import com.bjoey.app.ui.dashboard.calculateOrderTotals
import com.bjoey.app.ui.dashboard.formatOrderNumber
import com.bjoey.app.ui.settings.menu.MenuCategory
import com.bjoey.app.ui.settings.menu.MenuItem
import com.bjoey.app.ui.settings.menu.ThousandSeparatorTransformation
import com.bjoey.app.ui.settings.menu.formatRupiah

private val KasaInk = Color(0xFF111111)
private val KasaSurface = Color(0xFFF8F9FA)
private val KasaLine = Color(0xFFE5E7EB)
private val KasaAccent = Color(0xFF14532D)
private val KasaMuted = Color(0xFF6B7280)
private val KasaDisabledBg = Color(0xFFD1D5DB)
private val KasaOrange = Color(0xFFEA580C)
private val KasaOrangeBg = Color(0xFFFFF7ED)
private val KasaOrangeBadge = Color(0xFFFED7AA)
private val KasaOrangeDark = Color(0xFF9A3412)
private val KasaDanger = Color(0xFFDC2626)

/**
 * Official KASA POS Dashboard implementing the Dine-In Open Bill workflow:
 * Table Selection -> Guest Count Input -> Menu Ordering -> Orange Occupied Table -> Update / Pay / Cancel.
 */
@Composable
fun DashboardScreen(
    session: StartedShiftSession,
    isOnline: Boolean?,
    isPaused: Boolean,
    tableDao: TableDao,
    menuDao: MenuDao,
    orderDao: OrderDao,
    onOpenMonitorClick: () -> Unit,
    onLockClick: () -> Unit,
    onOpenSettingsClick: () -> Unit = {},
    onOpenMenuClick: () -> Unit = {},
    dashboardViewModel: DashboardViewModel = viewModel(
        key = "dashboard_${session.session.id}",
        factory = DashboardViewModel.Factory(
            tableDao = tableDao,
            menuDao = menuDao,
            orderDao = orderDao
        )
    )
) {
    val stage by dashboardViewModel.stage.collectAsStateWithLifecycle()
    val allTableSlots by dashboardViewModel.tableSlots.collectAsStateWithLifecycle()
    val filteredTableSlots by dashboardViewModel.filteredTableSlots.collectAsStateWithLifecycle()
    val occupiedTableSlots by dashboardViewModel.occupiedTableSlots.collectAsStateWithLifecycle()
    val tableSearchQuery by dashboardViewModel.tableSearchQuery.collectAsStateWithLifecycle()
    val tableFilter by dashboardViewModel.tableFilter.collectAsStateWithLifecycle()

    val categories by dashboardViewModel.categories.collectAsStateWithLifecycle()
    val filteredMenuItems by dashboardViewModel.filteredMenuItems.collectAsStateWithLifecycle()
    val menuSearchQuery by dashboardViewModel.menuSearchQuery.collectAsStateWithLifecycle()
    val selectedCategoryId by dashboardViewModel.selectedCategoryId.collectAsStateWithLifecycle()

    val pendingGuestCountTable by dashboardViewModel.pendingGuestCountTable.collectAsStateWithLifecycle()
    val showEditGuestCountDialog by dashboardViewModel.showEditGuestCountDialog.collectAsStateWithLifecycle()
    val draftCart by dashboardViewModel.draftCart.collectAsStateWithLifecycle()
    val showPaymentDialog by dashboardViewModel.showPaymentDialog.collectAsStateWithLifecycle()
    val showCancelOrderDialog by dashboardViewModel.showCancelOrderDialog.collectAsStateWithLifecycle()
    val showDiscardDraftDialog by dashboardViewModel.showDiscardDraftDialog.collectAsStateWithLifecycle()
    val completedReceipt by dashboardViewModel.completedReceipt.collectAsStateWithLifecycle()
    val isProcessingOrder by dashboardViewModel.isProcessingOrder.collectAsStateWithLifecycle()

    val currentMenuStage = stage as? DashboardStage.MenuOrder
    if (currentMenuStage != null) {
        BackHandler {
            dashboardViewModel.requestBackToTables()
        }
    }

    val cartEntries = remember(draftCart) { draftCart.values.toList() }
    val totals = remember(cartEntries) { calculateOrderTotals(cartEntries) }

    pendingGuestCountTable?.let { table ->
        GuestCountDialog(
            tableName = table.name,
            initialCount = 2,
            confirmLabel = "Lanjut Pilih Menu",
            onDismiss = dashboardViewModel::dismissGuestCountDialog,
            onConfirm = dashboardViewModel::confirmGuestCountAndEnterMenu
        )
    }

    if (showEditGuestCountDialog && currentMenuStage != null) {
        GuestCountDialog(
            tableName = currentMenuStage.tableName,
            initialCount = currentMenuStage.guestCount,
            confirmLabel = "Simpan Jumlah Tamu",
            onDismiss = dashboardViewModel::dismissEditGuestCountDialog,
            onConfirm = dashboardViewModel::updateActiveGuestCount
        )
    }

    if (showPaymentDialog && currentMenuStage != null && cartEntries.isNotEmpty()) {
        PaymentCheckoutDialog(
            tableName = currentMenuStage.tableName,
            guestCount = currentMenuStage.guestCount,
            orderNumberLabel = currentMenuStage.existingOrderNumber?.let(::formatOrderNumber) ?: "ORDER BARU",
            totals = totals,
            isProcessing = isProcessingOrder,
            onDismiss = dashboardViewModel::dismissPaymentDialog,
            onConfirmPayment = { method, cashReceived ->
                dashboardViewModel.confirmPayment(
                    shiftSessionId = session.session.id,
                    paymentMethod = method,
                    cashReceived = cashReceived
                )
            }
        )
    }

    completedReceipt?.let { receipt ->
        ReceiptConfirmationDialog(
            receipt = receipt,
            onStartNextOrder = dashboardViewModel::dismissReceiptAndReturnToTables
        )
    }

    if (showCancelOrderDialog && currentMenuStage != null) {
        CancelOrderConfirmationDialog(
            tableName = currentMenuStage.tableName,
            orderNumberLabel = currentMenuStage.existingOrderNumber?.let(::formatOrderNumber) ?: "",
            onDismiss = dashboardViewModel::dismissCancelOrderDialog,
            onConfirm = dashboardViewModel::confirmCancelOpenOrder
        )
    }

    if (showDiscardDraftDialog) {
        DiscardDraftConfirmationDialog(
            onDismiss = dashboardViewModel::dismissDiscardDraftDialog,
            onConfirmDiscard = dashboardViewModel::confirmDiscardAndBackToTables
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ==========================================
        // LEFT / MAIN PANEL (65% WIDTH) — TABLES OR MENU
        // ==========================================
        Column(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
                .background(KasaSurface)
        ) {
            DashboardTopBar(
                session = session,
                isOnline = isOnline,
                isPaused = isPaused,
                isMenuStage = currentMenuStage != null,
                searchQuery = if (currentMenuStage != null) menuSearchQuery else tableSearchQuery,
                searchPlaceholder = if (currentMenuStage != null) {
                    "Cari menu atau kategori..."
                } else {
                    "Cari nama meja..."
                },
                onSearchQueryChange = { query ->
                    if (currentMenuStage != null) {
                        dashboardViewModel.onMenuSearchQueryChange(query)
                    } else {
                        dashboardViewModel.onTableSearchQueryChange(query)
                    }
                },
                onBackToTablesClick = dashboardViewModel::requestBackToTables,
                onOpenSettingsClick = onOpenSettingsClick,
                onOpenMenuClick = onOpenMenuClick,
                onOpenMonitorClick = onOpenMonitorClick,
                onLockClick = onLockClick
            )

            HorizontalDivider(thickness = 1.dp, color = KasaLine)

            if (currentMenuStage == null) {
                TableFilterStrip(
                    activeFilter = tableFilter,
                    totalCount = allTableSlots.size,
                    availableCount = allTableSlots.count { !it.isOccupied },
                    occupiedCount = allTableSlots.count { it.isOccupied },
                    shownCount = filteredTableSlots.size,
                    onFilterSelected = dashboardViewModel::onTableFilterSelected
                )

                HorizontalDivider(thickness = 1.dp, color = KasaLine)

                TableGridContent(
                    tables = filteredTableSlots,
                    isFiltered = tableSearchQuery.isNotBlank() || tableFilter != TableFilter.ALL,
                    onTableClick = dashboardViewModel::onTableClicked,
                    onResetFilters = {
                        dashboardViewModel.onTableSearchQueryChange("")
                        dashboardViewModel.onTableFilterSelected(TableFilter.ALL)
                    },
                    onOpenSettingsClick = onOpenSettingsClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            } else {
                MenuCategoryStrip(
                    categories = categories,
                    selectedCategoryId = selectedCategoryId,
                    shownCount = filteredMenuItems.size,
                    onCategorySelected = dashboardViewModel::onCategorySelected
                )

                HorizontalDivider(thickness = 1.dp, color = KasaLine)

                MenuGridContent(
                    menuItems = filteredMenuItems,
                    draftCart = draftCart,
                    isFiltered = menuSearchQuery.isNotBlank() || selectedCategoryId != null,
                    onItemClick = dashboardViewModel::addMenuItemToCart,
                    onResetFilters = {
                        dashboardViewModel.onMenuSearchQueryChange("")
                        dashboardViewModel.onCategorySelected(null)
                    },
                    onOpenMenuClick = onOpenMenuClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }

        // Vertical Divider between Left & Right Panels
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(KasaInk)
        )

        // ==========================================
        // RIGHT PANEL (35% WIDTH) — DINE-IN ORDER PANEL
        // ==========================================
        if (currentMenuStage == null) {
            TableGridRightPanel(
                occupiedTables = occupiedTableSlots,
                onSelectOccupiedTable = dashboardViewModel::onTableClicked,
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
            )
        } else {
            ActiveOrderRightPanel(
                stage = currentMenuStage,
                cartEntries = cartEntries,
                totals = totals,
                isProcessing = isProcessingOrder,
                onEditGuestCountClick = dashboardViewModel::openEditGuestCountDialog,
                onClearCartClick = dashboardViewModel::clearDraftCart,
                onCancelOpenOrderClick = dashboardViewModel::openCancelOrderDialog,
                onAdjustItemQuantity = dashboardViewModel::adjustCartItemQuantity,
                onSubmitOrderClick = { dashboardViewModel.submitOrder(session.session.id) },
                onPayClick = dashboardViewModel::openPaymentDialog,
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun DashboardTopBar(
    session: StartedShiftSession,
    isOnline: Boolean?,
    isPaused: Boolean,
    isMenuStage: Boolean,
    searchQuery: String,
    searchPlaceholder: String,
    onSearchQueryChange: (String) -> Unit,
    onBackToTablesClick: () -> Unit,
    onOpenSettingsClick: () -> Unit,
    onOpenMenuClick: () -> Unit,
    onOpenMonitorClick: () -> Unit,
    onLockClick: () -> Unit
) {
    var isSearchFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isMenuStage) {
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, KasaInk, RoundedCornerShape(8.dp))
                    .clickable { onBackToTablesClick() }
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Kembali ke Meja",
                    tint = KasaInk,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "MEJA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.6.sp,
                    color = KasaInk
                )
            }
        } else {
            IconButton(
                onClick = onOpenSettingsClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = KasaInk,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = onOpenMenuClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Outlined.RestaurantMenu,
                    contentDescription = "Menu Management",
                    tint = KasaInk,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Live Search Input
        Box(
            modifier = Modifier
                .weight(1f)
                .widthIn(max = 440.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(
                    width = if (isSearchFocused) 2.dp else 1.dp,
                    color = if (isSearchFocused) KasaAccent else KasaInk,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = KasaInk,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = searchPlaceholder,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF9CA3AF),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = KasaInk
                        ),
                        cursorBrush = SolidColor(KasaAccent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { isSearchFocused = it.isFocused }
                    )
                }
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { onSearchQueryChange("") },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search",
                            tint = KasaMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Cashier & Shift Status Indicator (Opens Heartbeat Monitor)
        val statusColor = when {
            isPaused -> Color(0xFFD97706)
            isOnline == false -> Color(0xFFEF4444)
            else -> KasaAccent
        }
        val statusLabel = when {
            isPaused -> "PAUSED"
            isOnline == false -> "OFFLINE"
            else -> "ACTIVE"
        }

        Row(
            modifier = Modifier
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(KasaSurface)
                .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
                .clickable { onOpenMonitorClick() }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(statusColor)
            )
            Column {
                Text(
                    text = "${session.shift.name.uppercase()} • $statusLabel",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaInk,
                    lineHeight = 14.sp
                )
                Text(
                    text = "Monitor Heartbeat",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = KasaMuted,
                    lineHeight = 13.sp
                )
            }
        }

        // Lock / Exit Button
        IconButton(
            onClick = onLockClick,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Kunci / Keluar",
                tint = KasaInk,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun TableFilterStrip(
    activeFilter: TableFilter,
    totalCount: Int,
    availableCount: Int,
    occupiedCount: Int,
    shownCount: Int,
    onFilterSelected: (TableFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val options = listOf(
                Triple(TableFilter.ALL, "SEMUA ($totalCount)", KasaAccent),
                Triple(TableFilter.AVAILABLE, "TERSEDIA ($availableCount)", KasaInk),
                Triple(TableFilter.OCCUPIED, "TERISI ($occupiedCount)", KasaOrange)
            )
            items(options, key = { it.first.name }) { (filter, label, activeColor) ->
                val isActive = activeFilter == filter
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isActive) activeColor else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isActive) activeColor else KasaLine,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onFilterSelected(filter) }
                        .padding(horizontal = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp,
                        color = if (isActive) Color.White else KasaInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "$shownCount MEJA",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = KasaMuted,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun TableGridContent(
    tables: List<TableSlotUi>,
    isFiltered: Boolean,
    onTableClick: (TableSlotUi) -> Unit,
    onResetFilters: () -> Unit,
    onOpenSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (tables.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, KasaInk, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFiltered) Icons.Outlined.Search else Icons.Outlined.TableRestaurant,
                        contentDescription = null,
                        tint = KasaInk,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isFiltered) "Meja tidak ditemukan" else "Belum ada meja terdaftar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isFiltered) {
                        "Coba ubah filter status atau kata kunci pencarian meja."
                    } else {
                        "Tambahkan daftar meja terlebih dahulu di menu Settings."
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = KasaMuted,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = if (isFiltered) onResetFilters else onOpenSettingsClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KasaInk),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text(
                        text = if (isFiltered) "RESET FILTER" else "BUKA SETTINGS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp,
                        color = Color.White
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 185.dp),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tables, key = { it.id }) { slot ->
                    TableSlotCard(
                        slot = slot,
                        onClick = { onTableClick(slot) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TableSlotCard(
    slot: TableSlotUi,
    onClick: () -> Unit
) {
    val activeOrder = slot.activeOrder
    val isOccupied = activeOrder != null
    val cardBg = if (isOccupied) KasaOrangeBg else Color.White
    val borderColor = if (isOccupied) KasaOrange else KasaLine
    val borderWidth = if (isOccupied) 2.dp else 1.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 168.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(cardBg)
            .border(borderWidth, borderColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isOccupied) KasaOrangeBadge else KasaSurface)
                    .border(
                        width = 1.dp,
                        color = if (isOccupied) KasaOrange else KasaLine,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.TableRestaurant,
                    contentDescription = null,
                    tint = if (isOccupied) KasaOrangeDark else KasaInk,
                    modifier = Modifier.size(20.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isOccupied) KasaOrange else KasaSurface)
                    .border(
                        width = 1.dp,
                        color = if (isOccupied) KasaOrange else KasaLine,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (isOccupied) "TERISI" else "TERSEDIA",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.6.sp,
                    color = if (isOccupied) Color.White else KasaMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column {
            Text(
                text = slot.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (isOccupied) KasaOrangeDark else KasaInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (activeOrder != null) {
                val itemCount = activeOrder.items.sumOf { it.quantity }
                Text(
                    text = "${formatOrderNumber(activeOrder.order.orderNumber)} • ${activeOrder.order.guestCount} Orang",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = KasaOrangeDark
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 1.dp, color = KasaOrangeBadge)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$itemCount ITEM",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaOrangeDark
                    )
                    Text(
                        text = formatRupiah(activeOrder.order.grandTotal),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaOrangeDark
                    )
                }
            } else {
                Text(
                    text = "Ketuk untuk input tamu",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = KasaMuted
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 1.dp, color = KasaLine)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "DINE-IN READY",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaAccent,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun MenuCategoryStrip(
    categories: List<MenuCategory>,
    selectedCategoryId: String?,
    shownCount: Int,
    onCategorySelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(key = "all") {
                val isActive = selectedCategoryId == null
                CategoryPill(
                    label = "ALL",
                    isActive = isActive,
                    onClick = { onCategorySelected(null) }
                )
            }

            items(categories, key = { it.id }) { category ->
                CategoryPill(
                    label = category.name.uppercase(),
                    isActive = selectedCategoryId == category.id,
                    onClick = { onCategorySelected(category.id) }
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "$shownCount ${if (shownCount == 1) "ITEM" else "ITEMS"}",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = KasaMuted,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun CategoryPill(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isActive) KasaAccent else Color.White)
            .border(
                width = 1.dp,
                color = if (isActive) KasaAccent else KasaLine,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.6.sp,
            color = if (isActive) Color.White else KasaInk
        )
    }
}

@Composable
private fun MenuGridContent(
    menuItems: List<MenuItem>,
    draftCart: Map<String, DraftCartItem>,
    isFiltered: Boolean,
    onItemClick: (MenuItem) -> Unit,
    onResetFilters: () -> Unit,
    onOpenMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (menuItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, KasaInk, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFiltered) Icons.Outlined.Search else Icons.Outlined.RestaurantMenu,
                        contentDescription = null,
                        tint = KasaInk,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isFiltered) "Menu tidak ditemukan" else "Belum ada menu tersedia",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isFiltered) {
                        "Coba hapus filter pencarian atau pindah kategori."
                    } else {
                        "Tambahkan menu terlebih dahulu melalui halaman Menu Management."
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = KasaMuted,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = if (isFiltered) onResetFilters else onOpenMenuClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KasaInk),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text(
                        text = if (isFiltered) "RESET FILTERS" else "KELOLA MENU",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp,
                        color = Color.White
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 175.dp),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(menuItems, key = { it.id }) { item ->
                    val qtyInCart = draftCart[item.id]?.quantity ?: 0
                    MenuCatalogCard(
                        item = item,
                        qtyInCart = qtyInCart,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuCatalogCard(
    item: MenuItem,
    qtyInCart: Int,
    onClick: () -> Unit
) {
    val isSelected = qtyInCart > 0
    val borderColor = if (isSelected) KasaInk else KasaLine
    val borderWidth = if (isSelected) 2.dp else 1.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(KasaSurface)
                    .border(1.dp, KasaLine, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (!item.imageUri.isNullOrBlank()) {
                    AsyncImage(
                        model = item.imageUri,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.RestaurantMenu,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.categoryName.uppercase(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9CA3AF),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(KasaAccent)
                            .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "×$qtyInCart",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KasaInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.categoryName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = KasaMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 1.dp, color = KasaLine)
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatRupiah(item.price),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KasaInk
            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, KasaLine, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaInk
                )
            }
        }
    }
}

@Composable
private fun TableGridRightPanel(
    occupiedTables: List<TableSlotUi>,
    onSelectOccupiedTable: (TableSlotUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DINE-IN",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaInk,
                    letterSpacing = (-0.5).sp
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (occupiedTables.isNotEmpty()) KasaOrange else KasaSurface)
                        .border(
                            width = 1.dp,
                            color = if (occupiedTables.isNotEmpty()) KasaOrange else KasaLine,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${occupiedTables.size} MEJA AKTIF",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (occupiedTables.isNotEmpty()) Color.White else KasaMuted
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Ketuk meja di panel kiri untuk memulai pesanan baru atau kelola tagihan meja aktif.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = KasaMuted
            )
        }

        HorizontalDivider(thickness = 1.dp, color = KasaLine)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (occupiedTables.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(KasaSurface)
                            .border(1.dp, KasaLine, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.TableRestaurant,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Belum ada meja yang terisi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pilih meja putih di sebelah kiri, masukkan jumlah orang, lalu pilih menu.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = KasaMuted,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item(key = "active_header") {
                        Text(
                            text = "OPEN BILL MEJA AKTIF",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.6.sp
                        )
                    }

                    items(occupiedTables, key = { it.id }) { slot ->
                        val activeOrder = slot.activeOrder ?: return@items
                        val itemCount = activeOrder.items.sumOf { it.quantity }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(KasaOrangeBg)
                                .border(1.5.dp, KasaOrange, RoundedCornerShape(8.dp))
                                .clickable { onSelectOccupiedTable(slot) }
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = slot.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KasaOrangeDark
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(KasaOrangeBadge)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = formatOrderNumber(activeOrder.order.orderNumber),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = KasaOrangeDark
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${activeOrder.order.guestCount} Orang • $itemCount Item",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = KasaOrangeDark
                                )
                            }

                            Text(
                                text = formatRupiah(activeOrder.order.grandTotal),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KasaOrangeDark
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(thickness = 1.dp, color = KasaInk)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(KasaSurface)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TOTAL TAGIHAN",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaMuted
                )
                Text(
                    text = formatRupiah(0L),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaMuted
                )
            }

            Button(
                onClick = {},
                enabled = false,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = KasaDisabledBg,
                    disabledContentColor = KasaMuted
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = "PILIH MEJA TERLEBIH DAHULU",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.6.sp
                )
            }
        }
    }
}

@Composable
private fun ActiveOrderRightPanel(
    stage: DashboardStage.MenuOrder,
    cartEntries: List<DraftCartItem>,
    totals: OrderTotals,
    isProcessing: Boolean,
    onEditGuestCountClick: () -> Unit,
    onClearCartClick: () -> Unit,
    onCancelOpenOrderClick: () -> Unit,
    onAdjustItemQuantity: (String, Int) -> Unit,
    onSubmitOrderClick: () -> Unit,
    onPayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Color.White)
    ) {
        // Order Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stage.tableName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaInk,
                        letterSpacing = (-0.5).sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (stage.isExistingOrder) KasaOrange else KasaInk)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = stage.existingOrderNumber?.let(::formatOrderNumber) ?: "ORDER BARU",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(KasaAccent)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${totals.totalQty} ITEM",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }
                }

                if (stage.isExistingOrder) {
                    OutlinedButton(
                        onClick = onCancelOpenOrderClick,
                        enabled = !isProcessing,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, KasaDanger.copy(alpha = 0.5f)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier.height(42.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "Batalkan Order",
                            tint = KasaDanger,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "BATALKAN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaDanger,
                            letterSpacing = 0.5.sp
                        )
                    }
                } else {
                    val isCartEmpty = cartEntries.isEmpty()
                    OutlinedButton(
                        onClick = onClearCartClick,
                        enabled = !isCartEmpty && !isProcessing,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, KasaLine),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier
                            .height(42.dp)
                            .alpha(if (isCartEmpty) 0.4f else 1f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "Clear cart",
                            tint = KasaMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CLEAR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            // Dine-In Info + Editable Guest Count Pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(KasaSurface)
                    .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MODE: DINE-IN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaInk,
                    letterSpacing = 0.6.sp
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                        .border(1.dp, KasaLine, RoundedCornerShape(6.dp))
                        .clickable { onEditGuestCountClick() }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Group,
                        contentDescription = "Jumlah Tamu",
                        tint = KasaAccent,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "${stage.guestCount} Orang",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaInk
                    )
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Ubah Jumlah Tamu",
                        tint = KasaMuted,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }

        HorizontalDivider(thickness = 1.dp, color = KasaLine)

        // Scrollable Cart List
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (cartEntries.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(KasaSurface)
                            .border(1.dp, KasaLine, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Belum ada item dipilih",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ketuk menu di sebelah kiri untuk menambah ke pesanan meja.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = KasaMuted,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(cartEntries, key = { it.menuItemId }) { item ->
                        val lineTotal = item.unitPrice * item.quantity
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 10.dp)
                            ) {
                                Text(
                                    text = item.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KasaInk,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = formatRupiah(item.unitPrice),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = KasaMuted
                                    )
                                    Text(
                                        text = "•",
                                        fontSize = 12.sp,
                                        color = Color(0xFFD1D5DB)
                                    )
                                    Text(
                                        text = formatRupiah(lineTotal),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KasaInk
                                    )
                                }
                            }

                            // Tactile Quantity Stepper (44dp touch targets)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .border(1.dp, KasaInk, RoundedCornerShape(8.dp))
                                        .clickable(enabled = !isProcessing) {
                                            onAdjustItemQuantity(item.menuItemId, -1)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "−",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KasaInk
                                    )
                                }
                                Text(
                                    text = item.quantity.toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KasaInk,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(32.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .border(1.dp, KasaInk, RoundedCornerShape(8.dp))
                                        .clickable(enabled = !isProcessing) {
                                            onAdjustItemQuantity(item.menuItemId, 1)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KasaInk
                                    )
                                }
                            }
                        }
                        HorizontalDivider(thickness = 1.dp, color = KasaLine)
                    }
                }
            }
        }

        HorizontalDivider(thickness = 1.dp, color = KasaInk)

        // Summary & Actions Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(KasaSurface)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KasaMuted
                    )
                    Text(
                        text = formatRupiah(totals.subtotal),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KasaInk
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tax (11% PPN)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KasaMuted
                    )
                    Text(
                        text = formatRupiah(totals.taxAmount),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KasaInk
                    )
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = KasaLine,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOTAL AMOUNT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaInk
                    )
                    Text(
                        text = formatRupiah(totals.grandTotal),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaInk,
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            if (!stage.isExistingOrder) {
                Button(
                    onClick = onSubmitOrderClick,
                    enabled = cartEntries.isNotEmpty() && !isProcessing,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KasaAccent,
                        contentColor = Color.White,
                        disabledContainerColor = KasaDisabledBg,
                        disabledContentColor = KasaMuted
                    ),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ORDER",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "Simpan Meja — ${formatRupiah(totals.grandTotal)}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onSubmitOrderClick,
                        enabled = cartEntries.isNotEmpty() && !isProcessing,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = if (cartEntries.isNotEmpty() && !isProcessing) KasaInk else KasaLine
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = KasaInk,
                            disabledContentColor = KasaMuted
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Text(
                            text = "UPDATE ORDER",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = onPayClick,
                        enabled = cartEntries.isNotEmpty() && !isProcessing,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KasaAccent,
                            contentColor = Color.White,
                            disabledContainerColor = KasaDisabledBg,
                            disabledContentColor = KasaMuted
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Text(
                            text = "BAYAR",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GuestCountDialog(
    tableName: String,
    initialCount: Int,
    confirmLabel: String,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var guestCount by remember(tableName, initialCount) {
        mutableIntStateOf(initialCount.coerceAtLeast(1))
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            border = BorderStroke(2.dp, KasaInk),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DINE-IN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaAccent,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = tableName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = KasaMuted
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = KasaLine)

                Text(
                    text = "Berapa orang yang duduk di meja ini?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = KasaInk
                )

                // Large Tactile Stepper
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(KasaSurface)
                        .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(
                                width = 1.dp,
                                color = if (guestCount > 1) KasaInk else KasaLine,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = guestCount > 1) { guestCount -= 1 },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "−",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (guestCount > 1) KasaInk else KasaDisabledBg
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = guestCount.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                        Text(
                            text = "ORANG",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.8.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(1.dp, KasaInk, RoundedCornerShape(8.dp))
                            .clickable { guestCount += 1 },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                    }
                }

                // Quick Preset Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(1, 2, 3, 4, 6, 8).forEach { preset ->
                        val active = guestCount == preset
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (active) KasaInk else Color.White)
                                .border(
                                    width = 1.dp,
                                    color = if (active) KasaInk else KasaLine,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { guestCount = preset },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = preset.toString(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (active) Color.White else KasaInk
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, KasaLine),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "BATAL",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted
                        )
                    }
                    Button(
                        onClick = { onConfirm(guestCount) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KasaAccent),
                        modifier = Modifier
                            .weight(1.6f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = confirmLabel.uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentCheckoutDialog(
    tableName: String,
    guestCount: Int,
    orderNumberLabel: String,
    totals: OrderTotals,
    isProcessing: Boolean,
    onDismiss: () -> Unit,
    onConfirmPayment: (method: String, cashReceived: Long?) -> Unit
) {
    var selectedMethod by remember { mutableStateOf("Cash") }
    var cashDigits by remember(totals.grandTotal) { mutableStateOf(totals.grandTotal.toString()) }

    val cashAmount = cashDigits.toLongOrNull() ?: 0L
    val changeAmount = cashAmount - totals.grandTotal
    val isCashValid = selectedMethod != "Cash" || cashAmount >= totals.grandTotal

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            border = BorderStroke(2.dp, KasaInk),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 460.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "PEMBAYARAN DINE-IN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaAccent,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "$tableName • $orderNumberLabel",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                        Text(
                            text = "$guestCount Orang • ${totals.totalQty} Item",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = KasaMuted
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = KasaMuted
                        )
                    }
                }

                // Total Tagihan Highlight
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(KasaSurface)
                        .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", fontSize = 12.sp, color = KasaMuted, fontWeight = FontWeight.SemiBold)
                        Text(formatRupiah(totals.subtotal), fontSize = 12.sp, color = KasaInk, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tax (11% PPN)", fontSize = 12.sp, color = KasaMuted, fontWeight = FontWeight.SemiBold)
                        Text(formatRupiah(totals.taxAmount), fontSize = 12.sp, color = KasaInk, fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(thickness = 1.dp, color = KasaLine, modifier = Modifier.padding(vertical = 2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TOTAL TAGIHAN",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                        Text(
                            text = formatRupiah(totals.grandTotal),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                    }
                }

                // Payment Method Selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "METODE PEMBAYARAN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KasaMuted,
                        letterSpacing = 0.6.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Cash", "QRIS", "Card").forEach { method ->
                            val active = selectedMethod == method
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (active) KasaInk else Color.White)
                                    .border(
                                        width = 1.dp,
                                        color = if (active) KasaInk else KasaLine,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedMethod = method },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = method.uppercase(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (active) Color.White else KasaInk
                                )
                            }
                        }
                    }
                }

                // Cash Quick Input & Change Calculation
                if (selectedMethod == "Cash") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "UANG DITERIMA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.6.sp
                        )

                        OutlinedTextField(
                            value = cashDigits,
                            onValueChange = { raw ->
                                cashDigits = raw.filter { it.isDigit() }.take(10)
                            },
                            singleLine = true,
                            prefix = {
                                Text(
                                    text = "Rp ",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KasaInk
                                )
                            },
                            visualTransformation = ThousandSeparatorTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = KasaInk,
                                unfocusedBorderColor = KasaLine,
                                focusedTextColor = KasaInk,
                                unfocusedTextColor = KasaInk
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Quick Cash Presets
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val presets = listOf(
                                "Uang Pas" to totals.grandTotal,
                                "50 rb" to 50_000L,
                                "100 rb" to 100_000L,
                                "200 rb" to 200_000L
                            )
                            presets.forEach { (label, value) ->
                                val isChosen = cashAmount == value
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isChosen) KasaAccent else KasaSurface)
                                        .border(
                                            width = 1.dp,
                                            color = if (isChosen) KasaAccent else KasaLine,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { cashDigits = value.toString() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (isChosen) Color.White else KasaInk,
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (changeAmount >= 0L) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (changeAmount >= 0L) Color(0xFFA7F3D0) else Color(0xFFFECACA),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (changeAmount >= 0L) "KEMBALIAN" else "KURANG",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (changeAmount >= 0L) KasaAccent else KasaDanger
                            )
                            Text(
                                text = formatRupiah(kotlin.math.abs(changeAmount)),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (changeAmount >= 0L) KasaAccent else KasaDanger
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        enabled = !isProcessing,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, KasaLine),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "BATAL",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted
                        )
                    }
                    Button(
                        onClick = {
                            onConfirmPayment(
                                selectedMethod,
                                if (selectedMethod == "Cash") cashAmount else null
                            )
                        },
                        enabled = isCashValid && !isProcessing,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KasaAccent,
                            contentColor = Color.White,
                            disabledContainerColor = KasaDisabledBg,
                            disabledContentColor = KasaMuted
                        ),
                        modifier = Modifier
                            .weight(1.6f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "KONFIRMASI BAYAR",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.6.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReceiptConfirmationDialog(
    receipt: CompletedReceiptUi,
    onStartNextOrder: () -> Unit
) {
    Dialog(onDismissRequest = onStartNextOrder) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            border = BorderStroke(2.dp, KasaInk),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
        ) {
            Column {
                // Green Modal Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(KasaAccent)
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TRANSAKSI LUNAS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White.copy(alpha = 0.8f),
                            letterSpacing = 1.2.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${formatOrderNumber(receipt.orderNumber)} Paid",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = (-0.5).sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Paid",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Modal Body
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Meja / Metode",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KasaMuted
                        )
                        Text(
                            text = "${receipt.tableName} (${receipt.guestCount} Org) • ${receipt.paymentMethod}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                    }

                    HorizontalDivider(thickness = 1.dp, color = KasaLine)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 176.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        receipt.items.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${item.quantity}× ${item.title}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KasaInk
                                )
                                Text(
                                    text = formatRupiah(item.unitPrice * item.quantity),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KasaInk
                                )
                            }
                        }
                    }

                    HorizontalDivider(thickness = 1.dp, color = KasaInk)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TOTAL CHARGED",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.6.sp
                        )
                        Text(
                            text = formatRupiah(receipt.grandTotal),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk,
                            letterSpacing = (-0.5).sp
                        )
                    }

                    if (receipt.cashReceived != null && receipt.changeAmount != null) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Tunai Diterima", fontSize = 12.sp, color = KasaMuted, fontWeight = FontWeight.Bold)
                                Text(formatRupiah(receipt.cashReceived), fontSize = 12.sp, color = KasaInk, fontWeight = FontWeight.Bold)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Kembalian", fontSize = 12.sp, color = KasaAccent, fontWeight = FontWeight.ExtraBold)
                                Text(formatRupiah(receipt.changeAmount), fontSize = 13.sp, color = KasaAccent, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }

                    Button(
                        onClick = onStartNextOrder,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KasaInk),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "SELESAI / KEMBALI KE MEJA",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.6.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CancelOrderConfirmationDialog(
    tableName: String,
    orderNumberLabel: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        title = {
            Text(
                text = "Batalkan Open Bill?",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KasaInk
            )
        },
        text = {
            Text(
                text = "Pesanan $orderNumberLabel untuk $tableName akan dibatalkan dan meja akan kembali tersedia (kosong).",
                fontSize = 13.sp,
                color = KasaMuted
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, KasaLine)
            ) {
                Text("Tidak", color = KasaInk, fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KasaDanger)
            ) {
                Text("Ya, Batalkan Order", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
private fun DiscardDraftConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirmDiscard: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        title = {
            Text(
                text = "Buang perubahan yang belum disimpan?",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KasaInk
            )
        },
        text = {
            Text(
                text = "Anda memiliki item atau perubahan yang belum disimpan. Kembali ke daftar meja akan membuang perubahan tersebut.",
                fontSize = 13.sp,
                color = KasaMuted
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, KasaLine)
            ) {
                Text("Tetap di Menu", color = KasaInk, fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmDiscard,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KasaInk)
            ) {
                Text("Ya, Kembali ke Meja", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    )
}
