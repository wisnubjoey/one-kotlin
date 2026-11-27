package com.bjoey.app.ui.settings.table

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableFormScreen(
    existingTable: TableItem?,
    onBackClick: () -> Unit,
    onSaveClick: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEditMode = existingTable != null
    var name by rememberSaveable(existingTable?.id) {
        mutableStateOf(existingTable?.name.orEmpty())
    }
    val isSaveEnabled = isValidTableName(name)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TableColors.ScreenBackground,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = if (isEditMode) "Edit Meja" else "Tambah Meja",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TableColors.TextPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali",
                                tint = TableColors.TextPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = TableColors.CardSurface,
                        titleContentColor = TableColors.TextPrimary
                    )
                )
                HorizontalDivider(thickness = 1.dp, color = TableColors.BorderDefault)
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
                color = TableColors.CardSurface,
                border = BorderStroke(1.dp, TableColors.BorderDefault)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Nama Meja") },
                        placeholder = {
                            Text(
                                text = "Contoh: Meja 01",
                                color = TableColors.TextSecondary
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TableColors.BorderFocused,
                            unfocusedBorderColor = TableColors.BorderDefault,
                            focusedLabelColor = TableColors.BorderFocused,
                            unfocusedLabelColor = TableColors.TextSecondary,
                            focusedContainerColor = TableColors.CardSurface,
                            unfocusedContainerColor = TableColors.CardSurface,
                            focusedTextColor = TableColors.TextPrimary,
                            unfocusedTextColor = TableColors.TextPrimary,
                            cursorColor = TableColors.BorderFocused
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

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
                            border = BorderStroke(1.dp, TableColors.BorderDefault)
                        ) {
                            Text(
                                text = "Batal",
                                fontWeight = FontWeight.SemiBold,
                                color = TableColors.TextPrimary
                            )
                        }

                        Button(
                            onClick = { onSaveClick(normalizeTableName(name)) },
                            enabled = isSaveEnabled,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TableColors.PrimaryAccent,
                                contentColor = Color.White,
                                disabledContainerColor = Color(0xFFD1D5DB),
                                disabledContentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Simpan Meja",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
