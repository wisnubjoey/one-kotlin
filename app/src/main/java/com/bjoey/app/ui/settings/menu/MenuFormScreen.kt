package com.bjoey.app.ui.settings.menu

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuFormScreen(
    existingItem: MenuItem?,
    categories: List<MenuCategory>,
    onAddCategory: (String, (MenuCategory) -> Unit) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: (title: String, price: Long, categoryId: String, imageUri: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEditMode = existingItem != null
    val context = LocalContext.current

    var imageUri by rememberSaveable(existingItem?.id) {
        mutableStateOf(existingItem?.imageUri)
    }
    var title by rememberSaveable(existingItem?.id) {
        mutableStateOf(existingItem?.title.orEmpty())
    }
    var priceDigits by rememberSaveable(existingItem?.id) {
        mutableStateOf(existingItem?.price?.takeIf { it > 0L }?.toString().orEmpty())
    }
    var selectedCategoryId by rememberSaveable(existingItem?.id) {
        mutableStateOf(existingItem?.categoryId.orEmpty())
    }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }

    // Default to first available category when adding a new item
    LaunchedEffect(categories) {
        if (selectedCategoryId.isBlank() && categories.isNotEmpty()) {
            selectedCategoryId = categories.first().id
        }
    }

    val selectedCategoryName = categories.firstOrNull { it.id == selectedCategoryId }?.name.orEmpty()
    val parsedPrice = priceDigits.toLongOrNull() ?: 0L
    val isSaveEnabled = title.isNotBlank() && parsedPrice > 0L && selectedCategoryId.isNotBlank()

    if (showAddCategoryDialog) {
        AddCategoryDialog(
            onDismiss = { showAddCategoryDialog = false },
            onConfirm = { newCategoryName ->
                onAddCategory(newCategoryName) { created ->
                    selectedCategoryId = created.id
                }
                showAddCategoryDialog = false
            }
        )
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Non-persistable URI providers still remain readable for the current session.
            }
            imageUri = uri.toString()
        }
    }

    val launchImagePicker = {
        photoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MenuColors.ScreenBackground,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = if (isEditMode) "Edit Menu" else "Tambah Menu",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MenuColors.TextPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali",
                                tint = MenuColors.TextPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MenuColors.CardSurface,
                        titleContentColor = MenuColors.TextPrimary
                    )
                )
                HorizontalDivider(thickness = 1.dp, color = MenuColors.BorderDefault)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                modifier = Modifier
                    .widthIn(max = 520.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MenuColors.CardSurface,
                border = BorderStroke(1.dp, MenuColors.BorderDefault)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Field 1: Foto Menu (1:1 Image Picker)
                    MenuPhotoPickerField(
                        imageUri = imageUri,
                        onPickImageClick = launchImagePicker,
                        onRemoveImageClick = { imageUri = null }
                    )

                    // Field 2: Nama Menu (Title)
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Nama Menu") },
                        placeholder = {
                            Text(
                                text = "Contoh: Iced Matcha Latte",
                                color = MenuColors.TextSecondary
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        shape = RoundedCornerShape(10.dp),
                        colors = menuInputColors()
                    )

                    // Field 3: Kategori Menu
                    ExposedDropdownMenuBox(
                        expanded = categoryDropdownExpanded,
                        onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedCategoryName,
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            label = { Text("Kategori") },
                            placeholder = {
                                Text(
                                    text = "Pilih kategori",
                                    color = MenuColors.TextSecondary
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = menuInputColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = categoryDropdownExpanded,
                            onDismissRequest = { categoryDropdownExpanded = false },
                            modifier = Modifier.background(MenuColors.CardSurface)
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = category.name,
                                            fontWeight = if (category.id == selectedCategoryId) {
                                                FontWeight.Bold
                                            } else {
                                                FontWeight.Normal
                                            },
                                            color = MenuColors.TextPrimary
                                        )
                                    },
                                    onClick = {
                                        selectedCategoryId = category.id
                                        categoryDropdownExpanded = false
                                    }
                                )
                            }

                            HorizontalDivider(thickness = 1.dp, color = MenuColors.BorderDefault)

                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = MenuColors.PrimaryAccent
                                    )
                                },
                                text = {
                                    Text(
                                        text = "Tambah Kategori Baru",
                                        fontWeight = FontWeight.SemiBold,
                                        color = MenuColors.PrimaryAccent
                                    )
                                },
                                onClick = {
                                    categoryDropdownExpanded = false
                                    showAddCategoryDialog = true
                                }
                            )
                        }
                    }

                    // Field 4: Harga (Price)
                    val thousandTransformation = remember { ThousandSeparatorTransformation() }
                    OutlinedTextField(
                        value = priceDigits,
                        onValueChange = { rawInput ->
                            val sanitized = rawInput
                                .filter { it.isDigit() }
                                .trimStart('0')
                                .take(12)
                            priceDigits = sanitized
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Harga") },
                        placeholder = {
                            Text(
                                text = "0",
                                color = MenuColors.TextSecondary
                            )
                        },
                        prefix = {
                            Text(
                                text = "Rp ",
                                fontWeight = FontWeight.Bold,
                                color = MenuColors.TextPrimary
                            )
                        },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFeatureSettings = "tnum",
                            fontSize = 16.sp
                        ),
                        visualTransformation = thousandTransformation,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = menuInputColors()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Footer Actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, MenuColors.BorderDefault)
                        ) {
                            Text(
                                text = "Batal",
                                fontWeight = FontWeight.SemiBold,
                                color = MenuColors.TextPrimary
                            )
                        }

                        Button(
                            onClick = {
                                onSaveClick(
                                    title.trim(),
                                    parsedPrice,
                                    selectedCategoryId,
                                    imageUri
                                )
                            },
                            enabled = isSaveEnabled,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MenuColors.PrimaryAccent,
                                contentColor = Color.White,
                                disabledContainerColor = Color(0xFFD1D5DB),
                                disabledContentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Simpan Menu",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuPhotoPickerField(
    imageUri: String?,
    onPickImageClick: () -> Unit,
    onRemoveImageClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Foto Menu",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MenuColors.TextPrimary,
            modifier = Modifier.align(Alignment.Start)
        )

        val cornerRadiusDp = 12.dp
        val borderColor = if (imageUri != null) MenuColors.BorderDefault else Color(0xFFD1D5DB)

        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(cornerRadiusDp))
                .background(MenuColors.ScreenBackground)
                .drawBehind {
                    val strokeWidth = 1.5.dp.toPx()
                    val dashEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                    drawRoundRect(
                        color = borderColor,
                        style = Stroke(width = strokeWidth, pathEffect = dashEffect),
                        cornerRadius = CornerRadius(cornerRadiusDp.toPx(), cornerRadiusDp.toPx())
                    )
                }
                .clickable(onClick = onPickImageClick),
            contentAlignment = Alignment.Center
        ) {
            if (!imageUri.isNullOrBlank()) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Preview Foto Menu",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddPhotoAlternate,
                        contentDescription = "Pilih Foto",
                        tint = MenuColors.TextSecondary,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Pilih Foto (1:1)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MenuColors.TextSecondary
                    )
                }
            }
        }

        if (!imageUri.isNullOrBlank()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onPickImageClick) {
                    Text(
                        text = "Ganti",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MenuColors.TextPrimary
                    )
                }
                TextButton(onClick = onRemoveImageClick) {
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
private fun menuInputColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MenuColors.BorderFocused,
    unfocusedBorderColor = MenuColors.BorderDefault,
    focusedLabelColor = MenuColors.BorderFocused,
    unfocusedLabelColor = MenuColors.TextSecondary,
    focusedContainerColor = MenuColors.CardSurface,
    unfocusedContainerColor = MenuColors.CardSurface,
    focusedTextColor = MenuColors.TextPrimary,
    unfocusedTextColor = MenuColors.TextPrimary,
    cursorColor = MenuColors.BorderFocused
)
