package com.bjoey.app.archive.cashier

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

// ============================================================================
// DATA MODELS (Sesuai persis dengan index.html)
// ============================================================================

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val cat: String,
    val abbr: String
)

data class ModifierOption(
    val label: String,
    val price: Double = 0.0,
    val isDefault: Boolean = false
)

data class ModifierGroup(
    val key: String,
    val label: String,
    val options: List<ModifierOption>
)

data class CartLine(
    val id: Long,
    val productId: Int,
    val name: String,
    val unitPrice: Double,
    val basePrice: Double,
    val mods: String,
    var qty: Int
)

// ============================================================================
// KATALOG 27 PRODUK LENGKAP (Identik dengan index.html)
// ============================================================================

val POS_PRODUCTS = listOf(
    Product(1, "Espresso", 3.00, "espresso", "ESP"),
    Product(2, "Americano", 3.75, "espresso", "AME"),
    Product(3, "Cappuccino", 4.25, "espresso", "CAP"),
    Product(4, "Flat White", 4.50, "espresso", "FW"),
    Product(5, "Latte", 4.75, "espresso", "LAT"),
    Product(6, "Mocha", 5.25, "espresso", "MOC"),
    Product(7, "Cortado", 4.00, "espresso", "COR"),
    Product(8, "Macchiato", 4.00, "espresso", "MAC"),
    Product(9, "Pour Over", 5.00, "manual", "POV"),
    Product(10, "V60", 5.50, "manual", "V60"),
    Product(11, "Chemex", 6.50, "manual", "CHX"),
    Product(12, "Aeropress", 5.25, "manual", "AER"),
    Product(13, "Hot Chocolate", 4.50, "noncoffee", "HC"),
    Product(14, "Matcha Latte", 5.50, "noncoffee", "MTL"),
    Product(15, "Chai Latte", 5.00, "noncoffee", "CHL"),
    Product(16, "Lemonade", 4.00, "noncoffee", "LMN"),
    Product(17, "Green Tea", 3.50, "tea", "GT"),
    Product(18, "Earl Grey", 3.50, "tea", "EG"),
    Product(19, "Jasmine", 3.75, "tea", "JAS"),
    Product(20, "Cold Brew", 4.50, "cold", "CB"),
    Product(21, "Nitro Cold Brew", 5.50, "cold", "NCB"),
    Product(22, "Iced Latte", 5.00, "cold", "IL"),
    Product(23, "Croissant", 3.50, "food", "CR"),
    Product(24, "Pain au Chocolat", 4.00, "food", "PDC"),
    Product(25, "Blueberry Muffin", 3.75, "food", "BM"),
    Product(26, "Cinnamon Roll", 4.50, "food", "CR"),
    Product(27, "Banana Bread", 3.50, "food", "BB")
)

val POS_MOD_GROUPS = listOf(
    ModifierGroup(
        key = "size",
        label = "Size",
        options = listOf(
            ModifierOption("Small", 0.0),
            ModifierOption("Regular", 0.0, isDefault = true),
            ModifierOption("Large", 0.75)
        )
    ),
    ModifierGroup(
        key = "milk",
        label = "Milk",
        options = listOf(
            ModifierOption("Whole", 0.0, isDefault = true),
            ModifierOption("Oat", 0.50),
            ModifierOption("Almond", 0.50),
            ModifierOption("Skim", 0.0)
        )
    ),
    ModifierGroup(
        key = "shot",
        label = "Extra Shot",
        options = listOf(
            ModifierOption("None", 0.0, isDefault = true),
            ModifierOption("Single +", 0.75),
            ModifierOption("Double +", 1.50)
        )
    )
)

val POS_SUGAR_OPTIONS = listOf("None", "1", "2", "3")

val CATEGORIES = listOf(
    "all" to "All",
    "espresso" to "Espresso",
    "manual" to "Manual Brew",
    "noncoffee" to "Non-Coffee",
    "tea" to "Tea",
    "cold" to "Cold Brew",
    "food" to "Pastries"
)

// Color tokens identik Tailwind di index.html
private val BorderHairline = Color(0xFFE5E5E5)
private val BorderHairlineStrong = Color(0xFFD4D4D4)
private val Neutral50 = Color(0xFFF9FAFB)
private val Neutral100 = Color(0xFFF5F5F5)
private val Neutral200 = Color(0xFFE5E5E5)
private val Neutral400 = Color(0xFFA3A3A3)
private val Neutral500 = Color(0xFF737373)
private val Neutral600 = Color(0xFF525252)
private val Neutral700 = Color(0xFF404040)
private val Neutral900 = Color(0xFF171717)
private val AccentGreen = Color(0xFF064E3B)
private val AccentSoft = Color(0xFFECFDF5)

fun fmtPrice(amount: Double): String = String.format(Locale.US, "$%.2f", amount)

// ============================================================================
// MAIN SCREEN COMPOSABLE
// ============================================================================

@Composable
fun CashierDashboardScreen(
    cashierName: String,
    onLogout: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // State Navigasi Tab Samping
    var activeNav by remember { mutableStateOf("menu") }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // State Kategori & Pencarian
    var activeCat by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }

    // State Order & Keranjang
    var orderType by remember { mutableStateOf("dinein") } // "dinein" or "takeout"
    var cart by remember { mutableStateOf(listOf<CartLine>()) }
    var isPaying by remember { mutableStateOf(false) }
    var paidMessage by remember { mutableStateOf<String?>(null) }

    // State Modifier Drawer
    var activeProductForMod by remember { mutableStateOf<Product?>(null) }

    // Filter Produk Berdasarkan Kategori dan Search Query
    val filteredProducts = remember(activeCat, searchQuery) {
        POS_PRODUCTS.filter { product ->
            val matchCat = if (activeCat == "all") true else product.cat == activeCat
            val matchSearch = if (searchQuery.isBlank()) true else {
                product.name.contains(searchQuery, ignoreCase = true) ||
                        product.abbr.contains(searchQuery, ignoreCase = true)
            }
            matchCat && matchSearch
        }
    }

    // Perhitungan Total
    val subtotal = cart.sumOf { it.unitPrice * it.qty }
    val tax = subtotal * 0.08
    val total = subtotal + tax
    val totalItems = cart.sumOf { it.qty }

    // Dialog Konfirmasi Logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Keluar Sesi Kasir", fontWeight = FontWeight.Bold) },
            text = { Text("Kasir $cashierName, apakah Anda yakin ingin keluar dari sesi ini?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Row(modifier = Modifier.fillMaxSize()) {

            // ================================================================
            // 1. LEFT SIDEBAR (w-[88px] shrink-0 border-r)
            // ================================================================
            SidebarView(
                activeNav = activeNav,
                cashierName = cashierName,
                onNavClick = { activeNav = it },
                onAvatarClick = { showLogoutDialog = true }
            )

            // ================================================================
            // 2. CENTRAL AREA (flex-1 bg-neutral-50)
            // ================================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Neutral50)
            ) {
                // Top Bar (h-[72px])
                TopBarView(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it }
                )

                // Category Filter Tabs
                CategoryTabsView(
                    activeCat = activeCat,
                    onCategorySelect = { activeCat = it }
                )

                // Product Grid (flex-1)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 24.dp)
                ) {
                    if (filteredProducts.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No items in this category.",
                                color = Neutral400,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 160.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredProducts, key = { it.id }) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = { activeProductForMod = product }
                                )
                            }
                        }
                    }
                }
            }

            // ================================================================
            // 3. RIGHT ORDER PANEL (w-[350px] shrink-0 border-l)
            // ================================================================
            OrderPanelView(
                totalItems = totalItems,
                orderType = orderType,
                onOrderTypeChange = { orderType = it },
                cart = cart,
                subtotal = subtotal,
                tax = tax,
                total = total,
                isPaying = isPaying,
                paidMessage = paidMessage,
                onClearCart = { cart = emptyList() },
                onQtyChange = { lineId, delta ->
                    cart = cart.mapNotNull { line ->
                        if (line.id == lineId) {
                            val newQty = line.qty + delta
                            if (newQty > 0) line.copy(qty = newQty) else null
                        } else line
                    }
                },
                onRemoveItem = { lineId ->
                    cart = cart.filter { it.id != lineId }
                },
                onPayNow = {
                    if (cart.isNotEmpty() && !isPaying) {
                        isPaying = true
                        val amountStr = fmtPrice(total)
                        paidMessage = "Paid $amountStr"
                        coroutineScope.launch {
                            delay(1400)
                            cart = emptyList()
                            paidMessage = null
                            isPaying = false
                        }
                    }
                }
            )
        }

        // ================================================================
        // 4. MODIFIER DRAWER / OVERLAY (Muncul saat kartu produk diklik)
        // ================================================================
        AnimatedVisibility(
            visible = activeProductForMod != null,
            enter = fadeIn(animationSpec = tween(150)),
            exit = fadeOut(animationSpec = tween(150))
        ) {
            val product = activeProductForMod
            if (product != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            activeProductForMod = null
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(380.dp)
                            .background(Color.White)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { /* Block click pass-through */ }
                    ) {
                        ModifierSheetContent(
                            product = product,
                            onClose = { activeProductForMod = null },
                            onAddToCart = { selectedMods, qty ->
                                // Hitung summary mod & unit price
                                val modLabels = listOf("size", "milk", "shot")
                                    .mapNotNull { selectedMods[it]?.label }
                                    .joinToString(" · ")
                                val sugarVal = selectedMods["sugar"]?.label
                                val sugarLabel = if (!sugarVal.isNullOrBlank() && sugarVal != "None") "$sugarVal sugar" else ""
                                val modSummary = listOf(modLabels, sugarLabel).filter { it.isNotBlank() }.joinToString(" · ")

                                val extraPrice = (selectedMods["size"]?.price ?: 0.0) +
                                        (selectedMods["milk"]?.price ?: 0.0) +
                                        (selectedMods["shot"]?.price ?: 0.0)
                                val unitPrice = product.price + extraPrice

                                val existing = cart.find { it.productId == product.id && it.mods == modSummary }
                                if (existing != null) {
                                    cart = cart.map {
                                        if (it.id == existing.id) it.copy(qty = it.qty + qty) else it
                                    }
                                } else {
                                    cart = cart + CartLine(
                                        id = System.currentTimeMillis() + (0..1000).random(),
                                        productId = product.id,
                                        name = product.name,
                                        unitPrice = unitPrice,
                                        basePrice = product.price,
                                        mods = modSummary,
                                        qty = qty
                                    )
                                }
                                activeProductForMod = null
                            }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// 1. KOMPONEN SIDEBAR (w-[88px])
// ============================================================================

@Composable
private fun SidebarView(
    activeNav: String,
    cashierName: String,
    onNavClick: (String) -> Unit,
    onAvatarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(88.dp)
            .fillMaxHeight()
            .background(Color.White)
            .border(width = 1.dp, color = BorderHairline)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo & Nav Buttons
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Logo Cangkir Kopi
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Neutral900),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalCafe,
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Primary Nav
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NavButton(
                    label = "Menu",
                    icon = Icons.Default.GridView,
                    isActive = activeNav == "menu",
                    onClick = { onNavClick("menu") }
                )
                NavButton(
                    label = "Sales",
                    icon = Icons.Default.ReceiptLong,
                    isActive = activeNav == "sales",
                    onClick = { onNavClick("sales") }
                )
                NavButton(
                    label = "Settings",
                    icon = Icons.Default.Settings,
                    isActive = activeNav == "settings",
                    onClick = { onNavClick("settings") }
                )
            }
        }

        // Bottom: Barista Avatar & Versi
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val initials = remember(cashierName) {
                val parts = cashierName.trim().split(" ")
                if (parts.size >= 2) {
                    "${parts[0].take(1)}${parts[1].take(1)}".uppercase()
                } else {
                    cashierName.take(2).uppercase().ifBlank { "AR" }
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Neutral200)
                    .clickable { onAvatarClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Neutral700
                )
            }

            Text(
                text = "v1.0",
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = Neutral400,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun NavButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isActive) Neutral900 else Color.Transparent
    val contentColor = if (isActive) Color.White else Neutral500

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

// ============================================================================
// 2. CENTRAL AREA: TOP BAR & CATEGORY TABS
// ============================================================================

@Composable
private fun TopBarView(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    // Pulse animation untuk green dot
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
            .border(width = 1.dp, color = BorderHairline)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Judul & Badge Meja
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Order",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Neutral900
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(AccentSoft)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(AccentGreen.copy(alpha = alpha))
                )
                Text(
                    text = "Dine-in · Table 07",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentGreen
                )
            }
        }

        // Search Input & Today Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Input (w-56 h-10)
            Row(
                modifier = Modifier
                    .width(224.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Neutral100)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Neutral400,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Neutral900
                    ),
                    cursorBrush = SolidColor(Neutral900),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search menu…",
                                fontSize = 14.sp,
                                color = Neutral400
                            )
                        }
                        innerTextField()
                    }
                )
            }

            // Button "Today"
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = BorderHairlineStrong, shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Neutral700,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Today",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Neutral700
                )
            }
        }
    }
}

@Composable
private fun CategoryTabsView(
    activeCat: String,
    onCategorySelect: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = BorderHairline)
            .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(CATEGORIES) { (catKey, catLabel) ->
                val isActive = activeCat == catKey
                val bgColor = if (isActive) Neutral900 else Color.Transparent
                val textColor = if (isActive) Color.White else Neutral600

                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(bgColor)
                        .clickable { onCategorySelect(catKey) }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = catLabel,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )
                }
            }
        }
    }
}

// ============================================================================
// 3. PRODUCT CARD (Grid Item)
// ============================================================================

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = BorderHairline, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Baris Atas: Abbr Box & Harga
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Neutral100),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = product.abbr,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Neutral500,
                        letterSpacing = 0.5.sp
                    )
                }

                Text(
                    text = fmtPrice(product.price),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Neutral900
                )
            }

            // Baris Bawah: Nama Produk & Kategori
            Column {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Neutral900,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = product.cat.replaceFirstChar { it.uppercase() },
                    fontSize = 12.sp,
                    color = Neutral400
                )
            }
        }
    }
}

// ============================================================================
// 4. RIGHT ORDER PANEL (w-[350px])
// ============================================================================

@Composable
private fun OrderPanelView(
    totalItems: Int,
    orderType: String,
    onOrderTypeChange: (String) -> Unit,
    cart: List<CartLine>,
    subtotal: Double,
    tax: Double,
    total: Double,
    isPaying: Boolean,
    paidMessage: String?,
    onClearCart: () -> Unit,
    onQtyChange: (Long, Int) -> Unit,
    onRemoveItem: (Long) -> Unit,
    onPayNow: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(350.dp)
            .fillMaxHeight()
            .background(Color.White)
            .border(width = 1.dp, color = BorderHairline)
    ) {
        // Order Header (px-5 py-4 border-b)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = BorderHairline)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Current Order",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Neutral900
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "#A-1042 · $totalItems item${if (totalItems == 1) "" else "s"}",
                    fontSize = 12.sp,
                    color = Neutral400
                )
            }

            Text(
                text = "Clear",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (cart.isEmpty()) Neutral400 else Neutral700,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(enabled = cart.isNotEmpty()) { onClearCart() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // Order Type Toggle (Dine-in / Takeout)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = BorderHairline)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Neutral100)
                    .padding(4.dp)
            ) {
                listOf("dinein" to "Dine-in", "takeout" to "Takeout").forEach { (typeKey, typeLabel) ->
                    val isSelected = orderType == typeKey
                    val tabBg = if (isSelected) Color.White else Color.Transparent
                    val tabText = if (isSelected) Neutral900 else Neutral500

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(tabBg)
                            .clickable { onOrderTypeChange(typeKey) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = typeLabel,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = tabText
                        )
                    }
                }
            }
        }

        // Cart Lines (flex-1)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            if (cart.isEmpty()) {
                // Empty Cart State
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Neutral100),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = BorderHairlineStrong,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No items yet",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Neutral500
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Tap a product to add it",
                        fontSize = 12.sp,
                        color = Neutral400
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(cart, key = { it.id }) { line ->
                        CartLineItem(
                            line = line,
                            onQtyChange = { delta -> onQtyChange(line.id, delta) },
                            onRemove = { onRemoveItem(line.id) }
                        )
                    }
                }
            }
        }

        // Totals (Subtotal, Tax 8%, Discount, Total)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = BorderHairline)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Subtotal", fontSize = 14.sp, color = Neutral500)
                Text(text = fmtPrice(subtotal), fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Neutral900)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Tax (8%)", fontSize = 14.sp, color = Neutral500)
                Text(text = fmtPrice(tax), fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Neutral900)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Discount", fontSize = 14.sp, color = Neutral500)
                Text(text = "—", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Neutral400)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BorderHairline)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "Total", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Neutral900)
                Text(text = fmtPrice(total), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Neutral900)
            }
        }

        // Pay Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onPayNow,
                enabled = cart.isNotEmpty() && !isPaying,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentGreen,
                    disabledContainerColor = AccentGreen.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (paidMessage != null) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                        Text(text = paidMessage, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    } else {
                        Icon(imageVector = Icons.Default.CreditCard, contentDescription = null, tint = Color.White)
                        Text(text = "Pay Now", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Press to charge customer",
                fontSize = 11.sp,
                color = Neutral400
            )
        }
    }
}

@Composable
private fun CartLineItem(
    line: CartLine,
    onQtyChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = BorderHairline)
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = line.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Neutral900,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Hapus",
                tint = BorderHairlineStrong,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() }
            )
        }

        if (line.mods.isNotBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = line.mods,
                fontSize = 12.sp,
                color = Neutral400,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stepper [- Qty +]
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, BorderHairlineStrong, RoundedCornerShape(6.dp))
                        .clickable { onQtyChange(-1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text("−", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Neutral700)
                }

                Text(
                    text = "${line.qty}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Neutral900,
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, BorderHairlineStrong, RoundedCornerShape(6.dp))
                        .clickable { onQtyChange(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Neutral700)
                }
            }

            Text(
                text = fmtPrice(line.unitPrice * line.qty),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Neutral900
            )
        }
    }
}

// ============================================================================
// 5. MODIFIER SHEET (Drawer Samping Kanan w-[380px])
// ============================================================================

@Composable
private fun ModifierSheetContent(
    product: Product,
    onClose: () -> Unit,
    onAddToCart: (selectedMods: Map<String, ModifierOption>, qty: Int) -> Unit
) {
    // Inisialisasi default modifiers
    val selectedOptions = remember(product.id) {
        mutableStateMapOf<String, ModifierOption>().apply {
            POS_MOD_GROUPS.forEach { g ->
                val def = g.options.find { it.isDefault } ?: g.options.first()
                put(g.key, def)
            }
            put("sugar", ModifierOption(label = "None"))
        }
    }

    var qty by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Sheet Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderHairline)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Neutral900
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Customize this item",
                    fontSize = 12.sp,
                    color = Neutral400
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Neutral100)
                    .clickable { onClose() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Tutup",
                    tint = Neutral500,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Sheet Body
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Radio Groups: Size, Milk, Shot
            items(POS_MOD_GROUPS) { group ->
                Column {
                    Text(
                        text = group.label.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Neutral400,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        group.options.forEach { opt ->
                            val isSelected = selectedOptions[group.key]?.label == opt.label
                            val optBg = if (isSelected) Neutral900 else Color.White
                            val optBorder = if (isSelected) Neutral900 else BorderHairlineStrong
                            val optText = if (isSelected) Color.White else Neutral700

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, optBorder, RoundedCornerShape(8.dp))
                                    .background(optBg)
                                    .clickable { selectedOptions[group.key] = opt },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (opt.price > 0) "${opt.label} +${fmtPrice(opt.price)}" else opt.label,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = optText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // Sugar Pills
            item {
                Column {
                    Text(
                        text = "SUGAR",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Neutral400,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        POS_SUGAR_OPTIONS.forEach { s ->
                            val isSelected = selectedOptions["sugar"]?.label == s
                            val sugarBg = if (isSelected) Neutral900 else Color.White
                            val sugarBorder = if (isSelected) Neutral900 else BorderHairlineStrong
                            val sugarText = if (isSelected) Color.White else Neutral700

                            Box(
                                modifier = Modifier
                                    .height(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, sugarBorder, RoundedCornerShape(8.dp))
                                    .background(sugarBg)
                                    .clickable { selectedOptions["sugar"] = ModifierOption(label = s) }
                                    .padding(horizontal = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = s,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = sugarText
                                )
                            }
                        }
                    }
                }
            }

            // Quantity Selector
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 0.5.dp, color = BorderHairline)
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "QUANTITY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Neutral400,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, BorderHairlineStrong, RoundedCornerShape(8.dp))
                                .clickable { qty = maxOf(1, qty - 1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("−", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Neutral700)
                        }

                        Text(
                            text = "$qty",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Neutral900,
                            modifier = Modifier.width(48.dp),
                            textAlign = TextAlign.Center
                        )

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, BorderHairlineStrong, RoundedCornerShape(8.dp))
                                .clickable { qty++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Neutral700)
                        }
                    }
                }
            }
        }

        // Sheet Footer: Add to Order Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderHairline)
                .padding(24.dp)
        ) {
            Button(
                onClick = { onAddToCart(selectedOptions.toMap(), qty) },
                colors = ButtonDefaults.buttonColors(containerColor = Neutral900),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Add to Order",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}
