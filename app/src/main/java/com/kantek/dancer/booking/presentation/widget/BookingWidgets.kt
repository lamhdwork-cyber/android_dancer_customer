package com.kantek.dancer.booking.presentation.widget

import android.support.ui.widget.AppButton
import android.support.ui.widget.AppInputText
import android.support.ui.widget.SpaceVertical
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.booking.BookingActionsBar
import com.kantek.dancer.booking.domain.model.booking.IBooking
import com.kantek.dancer.booking.presentation.theme.Colors

@Composable
fun BookingSuccessDialog(
    title: String,
    message: String,
    textConfirm: String = stringResource(R.string.all_view_detail),
    textDismiss: String = stringResource(R.string.all_close),
    onConfirm: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        containerColor = Color.White,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                contentDescription = null,
                tint = Colors.Green103
            )
        },
        title = {
            Text(
                title,
                textAlign = TextAlign.Center
            )
        },
        text = { Text(message, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                onDismiss?.let {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.Blue241,
                            contentColor = Colors.Primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 1.dp,
                            pressedElevation = 10.dp,
                            focusedElevation = 10.dp,
                            hoveredElevation = 10.dp
                        )
                    ) {
                        Text(
                            textDismiss,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                onConfirm?.let {
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.Primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 10.dp,
                            focusedElevation = 10.dp,
                            hoveredElevation = 10.dp
                        )
                    ) {
                        Text(
                            textConfirm,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        })
}


@Composable
fun CancellationReasonDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var reason by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text(stringResource(R.string.msg_cancel_reason), color = Color.Black) },
        text = {
            Column {
                AppInputText(
                    value = reason,
                    lightBackground = true,
                    singleLine = false,
                    maxLines = 6,
                    modifier = Modifier.height(150.dp),
                    placeHolderRes = R.string.all_cancellation_reason,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { reason = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(reason) },
                modifier = Modifier.widthIn(min = 80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Primary, contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 10.dp,
                    focusedElevation = 10.dp,
                    hoveredElevation = 10.dp
                )
            ) {
                Text(stringResource(R.string.all_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.all_cancel), color = Colors.Gray146)
            }
        })
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookingItemView(
    it: IBooking,
    onItemClick: () -> Unit,
    onRequestClick: () -> Unit,
    onCancelClick: () -> Unit,
    onAwaitClick: () -> Unit = {},
    onReadyClick: () -> Unit = {},
) {
    Card(
        onClick = onItemClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Dark190C19),
        border = androidx.compose.foundation.BorderStroke(1.dp, Colors.White1AFFFFFF)
    ) {
        Column(
            modifier = Modifier
                .background(Colors.Dark190C19)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.bookingCodeDisplay,
                        color = Colors.Pink66F425F4,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (it.isNow) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(it.colorStatus)
                            )
                        }
                        Text(
                            text = it.timeDisplay,
                            color = if (it.isNow) Colors.Primary else Colors.Gray6B7280,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                val namePhoneAnnotated = remember(it.customerNameDisplay, it.customerPhoneDisplay) {
                    val phone = it.customerPhoneDisplay
                    buildAnnotatedString {
                        pushStyle(
                            SpanStyle(
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        append(it.customerNameDisplay)
                        pop()
                        if (phone.isNotBlank()) {
                            pushStyle(
                                SpanStyle(
                                    color = Colors.Gray6B7280,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            append(" · $phone")
                            pop()
                        }
                    }
                }
                Text(
                    text = namePhoneAnnotated,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    FlowRow(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Colors.Pink26F425F4),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = it.roomImageURL,
                                    contentDescription = null,
                                    placeholder = rememberVectorPainter(it.roomImagePlaceholder),
                                    error = rememberVectorPainter(it.roomImagePlaceholder),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(3.dp),
                                    contentScale = ContentScale.Fit,
                                    colorFilter = ColorFilter.tint(Colors.Blue148)
                                )
                            }
                            Text(
                                text = it.roomNameDisplay,
                                color = Colors.Gray9CA3AF,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
//                        Row(
//                            horizontalArrangement = Arrangement.spacedBy(4.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Groups,
//                                contentDescription = null,
//                                tint = Colors.Pink66F425F4,
//                                modifier = Modifier.size(14.dp)
//                            )
//                            Text(
//                                text = it.numberOfGuestsDisplay,
//                                color = Colors.Gray9CA3AF,
//                                fontSize = 11.sp
//                            )
//                        }
//                        Row(
//                            horizontalArrangement = Arrangement.spacedBy(4.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.MusicNote,
//                                contentDescription = null,
//                                tint = Colors.Pink66F425F4,
//                                modifier = Modifier.size(14.dp)
//                            )
//                            Text(
//                                text = it.numberOfSongsDisplay,
//                                color = Colors.Gray9CA3AF,
//                                fontSize = 11.sp
//                            )
//                        }
                    }

//                    Text(
//                        text = it.totalAmountDisplay,
//                        color = Colors.Green103,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 14.sp
//                    )
                }
            }

            SpaceVertical(14.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White1AFFFFFF, RoundedCornerShape(50.dp))
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy((-8).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val totalAvatar =
                        maxOf(it.dancerAvatarsDisplay.size, it.dancersDisplayList.size)
                    repeat(totalAvatar) { index ->
                        val avatarUrl = it.dancerAvatarsDisplay.getOrNull(index)
                        val dancerName = it.dancersDisplayList.getOrNull(index).orEmpty()

                        if (!avatarUrl.isNullOrBlank()) {
                            AppImage(
                                url = avatarUrl,
                                isShowLoading = false,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Colors.Dark0D070D, CircleShape)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Colors.Dark2A1323)
                                    .border(2.dp, Colors.Dark0D070D, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dancerName.take(1).uppercase(),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    Text(
                        text = stringResource(R.string.booking_selected_talent),
                        color = Colors.Gray6B7280,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = it.dancersDisplayOrFallback,
                        color = Color.White,
                        fontSize = 11.sp,
                        lineHeight = 13.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            when (it.bookingActionsBar) {
                BookingActionsBar.USER_STANDARD -> {
                    if (it.hasShowButtonCancel) {
                        OutlinedButton(
                            onClick = onCancelClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Colors.RedEF4444.copy(alpha = 0.85f)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                Colors.Red33EF4444
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.all_cancel),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                    if (it.hasCancel) {
                        if (it.hasShowButtonCancel) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        AppButton(
                            nameRes = R.string.all_request_again,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(28.dp)),
                            onClick = onRequestClick
                        )
                    }
                }

                BookingActionsBar.CLUB_MANAGER_READY -> {
                    Button(
                        onClick = onReadyClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.Primary,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.booking_action_ready),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.2.sp
                        )
                    }
                }

                BookingActionsBar.CLUB_MANAGER_READY_WAIT -> {
                    val primaryLabel = when (it.bookingActionsBar) {
                        BookingActionsBar.CLUB_MANAGER_READY_WAIT ->
                            R.string.booking_action_ready

                        else ->
                            R.string.booking_action_await
                    }
                    val onPrimary = when (it.bookingActionsBar) {
                        BookingActionsBar.CLUB_MANAGER_READY_WAIT -> onReadyClick
                        else -> onAwaitClick
                    }
                    val secondaryLabel = when (it.bookingActionsBar) {
                        BookingActionsBar.CLUB_MANAGER_READY_WAIT ->
                            R.string.booking_action_await

                        else ->
                            R.string.all_cancel
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onPrimary,
                            modifier = Modifier
                                .weight(2f)
                                .height(40.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Colors.Primary,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Text(
                                text = stringResource(primaryLabel),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 1.2.sp
                            )
                        }
                        OutlinedButton(
                            onClick = onAwaitClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Colors.Gray9CA3AF
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                Colors.White1AFFFFFF
                            )
                        ) {
                            Text(
                                text = stringResource(secondaryLabel),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 1.2.sp
                            )
                        }
                    }
                }

                BookingActionsBar.NONE -> Unit
            }
        }
    }
}



@Composable
fun DetailBookingClubBlock(clubName: String, address: String) {
    Column {
        Text(
            text = clubName,
            color = Colors.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = Colors.Primary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = address,
                color = Colors.Gray9CA3AF,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}



