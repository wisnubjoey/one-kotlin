package com.bjoey.app.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.PointOfSale
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TableRestaurant
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjoey.app.data.StartedShiftSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val BrandRed = Color(0xFFC02C12)
private val BrandDark = Color(0xFF9E2209)
private val BrandLight = Color(0xFFE84B2E)
private val FnActiveBg = Color(0xFFFEE2E2)

private val Gray50 = Color(0xFFF9FAFB)
private val Gray100 = Color(0xFFF3F4F6)
private val Gray300 = Color(0xFFD1D5DB)
private val Gray400 = Color(0xFF9CA3AF)
private val Gray500 = Color(0xFF6B7280)
private val Gray600 = Color(0xFF4B5563)
private val Gray800 = Color(0xFF1F2937)
private val Gray900 = Color(0xFF111827)

private const val PIN_LENGTH = 6

private data class ShowcaseSlide(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val gradientColors: List<Color>
)

private val showcaseSlides = listOf(
    ShowcaseSlide(
        title = "Fine Dining & Service",
        subtitle = "Fast tableside ordering designed for peak restaurant hours",
        icon = Icons.Outlined.Restaurant,
        gradientColors = listOf(Color(0xFF3B1D16), Color(0xFF7C2D12), Color(0xFF9A3412))
    ),
    ShowcaseSlide(
        title = "Smart Table Management",
        subtitle = "Monitor active shifts, open tables, and guest orders effortlessly",
        icon = Icons.Outlined.TableRestaurant,
        gradientColors = listOf(Color(0xFF1F2937), Color(0xFF374151), Color(0xFF7F1D1D))
    ),
    ShowcaseSlide(
        title = "Offline-First POS Engine",
        subtitle = "Instant local SQLite shift verification with zero latency",
        icon = Icons.Outlined.PointOfSale,
        gradientColors = listOf(Color(0xFF431407), Color(0xFF9E2209), Color(0xFFC02C12))
    )
)

@Composable
fun PinLoginScreen(
    baseUrl: String,
    onSaveBaseUrl: (String) -> Unit,
    onValidatePin: suspend (String) -> StartedShiftSession?,
    onLoginSuccess: (StartedShiftSession) -> Unit
) {
    var pin by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }
    var isErrorStatus by remember { mutableStateOf(false) }
    var isVerifying by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    fun triggerPinValidation(enteredPin: String) {
        if (isVerifying) return
        isVerifying = true
        isErrorStatus = false
        statusMessage = "Verifying…"

        coroutineScope.launch {
            val startedSession = onValidatePin(enteredPin)
            if (startedSession != null) {
                isVerifying = false
                pin = ""
                statusMessage = ""
                onLoginSuccess(startedSession)
            } else {
                isErrorStatus = true
                statusMessage = "Incorrect PIN, try again"
                val keyframes = listOf(-18f, 18f, -14f, 14f, -8f, 8f, 0f)
                for (target in keyframes) {
                    shakeOffset.animateTo(
                        targetValue = target,
                        animationSpec = tween(durationMillis = 55)
                    )
                }
                pin = ""
                isVerifying = false
            }
        }
    }

    fun onDigitPressed(digit: String) {
        if (isVerifying || pin.length >= PIN_LENGTH) return
        val updatedPin = pin + digit
        pin = updatedPin
        statusMessage = ""
        isErrorStatus = false

        if (updatedPin.length == PIN_LENGTH) {
            triggerPinValidation(updatedPin)
        }
    }

    fun onBackspacePressed() {
        if (isVerifying || pin.isEmpty()) return
        pin = pin.dropLast(1)
        statusMessage = ""
        isErrorStatus = false
    }

    fun onClearPressed() {
        if (isVerifying) return
        pin = ""
        isErrorStatus = false
        statusMessage = "Cleared"
    }

    if (showSettingsDialog) {
        ServerUrlSettingsDialog(
            currentUrl = baseUrl,
            onDismiss = { showSettingsDialog = false },
            onSave = { updatedUrl ->
                onSaveBaseUrl(updatedUrl)
                statusMessage = "Server URL disimpan"
                isErrorStatus = false
                showSettingsDialog = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LeftBrandingSection(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        RightKeypadSection(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            pinLength = pin.length,
            statusMessage = statusMessage,
            isErrorStatus = isErrorStatus,
            shakeOffsetPx = shakeOffset.value,
            onOpenSettingsDialog = { showSettingsDialog = true },
            onDigitClick = ::onDigitPressed,
            onClearClick = ::onClearPressed,
            onBackspaceClick = ::onBackspacePressed
        )
    }
}

@Composable
fun ServerUrlSettingsDialog(
    currentUrl: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var urlInput by remember(currentUrl) { mutableStateOf(currentUrl) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Pengaturan Server URL",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Gray900
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Masukkan URL Cloudflare Tunnel server Next.js:",
                    fontSize = 13.sp,
                    color = Gray600
                )
                OutlinedTextField(
                    value = urlInput,
                    onValueChange = { urlInput = it },
                    singleLine = true,
                    label = { Text("Base URL Server") },
                    placeholder = { Text("https://example.trycloudflare.com") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandRed,
                        focusedLabelColor = BrandRed,
                        cursorColor = BrandRed
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(urlInput) },
                colors = ButtonDefaults.buttonColors(containerColor = BrandRed)
            ) {
                Text("Simpan", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = Gray600)
            }
        }
    )
}

@Composable
private fun LeftBrandingSection(modifier: Modifier = Modifier) {
    var activeSlide by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3500L)
            activeSlide = (activeSlide + 1) % showcaseSlides.size
        }
    }

    Box(
        modifier = modifier
            .background(BrandRed)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(BrandLight.copy(alpha = 0.35f), Color.Transparent),
                    center = Offset(x = 0f, y = 0f),
                    radius = size.minDimension * 0.65f
                ),
                center = Offset(x = 0f, y = 0f),
                radius = size.minDimension * 0.65f
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(BrandDark.copy(alpha = 0.45f), Color.Transparent),
                    center = Offset(x = size.width, y = size.height),
                    radius = size.minDimension * 0.75f
                ),
                center = Offset(x = size.width, y = size.height),
                radius = size.minDimension * 0.75f
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 44.dp, vertical = 36.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    TavolaBrandIcon(
                        color = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    text = "Tavola",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.4.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column {
                    Text(
                        text = "Welcome to Your\nRestaurant",
                        color = Color.White,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 44.sp,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "A smarter dining experience for staff and guests. Take orders, manage tables, and deliver delight — all from one place.",
                        color = Color.White.copy(alpha = 0.82f),
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 215.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 10f)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Crossfade(
                            targetState = activeSlide,
                            animationSpec = tween(durationMillis = 500),
                            label = "ShowcaseCarousel"
                        ) { slideIndex ->
                            val slide = showcaseSlides[slideIndex]
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.linearGradient(slide.gradientColors))
                                    .padding(24.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color.White.copy(alpha = 0.14f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = slide.icon,
                                            contentDescription = slide.title,
                                            tint = Color.White,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = slide.title,
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = slide.subtitle,
                                            color = Color.White.copy(alpha = 0.80f),
                                            fontSize = 13.sp,
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            showcaseSlides.indices.forEach { index ->
                                val isCurrent = index == activeSlide
                                val dotWidth by animateDpAsState(
                                    targetValue = if (isCurrent) 20.dp else 8.dp,
                                    animationSpec = tween(durationMillis = 300),
                                    label = "CarouselDotWidth"
                                )
                                Box(
                                    modifier = Modifier
                                        .width(dotWidth)
                                        .height(8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isCurrent) Color.White
                                            else Color.White.copy(alpha = 0.40f)
                                        )
                                )
                            }
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeaturePill(label = "Tableside ordering")
                FeaturePill(label = "Real-time sync")
            }
        }
    }
}

@Composable
private fun FeaturePill(label: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White.copy(alpha = 0.12f))
            .padding(horizontal = 14.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.size(15.dp)
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun RightKeypadSection(
    modifier: Modifier = Modifier,
    pinLength: Int,
    statusMessage: String,
    isErrorStatus: Boolean,
    shakeOffsetPx: Float,
    onOpenSettingsDialog: () -> Unit,
    onDigitClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackspaceClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onOpenSettingsDialog,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Pengaturan Server URL",
                    tint = Gray600,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrandRed.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                TavolaBrandIcon(
                    color = BrandRed,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Tavola",
                color = BrandRed,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Waiter Login",
                color = Gray900,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Enter your PIN to continue",
                color = Gray500,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.offset { IntOffset(shakeOffsetPx.roundToInt(), 0) },
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(PIN_LENGTH) { index ->
                    val isFilled = index < pinLength
                    val scale by animateFloatAsState(
                        targetValue = if (isFilled) 1.12f else 1.0f,
                        animationSpec = tween(durationMillis = 180),
                        label = "PinDotScale"
                    )
                    val dotBgColor by animateColorAsState(
                        targetValue = if (isFilled) BrandRed else Color.Transparent,
                        animationSpec = tween(durationMillis = 180),
                        label = "PinDotBg"
                    )
                    val dotBorderColor by animateColorAsState(
                        targetValue = if (isFilled) BrandRed else Gray300,
                        animationSpec = tween(durationMillis = 180),
                        label = "PinDotBorder"
                    )

                    Box(
                        modifier = Modifier
                            .scale(scale)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(dotBgColor)
                            .border(width = 2.dp, color = dotBorderColor, shape = CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.height(22.dp),
                contentAlignment = Alignment.Center
            ) {
                if (statusMessage.isNotEmpty()) {
                    Text(
                        text = statusMessage,
                        color = if (isErrorStatus) BrandRed else Gray400,
                        fontSize = 13.sp,
                        fontWeight = if (isErrorStatus) FontWeight.Medium else FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val rows = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9")
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                rows.forEach { rowKeys ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(28.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowKeys.forEach { digit ->
                            KeypadCircleButton(
                                isFunctionKey = false,
                                onClick = { onDigitClick(digit) }
                            ) { contentColor ->
                                Text(
                                    text = digit,
                                    color = contentColor,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KeypadCircleButton(
                        isFunctionKey = true,
                        onClick = onClearClick
                    ) { contentColor ->
                        Text(
                            text = "*",
                            color = contentColor,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    KeypadCircleButton(
                        isFunctionKey = false,
                        onClick = { onDigitClick("0") }
                    ) { contentColor ->
                        Text(
                            text = "0",
                            color = contentColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    KeypadCircleButton(
                        isFunctionKey = true,
                        onClick = onBackspaceClick
                    ) { contentColor ->
                        Icon(
                            imageVector = Icons.Outlined.Backspace,
                            contentDescription = "Backspace",
                            tint = contentColor,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }

        Text(
            text = "© 2026 Tavola POS. All rights reserved. For staff use only.",
            color = Gray300,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun KeypadCircleButton(
    isFunctionKey: Boolean,
    onClick: () -> Unit,
    content: @Composable (contentColor: Color) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1.0f,
        animationSpec = tween(durationMillis = 80),
        label = "KeyBtnScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = when {
            isPressed && isFunctionKey -> FnActiveBg
            isPressed && !isFunctionKey -> BrandRed
            else -> Gray50
        },
        animationSpec = tween(durationMillis = 120),
        label = "KeyBtnBg"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isPressed && isFunctionKey -> BrandRed
            isPressed && !isFunctionKey -> Color.White
            isFunctionKey -> Gray600
            else -> Gray800
        },
        animationSpec = tween(durationMillis = 120),
        label = "KeyBtnContent"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .size(76.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isPressed) Color.Transparent else Gray100,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content(contentColor)
    }
}

@Composable
private fun TavolaBrandIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.09f
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)

        drawLine(
            color = color,
            start = Offset(w * 0.125f, h * 0.458f),
            end = Offset(w * 0.875f, h * 0.458f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.208f, h * 0.208f),
            size = Size(w * 0.584f, h * 0.25f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f),
            style = stroke
        )

        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.25f, h * 0.458f),
            size = Size(w * 0.50f, h * 0.417f),
            cornerRadius = CornerRadius(w * 0.04f, w * 0.04f),
            style = stroke
        )

        drawLine(
            color = color,
            start = Offset(w * 0.375f, h * 0.125f),
            end = Offset(w * 0.375f, h * 0.208f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(w * 0.625f, h * 0.125f),
            end = Offset(w * 0.625f, h * 0.208f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
