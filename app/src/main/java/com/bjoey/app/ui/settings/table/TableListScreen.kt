package com.bjoey.app.ui.settings.table

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableListScreen(
    tables: List<TableItem>,
    totalCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteConfirm: (TableItem) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    if (onBackClick != null) {
        BackHandler(onBack = onBackClick)
    }

    var pendingDeleteItem by remember { mutableStateOf<TableItem?>(null) }

    pendingDeleteItem?.let { item ->
        DeleteTableConfirmationDialog(
            item = item,
            onDismiss = { pendingDeleteItem = null },
            onConfirm = {
                onDeleteConfirm(item)
                pendingDeleteItem = null
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TableColors.ScreenBackground)
    ) {
        TableListHeader(
            totalCount = totalCount,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onAddClick = onAddClick,
            onBackClick = onBackClick
        )

        HorizontalDivider(thickness = 1.dp, color = TableColors.BorderDefault)

        if (tables.isEmpty()) {
            val isFiltered = searchQuery.isNotBlank()
            TableEmptyState(
                isFiltered = isFiltered,
                onResetSearch = { onSearchQueryChange("") },
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
                    items = tables,
                    key = { it.id }
                ) { item ->
                    TableCard(
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
private fun TableListHeader(
    totalCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onBackClick: (() -> Unit)?
) {
    Surface(
        color = TableColors.CardSurface,
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
                            tint = TableColors.TextPrimary
                        )
                    }
                }

                Text(
                    text = "Table",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TableColors.TextPrimary
                )

                Surface(
                    shape = CircleShape,
                    color = TableColors.BadgeBackground,
                    border = BorderStroke(1.dp, TableColors.BorderDefault)
                ) {
                    Text(
                        text = "$totalCount Meja",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TableColors.TextPrimary,
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
                        TableSearchField(
                            query = searchQuery,
                            onQueryChange = onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        AddTableButton(
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
                        TableSearchField(
                            query = searchQuery,
                            onQueryChange = onSearchQueryChange,
                            modifier = Modifier.weight(1f)
                        )
                        AddTableButton(
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
private fun TableSearchField(
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
                text = "Cari nama meja...",
                color = TableColors.TextSecondary,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Cari",
                tint = TableColors.TextSecondary
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hapus pencarian",
                        tint = TableColors.TextSecondary
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TableColors.BorderFocused,
            unfocusedBorderColor = TableColors.BorderDefault,
            focusedContainerColor = TableColors.CardSurface,
            unfocusedContainerColor = TableColors.CardSurface,
            focusedTextColor = TableColors.TextPrimary,
            unfocusedTextColor = TableColors.TextPrimary,
            cursorColor = TableColors.BorderFocused
        )
    )
}

@Composable
private fun AddTableButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TableColors.PrimaryAccent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Text(
            text = "+ Tambah Meja",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TableCard(
    item: TableItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(12.dp)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = cardShape,
        color = TableColors.CardSurface,
        border = BorderStroke(1.dp, TableColors.BorderDefault)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = TableColors.PlaceholderBackground,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.TableRestaurant,
                            contentDescription = null,
                            tint = TableColors.TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TableColors.TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider(thickness = 1.dp, color = TableColors.BorderDefault)

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
                        tint = TableColors.TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Edit",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TableColors.TextPrimary
                    )
                }

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = TableColors.BorderDefault
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
                        tint = TableColors.Destructive,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Hapus",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TableColors.Destructive
                    )
                }
            }
        }
    }
}

@Composable
private fun TableEmptyState(
    isFiltered: Boolean,
    onResetSearch: () -> Unit,
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
                color = TableColors.CardSurface,
                border = BorderStroke(1.dp, TableColors.BorderDefault),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isFiltered) Icons.Outlined.Search else Icons.Outlined.TableRestaurant,
                        contentDescription = null,
                        tint = TableColors.TextSecondary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isFiltered) "Meja tidak ditemukan" else "Belum ada meja",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TableColors.TextPrimary
            )

            Text(
                text = if (isFiltered) {
                    "Coba kata kunci lain untuk melihat daftar meja."
                } else {
                    "Tambahkan meja pertama untuk mulai mengelola daftar meja POS."
                },
                fontSize = 14.sp,
                color = TableColors.TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (isFiltered) {
                OutlinedButton(
                    onClick = onResetSearch,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, TableColors.BorderDefault)
                ) {
                    Text(
                        text = "Reset Pencarian",
                        color = TableColors.TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                AddTableButton(onClick = onAddClick)
            }
        }
    }
}

@Composable
private fun DeleteTableConfirmationDialog(
    item: TableItem,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = TableColors.CardSurface,
        shape = RoundedCornerShape(12.dp),
        title = {
            Text(
                text = "Hapus Meja?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TableColors.TextPrimary
            )
        },
        text = {
            Text(
                text = "Meja \"${item.name}\" akan dihapus dari daftar. Tindakan ini tidak dapat dibatalkan.",
                fontSize = 14.sp,
                color = TableColors.TextSecondary
            )
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, TableColors.BorderDefault)
            ) {
                Text(
                    text = "Batal",
                    color = TableColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TableColors.Destructive,
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
