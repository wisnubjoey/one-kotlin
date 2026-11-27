package com.bjoey.app.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bjoey.app.data.local.entity.StartedShiftSession
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

private val KasaInk = Color(0xFF111111)
private val KasaSurface = Color(0xFFF8F9FA)
private val KasaLine = Color(0xFFE5E7EB)
private val KasaAccent = Color(0xFF14532D)
private val KasaMuted = Color(0xFF6B7280)
private val KasaDisabledBg = Color(0xFFD1D5DB)

private data class PosProduct(
    val id: String,
    val name: String,
    val variant: String,
    val price: Int,
    val category: String,
    val seed: String
)

private val POS_CATEGORIES = listOf("All", "Category 1", "Category 2", "Category 3", "Category 4")

private val POS_PRODUCTS = listOf(
    PosProduct("item-01", "Menu Item 01", "Regular • 250g", 45000, "Category 1", "pos01"),
    PosProduct("item-02", "Menu Item 02", "Large • 350g", 58000, "Category 1", "pos02"),
    PosProduct("item-03", "Signature Drink A", "Iced • 16oz", 32000, "Category 2", "pos03"),
    PosProduct("item-04", "Signature Drink B", "Hot • 12oz", 28000, "Category 2", "pos04"),
    PosProduct("item-05", "Snack Plate A", "Sharing • 4 pcs", 36000, "Category 3", "pos05"),
    PosProduct("item-06", "Menu Item 03", "Standard • Bowl", 52000, "Category 1", "pos06"),
    PosProduct("item-07", "Dessert Box A", "Single Slice", 34000, "Category 4", "pos07"),
    PosProduct("item-08", "Signature Drink C", "Double Shot • 12oz", 35000, "Category 2", "pos08"),
    PosProduct("item-09", "Snack B", "Crispy • Medium", 26000, "Category 3", "pos09"),
    PosProduct("item-10", "Menu Item 04", "Combo Set • Plate", 65000, "Category 1", "pos10"),
    PosProduct("item-11", "Dessert Cup B", "Chilled • 180ml", 29000, "Category 4", "pos11"),
    PosProduct("item-12", "Snack C", "Light Bite • 3 pcs", 24000, "Category 3", "pos12"),
    PosProduct("item-13", "Signature Drink D", "Sparkling • 16oz", 30000, "Category 2", "pos13"),
    PosProduct("item-14", "Menu Item 05", "Chef Special • 300g", 62000, "Category 1", "pos14"),
    PosProduct("item-15", "Pastry Item A", "Fresh Baked", 25000, "Category 4", "pos15"),
    PosProduct("item-16", "Side Dish D", "Small Portion", 20000, "Category 3", "pos16")
)

private fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return "Rp ${formatter.format(amount)}"
}

private fun formatOrderNum(num: Int): String {
    return "#ORD-${num.toString().padStart(3, '0')}"
}

/**
 * Official KASA POS Dashboard converted from index.html into native Jetpack Compose.
 */
@Composable
fun DashboardScreen(
    session: StartedShiftSession,
    isOnline: Boolean?,
    isPaused: Boolean,
    onOpenMonitorClick: () -> Unit,
    onLockClick: () -> Unit,
    onOpenSettingsClick: () -> Unit = {},
    onOpenMenuClick: () -> Unit = {}
) {
    var activeCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchFocused by remember { mutableStateOf(false) }
    var orderNumber by remember { mutableIntStateOf(1) }
    var orderType by remember { mutableStateOf("Dine-In") }
    var paymentMethod by remember { mutableStateOf("Cash") }
    var showReceiptModal by remember { mutableStateOf(false) }

    // Pre-populate cart to match index.html initial checkout state
    var cart by remember {
        mutableStateOf(
            mapOf(
                "item-01" to 1,
                "item-02" to 1,
                "item-03" to 1
            )
        )
    }

    fun updateItemQuantity(productId: String, delta: Int) {
        val current = cart[productId] ?: 0
        val next = current + delta
        cart = if (next <= 0) {
            cart - productId
        } else {
            cart + (productId to next)
        }
    }

    val trimmedQuery = searchQuery.trim().lowercase()
    val filteredProducts = remember(activeCategory, trimmedQuery) {
        POS_PRODUCTS.filter { item ->
            val matchesCat = activeCategory == "All" || item.category == activeCategory
            val matchesSearch = trimmedQuery.isEmpty() ||
                item.name.lowercase().contains(trimmedQuery) ||
                item.variant.lowercase().contains(trimmedQuery) ||
                item.id.lowercase().contains(trimmedQuery)
            matchesCat && matchesSearch
        }
    }

    val cartEntries = remember(cart) {
        cart.entries.mapNotNull { (id, qty) ->
            POS_PRODUCTS.find { it.id == id }?.let { it to qty }
        }
    }
    val totalQty = cartEntries.sumOf { it.second }
    val subtotal = cartEntries.sumOf { (product, qty) -> product.price * qty }
    val tax = (subtotal * 0.11).roundToInt()
    val grandTotal = subtotal + tax

    if (showReceiptModal && cartEntries.isNotEmpty()) {
        ReceiptConfirmationDialog(
            orderTitle = "${formatOrderNum(orderNumber)} Paid",
            orderMeta = "$orderType • $paymentMethod",
            cartEntries = cartEntries,
            grandTotal = grandTotal,
            onStartNextOrder = {
                orderNumber += 1
                cart = emptyMap()
                showReceiptModal = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ==========================================
        // LEFT / MAIN PANEL (65% WIDTH) — CATALOG
        // ==========================================
        Column(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
                .background(KasaSurface)
        ) {
            // Top Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.White)
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Settings Button
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

                // Menu Management Button
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
                                    text = "Search menu item or code...",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF9CA3AF),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
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
                                onClick = { searchQuery = "" },
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

            HorizontalDivider(thickness = 1.dp, color = KasaLine)

            // Category Filter Strip
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
                    items(POS_CATEGORIES) { category ->
                        val isActive = activeCategory == category
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
                                .clickable { activeCategory = category }
                                .padding(horizontal = 18.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category.uppercase(),
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
                    text = "${filteredProducts.size} ${if (filteredProducts.size == 1) "ITEM" else "ITEMS"}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KasaMuted,
                    letterSpacing = 0.6.sp
                )
            }

            HorizontalDivider(thickness = 1.dp, color = KasaLine)

            // Product Grid / Empty State
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (filteredProducts.isEmpty()) {
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
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null,
                                tint = KasaInk,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No matching items found",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try clearing your search filter or switching categories.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = KasaMuted,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                activeCategory = "All"
                                searchQuery = ""
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KasaInk),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Text(
                                text = "RESET FILTERS",
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
                        items(filteredProducts, key = { it.id }) { product ->
                            val qtyInCart = cart[product.id] ?: 0
                            ProductCatalogCard(
                                product = product,
                                qtyInCart = qtyInCart,
                                onClick = { updateItemQuantity(product.id, 1) }
                            )
                        }
                    }
                }
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
        // RIGHT PANEL (35% WIDTH) — ORDER & CHECKOUT
        // ==========================================
        Column(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            // Order Header
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = formatOrderNum(orderNumber),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk,
                            letterSpacing = (-0.5).sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(KasaAccent)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "$totalQty ${if (totalQty == 1) "ITEM" else "ITEMS"}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }

                    val isCartEmpty = cartEntries.isEmpty()
                    OutlinedButton(
                        onClick = { cart = emptyMap() },
                        enabled = !isCartEmpty,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, KasaLine),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        modifier = Modifier
                            .height(44.dp)
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
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.6.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Order Type Toggle (Dine-In / Takeaway)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(KasaSurface)
                        .border(1.dp, KasaLine, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Dine-In", "Takeaway").forEach { type ->
                        val active = orderType == type
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (active) KasaInk else Color.Transparent)
                                .clickable { orderType = type },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type.uppercase(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.6.sp,
                                color = if (active) Color.White else KasaMuted
                            )
                        }
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
                            text = "No items in current order",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap any product card on the left to add it.",
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
                        items(cartEntries, key = { it.first.id }) { (product, qty) ->
                            val lineTotal = product.price * qty
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
                                        text = product.name,
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
                                            text = formatRupiah(product.price),
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
                                            .clickable { updateItemQuantity(product.id, -1) },
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
                                        text = qty.toString(),
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
                                            .clickable { updateItemQuantity(product.id, 1) },
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

            // Summary & Payment Footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(KasaSurface)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Breakdown Rows
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
                            text = formatRupiah(subtotal),
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
                            text = formatRupiah(tax),
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
                            text = formatRupiah(grandTotal),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk,
                            letterSpacing = (-0.5).sp
                        )
                    }
                }

                // Payment Method Toggles
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PAYMENT METHOD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaMuted,
                            letterSpacing = 0.6.sp
                        )
                        Text(
                            text = "$orderType • $paymentMethod",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = KasaInk
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Cash", "QRIS", "Card").forEach { method ->
                            val active = paymentMethod == method
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(54.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (active) KasaInk else Color.White)
                                    .border(
                                        width = 1.dp,
                                        color = if (active) KasaInk else KasaLine,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { paymentMethod = method },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = method.uppercase(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.6.sp,
                                    color = if (active) Color.White else KasaInk
                                )
                            }
                        }
                    }
                }

                // Primary High-Impact Dark Green CTA Button
                Button(
                    onClick = { showReceiptModal = true },
                    enabled = cartEntries.isNotEmpty(),
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
                            text = "CHARGE ORDER",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.6.sp
                        )
                        Text(
                            text = "Charge — ${formatRupiah(grandTotal)}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCatalogCard(
    product: PosProduct,
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
            // Top 4:3 Image Placeholder Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(KasaSurface)
                    .border(1.dp, KasaLine, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = product.seed.uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF9CA3AF),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = product.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD1D5DB)
                    )
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
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = KasaInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = product.variant,
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
                text = formatRupiah(product.price),
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
private fun ReceiptConfirmationDialog(
    orderTitle: String,
    orderMeta: String,
    cartEntries: List<Pair<PosProduct, Int>>,
    grandTotal: Int,
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
                            text = "TRANSACTION COMPLETE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White.copy(alpha = 0.8f),
                            letterSpacing = 1.2.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = orderTitle,
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Order Type / Method",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KasaMuted
                        )
                        Text(
                            text = orderMeta,
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
                        cartEntries.forEach { (product, qty) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${qty}× ${product.name}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KasaInk
                                )
                                Text(
                                    text = formatRupiah(product.price * qty),
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
                            text = formatRupiah(grandTotal),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KasaInk,
                            letterSpacing = (-0.5).sp
                        )
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
                            text = "START NEXT ORDER",
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
