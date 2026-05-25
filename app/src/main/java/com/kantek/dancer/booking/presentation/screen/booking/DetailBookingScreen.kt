package com.kantek.dancer.booking.presentation.screen.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppNotifications
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.data.factory.BookingFactory
import com.kantek.dancer.booking.presentation.model.support.Scopes
import com.kantek.dancer.booking.domain.model.booking.BookingActionsBar
import com.kantek.dancer.booking.domain.model.booking.IBookingDetail
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.AppConfirmDialog
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.DetailBookingClubBlock
import com.kantek.dancer.booking.presentation.widget.AvatarImage
import com.kantek.dancer.booking.presentation.widget.CancellationReasonDialog
import com.kantek.dancer.booking.presentation.widget.NoDataView
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

private enum class DetailManagerConfirm {
    Accept,
    Complete
}

@Composable
fun DetailBookingScreen(
    bookingID: String = "",
    viewModel: DetailBookingVM = koinViewModel()
) = ScopeProvider(Scopes.MyBooking) {

    val appNavigator = use<AppNavigator>()
    val detail by viewModel.details.collectAsState()

    var hasShowCancel by remember { mutableStateOf(false) }
    var hasShowComingSoon by remember { mutableStateOf(false) }
    var detailManagerConfirm by remember { mutableStateOf<DetailManagerConfirm?>(null) }

    LaunchedEffect(bookingID) {
        viewModel.bookingID = bookingID
        if (bookingID.isNotBlank()) {
            viewModel.fetchDetails()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.DarkFF0A050A)
    ) {
        DetailBookingHero(
            imageUrl = detail?.clubCoverImage.orEmpty(),
            detail = detail,
            onBack = { appNavigator.back() },
            onMore = { hasShowComingSoon = true }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 32.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (detail != null) {
                val d = detail!!
                DetailBookingClubBlock(
                    clubName = d.clubNameDisplay,
                    address = d.address.ifBlank { stringResource(R.string.club_current_location_placeholder) }
                )
                DetailBookingWhenWhereRow(
                    dateLabel = if (d.isNow) {
                        stringResource(R.string.booking_mode_now)
                    } else {
                        d.bookingDateShort.ifBlank { stringResource(R.string.booking_date) }
                    },
                    timeLabel = if (d.isNow) {
                        d.timeDisplay
                    } else {
                        d.bookingTimeFormatted.ifBlank { "—" }
                    },
                    roomLabel = d.roomNameDisplay,
                    roomIcon = d.roomImagePlaceholder
                )
                DetailBookingDancersSection(detail = d)
                DetailBookingSummaryCard(detail = d)
                when (d.bookingActionsBar) {
                    BookingActionsBar.USER_STANDARD -> {
                        val canCancel =
                            d.status.equals(AppConfig.Booking.Status.PENDING, true) ||
                                d.status.equals(AppConfig.Booking.Status.SCHEDULED, true) ||
                                d.status.equals(AppConfig.Booking.Status.CONFIRMED, true) ||
                                d.status.equals(AppConfig.Booking.Status.ACCEPTED, true)
                        if (canCancel) {
                            DetailBookingActions(onCancel = { hasShowCancel = true })
                        }
                    }

                    BookingActionsBar.CLUB_MANAGER_ACCEPT_REJECT -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DetailBookingWidePrimaryButton(
                                labelRes = R.string.booking_action_accept,
                                onClick = { detailManagerConfirm = DetailManagerConfirm.Accept }
                            )
                            DetailBookingWideOutlinedButton(
                                labelRes = R.string.booking_action_reject,
                                onClick = { hasShowCancel = true },
                                useCancelVisualStyle = false
                            )
                        }
                    }

                    BookingActionsBar.CLUB_MANAGER_COMPLETE_CANCEL -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DetailBookingWidePrimaryButton(
                                labelRes = R.string.booking_action_complete,
                                onClick = { detailManagerConfirm = DetailManagerConfirm.Complete }
                            )
                            DetailBookingWideOutlinedButton(
                                labelRes = R.string.all_cancel,
                                onClick = { hasShowCancel = true },
                                useCancelVisualStyle = true
                            )
                        }
                    }

                    BookingActionsBar.NONE -> Unit
                }
                if (d.hasCancel) {
                    DetailBookingCancelledBlock(reason = d.reason)
                }
            } else {
                NoDataView(htmlRes = R.string.no_data)
            }
        }
    }

    if (hasShowComingSoon) {
        AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
            hasShowComingSoon = false
        }
    }

    if (hasShowCancel && detail != null) {
        CancellationReasonDialog(
            onConfirm = {
                hasShowCancel = false
                viewModel.submitCancel(it)
            },
            onDismiss = { hasShowCancel = false }
        )
    }

    when (detailManagerConfirm) {
        DetailManagerConfirm.Accept -> AppConfirmDialog(
            title = stringResource(R.string.booking_confirm_accept_title),
            message = stringResource(R.string.booking_confirm_accept_message),
            textConfirm = stringResource(R.string.all_confirm),
            onConfirm = {
                detailManagerConfirm = null
                viewModel.submitAccept()
            },
            onDismiss = { detailManagerConfirm = null }
        )

        DetailManagerConfirm.Complete -> AppConfirmDialog(
            title = stringResource(R.string.booking_confirm_complete_title),
            message = stringResource(R.string.booking_confirm_complete_message),
            textConfirm = stringResource(R.string.all_confirm),
            onConfirm = {
                detailManagerConfirm = null
                viewModel.submitComplete()
            },
            onDismiss = { detailManagerConfirm = null }
        )

        null -> Unit
    }
}

@Composable
private fun DetailBookingHero(
    imageUrl: String,
    detail: IBookingDetail?,
    onBack: () -> Unit,
    onMore: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Dark120812),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Transparent,
                            Colors.DarkFF0A050A
                        )
                    )
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailHeroCircleIcon(
                icon = Icons.Outlined.ArrowBackIosNew,
                onClick = onBack
            )
            DetailHeroCircleIcon(
                icon = Icons.Outlined.MoreHoriz,
                onClick = onMore
            )
        }
        if (detail != null) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailStatusChip(
                    label = detail.statusDisplay.uppercase(),
                    isAcceptedLike = detail.status.equals(AppConfig.Booking.Status.ACCEPTED, true) ||
                        detail.status.equals(AppConfig.Booking.Status.CONFIRMED, true) ||
                        detail.status.equals(AppConfig.Booking.Status.COMPLETED, true)
                )
                if (detail.showVipGuestBadge) {
                    DetailVipChip()
                }
            }
        }
    }
}

@Composable
private fun DetailHeroCircleIcon(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.3f))
            .border(1.dp, Colors.White1AFFFFFF, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Colors.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun DetailStatusChip(label: String, isAcceptedLike: Boolean) {
    val bg = if (isAcceptedLike) Colors.Emerald500 else Colors.Primary
    val border = if (isAcceptedLike) {
        Colors.Emerald500.copy(alpha = 0.5f)
    } else {
        Colors.Primary.copy(alpha = 0.5f)
    }
    Row(
        modifier = Modifier
            .height(28.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (isAcceptedLike) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun DetailVipChip() {
    Row(
        modifier = Modifier
            .height(28.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(Colors.Primary)
            .border(1.dp, Colors.Primary.copy(alpha = 0.5f), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.booking_detail_vip_guest),
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun DetailBookingWhenWhereRow(
    dateLabel: String,
    timeLabel: String,
    roomLabel: String,
    roomIcon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        DetailGlassMiniTile(
            title = stringResource(R.string.booking_date),
            value = dateLabel,
            icon = Icons.Outlined.CalendarToday,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        DetailGlassMiniTile(
            title = stringResource(R.string.booking_time),
            value = timeLabel,
            icon = Icons.Outlined.Schedule,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        DetailGlassMiniTile(
            title = stringResource(R.string.booking_detail_room_short),
            value = roomLabel,
            icon = roomIcon,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun DetailGlassMiniTile(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.Pink1AF425F4)
            .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(16.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Colors.Primary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title.uppercase(),
            color = Colors.Gray9CA3AF,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.2).sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            color = Colors.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 2.dp),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailBookingDancersSection(detail: IBookingDetail) {
    val names = detail.dancersDisplayList
    val avatars = detail.dancerAvatarsDisplay
    val styles = detail.dancerStyleLines
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.booking_selected_performers).uppercase(),
                color = Colors.Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = stringResource(R.string.booking_detail_reserved_format, names.size),
                color = Colors.Gray64748B,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (names.isEmpty()) {
            Text(
                text = stringResource(R.string.booking_detail_no_dancers),
                color = Colors.Gray64748B,
                fontSize = 13.sp
            )
        } else {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                names.forEachIndexed { index, name ->
                    DetailDancerTile(
                        name = name,
                        avatarUrl = avatars.getOrNull(index).orEmpty(),
                        subtitle = styles.getOrNull(index).orEmpty(),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailDancerTile(
    name: String,
    avatarUrl: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val sub = subtitle.ifBlank { stringResource(R.string.booking_detail_dancer_style_fallback) }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.Pink1AF425F4)
            .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(16.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .border(2.dp, Colors.Pink4DF425F4, CircleShape)
        ) {
            AvatarImage(url = avatarUrl, size = 44.dp)
        }
        Column {
            Text(
                text = name,
                color = Colors.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = sub.uppercase(),
                color = Colors.Gray9CA3AF,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DetailBookingSummaryCard(detail: IBookingDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Colors.Pink1AF425F4)
            .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(24.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DetailSummaryRow(
            icon = Icons.Outlined.Groups,
            label = stringResource(R.string.booking_guests_title),
            value = stringResource(R.string.booking_detail_guests_format, detail.numberOfGuests)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.White1AFFFFFF)
        )
        DetailSummaryRow(
            icon = Icons.Outlined.MusicNote,
            label = stringResource(R.string.booking_songs_title),
            value = stringResource(R.string.booking_detail_songs_format, detail.numberOfSongs)
        )
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.booking_total_amount_due).uppercase(),
                    color = Colors.Gray64748B,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Colors.Amber500.copy(alpha = 0.1f))
                        .border(1.dp, Colors.Amber500.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = stringResource(R.string.booking_cash_only),
                        color = Colors.Amber500,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = detail.totalAmountDisplay,
                    color = Colors.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = stringResource(R.string.booking_detail_incl_tax),
                    color = Colors.Gray64748B,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailSummaryRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Colors.Gray64748B,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = label,
                color = Colors.GrayCBD5E1,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            text = value,
            color = Colors.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun DetailBookingWidePrimaryButton(
    labelRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Primary,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = stringResource(labelRes),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun DetailBookingWideOutlinedButton(
    labelRes: Int,
    onClick: () -> Unit,
    useCancelVisualStyle: Boolean
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(28.dp),
        colors = if (useCancelVisualStyle) {
            ButtonDefaults.outlinedButtonColors(
                contentColor = Colors.RedEF4444.copy(alpha = 0.85f)
            )
        } else {
            ButtonDefaults.outlinedButtonColors(
                contentColor = Colors.Gray9CA3AF
            )
        },
        border = if (useCancelVisualStyle) {
            BorderStroke(1.dp, Colors.Red33EF4444)
        } else {
            BorderStroke(1.dp, Colors.White1AFFFFFF)
        }
    ) {
        Text(
            text = stringResource(labelRes),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun DetailBookingActions(
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DetailBookingWideOutlinedButton(
            labelRes = R.string.all_cancel,
            onClick = onCancel,
            useCancelVisualStyle = true
        )
    }
}

@Composable
private fun DetailBookingCancelledBlock(reason: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.White1AFFFFFF)
            .border(1.dp, Colors.White1AFFFFFF, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.all_cancel_reason),
            color = Colors.Primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = reason,
            color = Colors.White,
            fontSize = 14.sp
        )
    }
}

class DetailBookingVM(
    private val appEvent: AppEvent,
    private val fetchBookingDetailRepo: FetchBookingDetailRepo,
    private val bookingCancelRepo: BookingCancelRepo,
    private val bookingAcceptRepo: BookingAcceptRepo,
    private val bookingCompleteRepo: BookingCompleteRepo,
    private val appNotifications: AppNotifications
) : AppViewModel() {
    val details = fetchBookingDetailRepo.result
    var bookingID: String = ""

    fun fetchDetails() = launch(loading, error) {
        fetchBookingDetailRepo(bookingID)
        appNotifications.cancelNotification(bookingID.hashCode())
    }

    fun submitCancel(reason: String) = launch(loading, error) {
        bookingCancelRepo(bookingID, reason)
        fetchDetails()
        appEvent.onRefreshMyBooking.emit(true)
        appEvent.onRefreshNotification.emit(true)
    }

    fun submitAccept() = launch(loading, error) {
        bookingAcceptRepo(bookingID)
        fetchDetails()
        appEvent.onRefreshMyBooking.emit(true)
        appEvent.onRefreshNotification.emit(true)
    }

    fun submitComplete() = launch(loading, error) {
        bookingCompleteRepo(bookingID)
        fetchDetails()
        appEvent.onRefreshMyBooking.emit(true)
        appEvent.onRefreshNotification.emit(true)
    }
}

class FetchBookingDetailRepo(
    private val bookingApi: BookingApi,
    private val bookingFactory: BookingFactory
) {
    val result = MutableStateFlow<IBookingDetail?>(null)
    suspend operator fun invoke(id: String) {
        result.emit(bookingFactory.createDetails(bookingApi.details(id).awaitNullable()))
    }
}
