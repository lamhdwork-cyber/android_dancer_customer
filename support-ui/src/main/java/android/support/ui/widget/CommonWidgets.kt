package android.support.ui.widget

import android.annotation.SuppressLint
import android.support.ui.R
import android.support.ui.extension.loadUrlData
import android.support.ui.theme.CoreColors
import android.support.ui.extension.rememberDebouncedClick
import android.view.Gravity
import android.webkit.WebView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun SpaceVertical(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpaceHorizontal(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                setTextColor(Color.Black.toArgb())
                textSize = 14f
            }
        }
    )
}

@Composable
fun HtmlStyledText(
    html: String,
    modifier: Modifier
) {
    val spanned = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    Text(
        text = buildAnnotatedString {
            append(spanned.toString())
        },
        modifier = modifier
    )
}

@Composable
fun SocialLoginButton(iconRes: Int, onClick: () -> Unit) {
    IconButton(
        onClick = rememberDebouncedClick(onClick = onClick),
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Social Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun RotatingLoadingIndicator() {
    val rotation by animateFloatAsState(
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .graphicsLayer { rotationZ = rotation },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 4.dp, color = CoreColors.Primary)
    }
}

@Composable
fun AppButton(
    @StringRes nameRes: Int,
    @ColorInt backgroundColor: Color = CoreColors.Primary,
    @ColorInt textColor: Color = Color.White,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
        .height(55.dp)
        .fillMaxWidth(),
    contentPadding: PaddingValues = PaddingValues(start = 50.dp, end = 50.dp),
    fontSize: Int = 16,
    iconStartRes: Int = 0,
    iconStartVector: ImageVector? = null,
    iconStartTint: Color = CoreColors.Primary,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = rememberDebouncedClick(onClick = onClick),
        enabled = isEnabled,
        contentPadding = contentPadding,
        modifier = modifier.indication(
            interactionSource = interactionSource,
            indication = ripple(
                bounded = true,
                color = textColor.copy(alpha = 0.45f)
            )
        ),
        interactionSource = interactionSource,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 14.dp,
            focusedElevation = 14.dp,
            hoveredElevation = 14.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (iconStartVector != null) {
            Icon(
                imageVector = iconStartVector,
                contentDescription = "Icon",
                tint = iconStartTint
            )
            SpaceHorizontal(20.dp)
        } else if (iconStartRes != 0) {
            Icon(
                painter = painterResource(id = iconStartRes),
                contentDescription = "Icon",
                tint = iconStartTint
            )
            SpaceHorizontal(20.dp)
        }

        Text(text = stringResource(id = nameRes), fontSize = fontSize.sp)
    }
}

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun NoDataView(@StringRes htmlRes: Int) {
    val context = LocalContext.current
    val htmlString = remember { context.getString(htmlRes) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = {
                TextView(it).apply {
                    text = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    textSize = 14f
                    gravity = Gravity.CENTER
                    setTextColor(CoreColors.Gray9CA3AF.toArgb())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppLazyColumn(
    items: List<T>,
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    keyItem: ((T) -> Any)? = null,
    isLoading: Boolean = false,
    onLoadMore: (() -> Unit)? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    backgroundColor: Color = CoreColors.Background,
    @StringRes emptyHtmlRes: Int? = null,
    isEmpty: Boolean = false,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    itemContent: @Composable (T, Int, Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    val showEmptyPlaceholder =
        emptyHtmlRes != null && isEmpty && items.isEmpty()

    LaunchedEffect(listState, items.size, isLoading, showEmptyPlaceholder) {
        snapshotFlowLastIndex(listState)
            .distinctUntilChanged().collect { lastVisibleItemIndex ->
                if (!showEmptyPlaceholder
                    && items.isNotEmpty()
                    && lastVisibleItemIndex != null
                    && lastVisibleItemIndex >= items.lastIndex
                    && !isLoading
                ) {
                    onLoadMore?.invoke()
                }
            }
    }

    val pullToRefreshState = rememberPullToRefreshState()

    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        val lazyColumnModifier =
            if (onRefresh != null || showEmptyPlaceholder) Modifier.fillMaxSize()
            else Modifier

        val lazyColumnContent: @Composable () -> Unit = {
            LazyColumn(
                modifier = lazyColumnModifier,
                state = listState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement
            ) {
                if (showEmptyPlaceholder) {
                    item(key = "app_lazy_column_empty") {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            NoDataView(htmlRes = emptyHtmlRes!!)
                        }
                    }
                } else {
                    itemsIndexed(
                        items,
                        key = { _, item ->
                            keyItem?.invoke(item) ?: item.hashCode()
                        }) { index, item ->
                        itemContent(item, index, index == items.lastIndex)
                    }
                }

                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = CoreColors.Primary)
                        }
                    }
                }
            }
        }

        if (onRefresh != null) {
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                state = pullToRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        color = CoreColors.Primary,
                        containerColor = Color.White,
                    )
                },
            ) {
                lazyColumnContent()
            }
        } else {
            lazyColumnContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppLazyVerticalGrid(
    items: List<T>,
    keyItem: ((T) -> Any)? = null,
    columns: GridCells = GridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    isIndicatorRefreshing: Boolean = isRefreshing,
    onLoadMore: (() -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CoreColors.BackgroundGrid,
    pullRefreshContentColor: Color = CoreColors.Primary,
    pullRefreshContainerColor: Color = Color.White,
    itemContent: @Composable (T, Int, Boolean) -> Unit
) {
    val gridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(gridState, items, isLoading, isRefreshing) {
        snapshotFlowGridLastIndex(gridState)
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (
                    items.isNotEmpty() &&
                    lastVisibleIndex >= 0 &&
                    lastVisibleIndex >= items.lastIndex &&
                    !isLoading &&
                    !isRefreshing
                ) {
                    onLoadMore?.invoke()
                }
            }
    }

    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        val gridContent: @Composable () -> Unit = {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = columns,
                state = gridState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {
                itemsIndexed(
                    items,
                    key = { _, item -> keyItem?.invoke(item) ?: item.hashCode() }
                ) { index, item ->
                    itemContent(item, index, index == items.lastIndex)
                }
            }
        }

        if (onRefresh != null) {
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = isIndicatorRefreshing,
                onRefresh = onRefresh,
                state = pullToRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        state = pullToRefreshState,
                        isRefreshing = isIndicatorRefreshing,
                        color = pullRefreshContentColor,
                        containerColor = pullRefreshContainerColor,
                    )
                },
            ) {
                gridContent()
            }
        } else {
            gridContent()
        }
    }
}

private fun snapshotFlowLastIndex(listState: androidx.compose.foundation.lazy.LazyListState) =
    androidx.compose.runtime.snapshotFlow { listState.layoutInfo.visibleItemsInfo }
        .map { it.lastOrNull()?.index }

private fun snapshotFlowGridLastIndex(gridState: androidx.compose.foundation.lazy.grid.LazyGridState) =
    androidx.compose.runtime.snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
        .map { it ?: -1 }



@Composable
fun OtpVerificationScreen(
    otpLength: Int = 6,
    otpTimeoutSeconds: Int = 60,
    onOtpComplete: (String) -> Unit,
    onResend: () -> Unit,
    isLoading: Boolean = false,
    isOtpInvalid: Boolean = false,
    errorMessage: String? = null
) {
    val otpValues = remember { List(otpLength) { mutableStateOf("") } }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    var timer by remember { mutableStateOf(otpTimeoutSeconds) }
    var isResendVisible by remember { mutableStateOf(false) }
    var shouldFocus by remember { mutableStateOf(false) }

    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer--
        } else {
            isResendVisible = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value.value,
                    onValueChange = {
                        if (it.length <= 1) {
                            value.value = it
                            if (it.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else if (it.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    },
                    isError = isOtpInvalid,
                    modifier = Modifier
                        .width(45.dp)
                        .focusRequester(focusRequesters[index])
                        .focusProperties {
                            if (index < otpLength - 1) {
                                next = focusRequesters[index + 1]
                            }
                            if (index > 0) {
                                previous = focusRequesters[index - 1]
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }
        }

        if (isOtpInvalid && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        val isOtpComplete = otpValues.all { it.value.isNotEmpty() }

        Button(
            onClick = rememberDebouncedClick {
                val otp = otpValues.joinToString("") { it.value }
                onOtpComplete(otp)
            },
            enabled = isOtpComplete && !isLoading,
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = CoreColors.Primary,
                        strokeWidth = 3.dp,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically)
                            .padding(end = 10.dp, top = 5.dp),
                    )
                }
                Text(
                    stringResource(R.string.all_contiue),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isResendVisible) {
            TextButton(onClick = rememberDebouncedClick {
                otpValues.forEach { it.value = "" }
                timer = otpTimeoutSeconds
                isResendVisible = false
                shouldFocus = true
                onResend()
            }) {
                Text(stringResource(R.string.auth_otp_resend))
            }
        } else {
            Text(stringResource(R.string.auth_otp_resend_in_remaining_s, timer))
        }
    }

    LaunchedEffect(Unit) {
        delay(300)
        focusRequesters.first().requestFocus()
    }

    LaunchedEffect(shouldFocus) {
        if (shouldFocus) {
            delay(50)
            focusRequesters.first().requestFocus()
            shouldFocus = false
        }
    }
}

@Composable
fun AppWebView(
    url: String,
    modifier: Modifier = Modifier,
    onLoading: (Boolean) -> Unit = {},
    onError: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val webView = remember { WebView(context) }

    DisposableEffect(Unit) {
        onDispose {
            webView.stopLoading()
            webView.destroy()
        }
    }

    AndroidView(
        factory = {
            webView.loadUrlData(
                owner = lifecycleOwner,
                url = url,
                onLoading = onLoading,
                onError = onError
            )
            webView
        },
        modifier = modifier
    )
}

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Dp = 40.dp,
    spacing: Dp = 4.dp
) {
    Row(modifier = modifier) {
        for (i in 1..stars) {
            val icon = when {
                i <= rating -> Icons.Filled.Star
                i - rating in 0.5f..0.99f -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Filled.StarBorder
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier
                    .size(starSize)
                    .padding(end = spacing)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val half = starSize.toPx() / 2
                            val newRating = if (offset.x < half) i - 0.5f else i.toFloat()
                            onRatingChanged(newRating)
                        }
                    }
            )
        }
    }
}

@Composable
fun AppNextButton(
    @StringRes nameRes: Int,
    @ColorInt backgroundColor: Color = CoreColors.Primary,
    @ColorInt textColor: Color = Color.White,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier.height(55.dp),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = rememberDebouncedClick(onClick = onClick),
        enabled = isEnabled,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 14.dp,
            focusedElevation = 14.dp,
            hoveredElevation = 14.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, contentColor = textColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = nameRes),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_button_right),
                contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 2.dp)
            )
        }
    }
}


