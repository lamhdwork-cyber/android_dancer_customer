package com.kantek.dancer.booking.presentation.screen.conversation

import android.view.Gravity
import android.widget.TextView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.ui.Command
import com.kantek.dancer.booking.domain.model.ui.conversation.Message
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun ActionBarConversationView(
    bookingID: Int = 0, onCommand: (Command) -> Unit = {}
) {
    Surface(color = Color.White, shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {

            Image(painter = painterResource(R.drawable.baseline_arrow_back_24),
                colorFilter = ColorFilter.tint(Color.Black),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(indication = rememberRipple(bounded = true, radius = 24.dp),
                        interactionSource = remember { MutableInteractionSource() }) {
                        onCommand(
                            Command.ActionBarBack
                        )
                    }
                    .padding(16.dp))

            SpaceHorizontal(4.dp)

            Image(
                painter = painterResource(R.drawable.ic_avatar_consultant),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(42.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.top_bar_conversation), style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth()

                )

                Text(
                    text = "#$bookingID", color = Colors.Primary, style = TextStyle(
                        fontSize = 14.sp, platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}

@Composable
fun ChatInputBox(
    modifier: Modifier = Modifier,
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    onOpenCamera: () -> Unit,
    onOpenGallery: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (messageText.isNotBlank()) 0f else -90f, label = "rotationAnim"
    )

    Surface(
        modifier = modifier, shadowElevation = 4.dp, color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Camera icon
            IconButton(onClick = onOpenCamera) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Camera",
                    tint = Colors.Primary
                )
            }

            // Gallery icon
            IconButton(onClick = onOpenGallery) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Gallery",
                    tint = Colors.Primary
                )
            }

            // Input box
            TextField(
                value = messageText,
                onValueChange = onMessageChange,
                placeholder = { Text(stringResource(R.string.hint_message)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Colors.Primary,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                ),
                shape = RoundedCornerShape(20.dp),
                maxLines = 5,
                singleLine = false
            )

            // Button send
            IconButton(
                onClick = onSend, enabled = messageText.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = if (messageText.isNotBlank()) Colors.Primary else Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                        .rotate(rotation)
                )
            }
        }
    }
}

@Composable
fun NoDataConversationView() {
    val context = LocalContext.current
    val htmlString = remember { context.getString(R.string.no_data_conversation) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_message_empty),
            contentDescription = "",
            modifier = Modifier.size(42.dp)
        )
        SpaceVertical(10.dp)
        AndroidView(
            factory = {
                TextView(it).apply {
                    text = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    textSize = 14f
                    gravity = Gravity.CENTER
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun DynamicImageGrid(
    imageUrls: List<String>,
    isSending: Boolean = false,
    modifier: Modifier = Modifier
) {
    val displayImages = imageUrls.take(4)
    val extraCount = (imageUrls.size - 4).coerceAtLeast(0)

    when (displayImages.size) {
        1 -> {
            ChatImage(
                url = displayImages[0],
                isSending = isSending,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(4 / 3f)
            )
        }

        2 -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(180.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                displayImages.forEach { url ->
                    ChatImage(
                        url = url,
                        isSending = isSending,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }
            }
        }

        3 -> {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ChatImage(
                        url = displayImages[0],
                        isSending = isSending,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    ChatImage(
                        url = displayImages[1],
                        isSending = isSending,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }

                ChatImage(
                    url = displayImages[2],
                    isSending = isSending,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }
        }

        4 -> {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ChatImage(
                        url = displayImages[0],
                        isSending = isSending,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    ChatImage(
                        url = displayImages[1],
                        isSending = isSending,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ChatImage(
                        url = displayImages[2],
                        isSending = isSending,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        ChatImage(
                            url = displayImages[3],
                            isSending = isSending,
                            modifier = Modifier.matchParentSize()
                        )

                        if (extraCount > 0) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.6f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+$extraCount",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatImage(
    url: String,
    modifier: Modifier = Modifier,
    isSending: Boolean = false
) {
    val painter = rememberAsyncImagePainter(model = url)

    Box(modifier = modifier.clip(RoundedCornerShape(8.dp))) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .alpha(if (isSending) 0.6f else 1f)
        )

        if (painter.state is AsyncImagePainter.State.Loading || isSending) {
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xCC333333),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: Message,
    photoClick: (List<String>) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (message.isShowTime) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = message.createdAt,
                color = Colors.Gray146,
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
            )

            Spacer(Modifier.height(5.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 4.dp),
            horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            if (!message.isMe) {
                Box(
                    modifier = Modifier.defaultMinSize(28.dp)
                ) {
                    if (message.isShowAvatar) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_avatar_consultant),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(Modifier.width(4.dp))
            }

            Column(horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start) {
                if (!message.photos.isNullOrEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White
                    ) {
                        DynamicImageGrid(
                            imageUrls = message.photos,
                            isSending = message.isSending,
                            modifier = Modifier
                                .padding(6.dp)
                                .widthIn(max = 240.dp)
                                .clickable { photoClick(message.photos) }
                        )
                    }
                } else {
                    Surface(
                        color = if (message.isMe) Colors.Primary else Colors.Gray241,
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = message.message,
                                color = if (message.isMe) Color.White else Colors.Primary
                            )
                            if (message.isSending) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .padding(top = 2.dp)
                                        .align(Alignment.End),
                                    color = Color.White,
                                    strokeWidth = 1.dp
                                )
                            }
                        }
                    }
                }
            }

//        if (message.isMe && message.isShowAvatar) {
//            Spacer(Modifier.width(4.dp))
//            AvatarImage(message.avatar, 32.dp)
//        }
        }
    }
}

@Composable
fun ChatLazyColumn(
    items: List<Message>,
    state: LazyListState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onLoadMore: (() -> Unit)? = null,
    itemContent: @Composable (Message) -> Unit
) {

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.visibleItemsInfo }.map { it.lastOrNull()?.index }
            .distinctUntilChanged().collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= items.lastIndex && !isLoading) {
                    onLoadMore?.invoke()
                }
            }
    }

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(vertical = 12.dp),
        reverseLayout = true
    ) {
        items(
            items,
            key = { it.localId.ifEmpty { it.id } }) { item ->
            itemContent(item)
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Colors.Primary)
                }
            }
        }
    }

}
