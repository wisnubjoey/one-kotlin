package com.bjoey.app.ui.settings.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MenuListScreen(
    menuItems: List<MenuItem>,
    categories: List<MenuCategory>,
    selectedCategoryId: String?,
    totalCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelected: (String?) -> Unit,
    onAddCategory: (String) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteConfirm: (MenuItem) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    if (onBackClick != null) {
        BackHandler(onBack = onBackClick)
    }

    var pendingDeleteItem by remember { mutableStateOf<MenuItem?>(null) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }

    pendingDeleteItem?.let { item ->
        DeleteMenuConfirmationDialog(
            item = item,
            onDismiss = { pendingDeleteItem = null },
            onConfirm = {
                onDeleteConfirm(item)
                pendingDeleteItem = null
            }
        )
    }

    if (showAddCategoryDialog) {
        AddCategoryDialog(
            onDismiss = { showAddCategoryDialog = false },
            onConfirm = { name ->
                onAddCategory(name)
                showAddCategoryDialog = false
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MenuColors.ScreenBackground)
    ) {
        MenuListHeader(
            totalCount = totalCount,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onAddClick = onAddClick,
            onBackClick = onBackClick
        )

        HorizontalDivider(thickness = 1.dp, color = MenuColors.BorderDefault)

        CategoryFilterStrip(
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = onCategorySelected,
            onAddCategoryClick = { showAddCategoryDialog = true }
        )

        HorizontalDivider(thickness = 1.dp, color = MenuColors.BorderDefault)

        if (menuItems.isEmpty()) {
            val isFiltered = searchQuery.isNotBlank() || selectedCategoryId != null
            MenuEmptyState(
                isFiltered = isFiltered,
                onResetFilters = {
                    onSearchQueryChange("")
                    onCategorySelected(null)
                },
                onAddClick = onAddClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 170.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = menuItems,
                    key = { it.id }
                ) { item ->
                    MenuCard(
                        item = item,
                        onEditClick = { onEditClick(item.id) },
                        onDeleteClick = { pendingDeleteItem = item }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterStrip(
    categories: List<MenuCategory>,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit,
    onAddCategoryClick: () -> Unit
) {
    Surface(
        color = MenuColors.CardSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item(key = "all") {
                CategoryFilterChip(
                    label = "Semua",
                    selected = selectedCategoryId == null,
                    onClick = { onCategorySelected(null) }
                )
            }

            items(
                items = categories,
                key = { it.id }
            ) { category ->
                CategoryFilterChip(
                    label = category.name,
                    selected = selectedCategoryId == category.id,
                    onClick = { onCategorySelected(category.id) }
                )
            }

            item(key = "add_category") {
                Surface(
                    shape = CircleShape,
                    color = MenuColors.ScreenBackground,
                    border = BorderStroke(1.dp, MenuColors.BorderDefault),
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onAddCategoryClick)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Kategori",
                            tint = MenuColors.TextPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Kategori",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MenuColors.TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = if (selected) MenuColors.PrimaryAccent else MenuColors.CardSurface,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) MenuColors.PrimaryAccent else MenuColors.BorderDefault
        ),
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else MenuColors.TextPrimary,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
        )
    }
}

@Composable
private fun MenuListHeader(
    totalCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onBackClick: (() -> Unit)?
) {
    Surface(
        color = MenuColors.CardSurface,
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
                            tint = MenuColors.TextPrimary
                        )
                    }
                }

                Text(
                    text = "Menu Items",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MenuColors.TextPrimary
                )

                Surface(
                    shape = CircleShape,
                    color = MenuColors.BadgeBackground,
                    border = BorderStroke(1.dp, MenuColors.BorderDefault)
                ) {
                    Text(
                        text = "$totalCount Menu",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MenuColors.TextPrimary,
                        style = TextStyle(fontFeatureSettings = "tnum"),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }
            }

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isCompactWidth = maxWidth < 540.dp

                if (isCompactWidth) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MenuSearchField(
                            query = searchQuery,
                            onQueryChange = onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        AddMenuButton(
                            onClick = onAddClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MenuSearchField(
                            query = searchQuery,
                            onQueryChange = onSearchQueryChange,
                            modifier = Modifier.weight(1f)
                        )
                        AddMenuButton(
                            onClick = onAddClick,
                            modifier = Modifier.height(52.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuSearchField(
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
                text = "Cari nama menu...",
                color = MenuColors.TextSecondary,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Cari",
                tint = MenuColors.TextSecondary
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hapus pencarian",
                        tint = MenuColors.TextSecondary
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MenuColors.BorderFocused,
            unfocusedBorderColor = MenuColors.BorderDefault,
            focusedContainerColor = MenuColors.CardSurface,
            unfocusedContainerColor = MenuColors.CardSurface,
            focusedTextColor = MenuColors.TextPrimary,
            unfocusedTextColor = MenuColors.TextPrimary,
            cursorColor = MenuColors.BorderFocused
        )
    )
}

@Composable
private fun AddMenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MenuColors.PrimaryAccent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Text(
            text = "+ Tambah Menu",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MenuCard(
    item: MenuItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(12.dp)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = cardShape,
        color = MenuColors.CardSurface,
        border = BorderStroke(1.dp, MenuColors.BorderDefault)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(MenuColors.PlaceholderBackground),
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
                    Icon(
                        imageVector = Icons.Outlined.RestaurantMenu,
                        contentDescription = null,
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MenuColors.BadgeBackground,
                    border = BorderStroke(1.dp, MenuColors.BorderDefault)
                ) {
                    Text(
                        text = item.categoryName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MenuColors.TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MenuColors.TextPrimary,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = formatRupiah(item.price),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MenuColors.TextPrimary,
                    style = TextStyle(fontFeatureSettings = "tnum")
                )
            }

            HorizontalDivider(thickness = 1.dp, color = MenuColors.BorderDefault)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = MenuColors.TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Edit",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MenuColors.TextPrimary
                    )
                }

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = MenuColors.BorderDefault
                )

                TextButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteOutline,
                        contentDescription = null,
                        tint = MenuColors.Destructive,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Hapus",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MenuColors.Destructive
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuEmptyState(
    isFiltered: Boolean,
    onResetFilters: () -> Unit,
    onAddClick: () -> Unit,
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
                color = MenuColors.CardSurface,
                border = BorderStroke(1.dp, MenuColors.BorderDefault),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isFiltered) Icons.Outlined.Search else Icons.Outlined.RestaurantMenu,
                        contentDescription = null,
                        tint = MenuColors.TextSecondary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isFiltered) "Menu tidak ditemukan" else "Belum ada menu",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MenuColors.TextPrimary
            )

            Text(
                text = if (isFiltered) {
                    "Coba kata kunci atau kategori lain untuk melihat daftar menu."
                } else {
                    "Tambahkan item menu pertama untuk mulai mengelola katalog POS."
                },
                fontSize = 14.sp,
                color = MenuColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (isFiltered) {
                OutlinedButton(
                    onClick = onResetFilters,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, MenuColors.BorderDefault)
                ) {
                    Text(
                        text = "Reset Filter",
                        color = MenuColors.TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                AddMenuButton(onClick = onAddClick)
            }
        }
    }
}

@Composable
internal fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MenuColors.CardSurface,
        shape = RoundedCornerShape(12.dp),
        title = {
            Text(
                text = "Tambah Kategori",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MenuColors.TextPrimary
            )
        },
        text = {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                singleLine = true,
                label = { Text("Nama Kategori") },
                placeholder = { Text("Contoh: Snacks", color = MenuColors.TextSecondary) },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MenuColors.BorderFocused,
                    unfocusedBorderColor = MenuColors.BorderDefault,
                    focusedLabelColor = MenuColors.BorderFocused,
                    unfocusedLabelColor = MenuColors.TextSecondary,
                    focusedTextColor = MenuColors.TextPrimary,
                    unfocusedTextColor = MenuColors.TextPrimary,
                    cursorColor = MenuColors.BorderFocused
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MenuColors.BorderDefault)
            ) {
                Text(
                    text = "Batal",
                    color = MenuColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(categoryName.trim()) },
                enabled = categoryName.isNotBlank(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MenuColors.PrimaryAccent,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFD1D5DB),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "Simpan",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

@Composable
private fun DeleteMenuConfirmationDialog(
    item: MenuItem,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MenuColors.CardSurface,
        shape = RoundedCornerShape(12.dp),
        title = {
            Text(
                text = "Hapus Menu?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MenuColors.TextPrimary
            )
        },
        text = {
            Text(
                text = "Menu \"${item.title}\" akan dihapus dari daftar. Tindakan ini tidak dapat dibatalkan.",
                fontSize = 14.sp,
                color = MenuColors.TextSecondary
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MenuColors.BorderDefault)
            ) {
                Text(
                    text = "Batal",
                    color = MenuColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MenuColors.Destructive,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Hapus",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}
