package android.support.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun AvatarImageDef(
    url: String?,
    size: Dp = 70.dp,
    onClick: (() -> Unit)? = null,
    placeholderRes: Int? = null,
    errorRes: Int? = null
) {
    val shimmerBrush = rememberShimmerBrush(1000)

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url ?: "")
            .apply {
                placeholderRes?.let { placeholder(it) }
                errorRes?.let { error(it) }
            }
            .crossfade(true)
            .allowHardware(false)
            .build()
    )

    val state = painter.state

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Avatar Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (state is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(shimmerBrush)
            )
        }
    }
}

@Composable
fun AppImageDef(
    url: String?,
    placeholderRes: Int? = null,
    errorRes: Int? = null,
    modifier: Modifier = Modifier,
    isShowLoading: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url ?: "")
            .apply {
                placeholderRes?.let { placeholder(it) }
                errorRes?.let { error(it) }
            }
            .crossfade(true)
            .allowHardware(false)
            .build()
    )

    val state = painter.state

    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = "Image",
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )
        if (state is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                if (isShowLoading)
                    CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoomablePager(
    imageUrls: List<String>,
    placeholderRes: Int? = null,
    errorRes: Int? = null,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState { imageUrls.size }
    var currentScale by remember { mutableStateOf(1f) }

    LaunchedEffect(pagerState.currentPage) {
        currentScale = 1f
    }

    HorizontalPager(
        state = pagerState,
        flingBehavior = PagerDefaults.flingBehavior(pagerState),
        modifier = modifier.fillMaxSize(),
        userScrollEnabled = currentScale <= 1f
    ) { page ->
        ZoomableAsyncImage(
            placeholderRes = placeholderRes,
            errorRes = errorRes,
            imageUrl = imageUrls[page],
            onScaleChanged = { newScale ->
                currentScale = newScale
            }
        )
    }
}

@Composable
fun ZoomableAsyncImage(
    imageUrl: String,
    placeholderRes: Int? = null,
    errorRes: Int? = null,
    modifier: Modifier = Modifier,
    onScaleChanged: ((Float) -> Unit)? = null
) {
    var scale by remember(imageUrl) { mutableStateOf(1f) }
    var offset by remember(imageUrl) { mutableStateOf(Offset.Zero) }

    val minScale = 1f
    val maxScale = 5f

    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .background(Color.Black)
            .pointerInput(Unit) {
                awaitEachGesture {
                    do {
                        val event = awaitPointerEvent()
                        val zoomChange = event.calculateZoom()
                        val panChange = event.calculatePan()

                        val newScale = (scale * zoomChange).coerceIn(minScale, maxScale)
                        onScaleChanged?.invoke(newScale)

                        if (newScale > 1f) {
                            val maxOffset = 1000f
                            offset = Offset(
                                x = (offset.x + panChange.x).coerceIn(-maxOffset, maxOffset),
                                y = (offset.y + panChange.y).coerceIn(-maxOffset, maxOffset)
                            )
                        } else {
                            offset = Offset.Zero
                        }

                        scale = newScale
                    } while (event.changes.any { it.pressed })

                    if (scale <= 1f) {
                        onScaleChanged?.invoke(1f)
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = if (scale > minScale) {
                            offset = Offset.Zero
                            1f
                        } else {
                            2.5f
                        }
                        onScaleChanged?.invoke(scale)
                    }
                )
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y,
                clip = true,
            )
    ) {
        AppImageDef(
            url = imageUrl,
            placeholderRes = placeholderRes,
            errorRes = errorRes,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}
