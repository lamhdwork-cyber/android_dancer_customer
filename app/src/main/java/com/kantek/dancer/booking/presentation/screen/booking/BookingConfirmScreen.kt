package com.kantek.dancer.booking.presentation.screen.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.data.model.form.BookingForm
import com.kantek.dancer.booking.presentation.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.BookingSuccessDialog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BookingConfirmScreen(
    dancerIds: String = "",
    dancerNames: String = "",
    dancerAvatars: String = "",
    roomId: String = "",
    clubName: String = "",
    clubImage: String = "",
    bookingDate: String = "",
    bookingTime: String = "",
    roomName: String = "",
    songs: Int = 1,
    guests: Int = 1,
    totalAmount: String = "0",
    hasNow: Boolean = true,
    viewModel: BookingConfirmVM = koinViewModel()
) = ScopeProvider(Scopes.BookingConfirm) {
    val appNavigator = use<AppNavigator>()
    val showSuccessDialog = remember { mutableStateOf(false) }
    val createdBookingId = remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(
        dancerIds,
        dancerNames,
        dancerAvatars,
        roomId,
        clubName,
        clubImage,
        bookingDate,
        bookingTime,
        roomName,
        songs,
        guests,
        totalAmount,
        hasNow
    ) {
        viewModel.bindArgs(
            dancerIds = dancerIds,
            dancerNames = dancerNames,
            dancerAvatars = dancerAvatars,
            roomId = roomId,
            clubName = clubName,
            clubImage = clubImage,
            bookingDate = bookingDate,
            bookingTime = bookingTime,
            roomName = roomName,
            songs = songs,
            guests = guests,
            totalAmount = totalAmount,
            hasNow = hasNow
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
            .navigationBarsPadding()
    ) {
        ActionBarBackAndTitleView(R.string.booking_confirm_title) { appNavigator.back() }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ClubCard(summary = state)
            if (!state.hasNow) {
                DateTimeRow(summary = state)
            }
            RoomCard(summary = state)
            DancersCard(summary = state)
            AmountCard(summary = state)
            Spacer(modifier = Modifier.height(100.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.OverlayCC120812)
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            AppButton(
                nameRes = R.string.booking_confirm_uppercase,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp)),
                textColor = Colors.White,
                iconStartVector = Icons.Outlined.Verified,
                iconStartTint = Colors.White,
                onClick = {
                    viewModel.submit { bookingId ->
                        createdBookingId.value = bookingId
                        showSuccessDialog.value = true
                    }
                }
            )
        }
    }


    if (showSuccessDialog.value) {
        BookingSuccessDialog(
            title = stringResource(R.string.booking_request_submitted),
            message = stringResource(R.string.booking_success),
            textConfirm = stringResource(R.string.all_view_detail),
            onConfirm = {
                showSuccessDialog.value = false
                if (createdBookingId.value.isNotBlank()) {
                    appNavigator.navigateDetailCaseAfterBooking(createdBookingId.value)
                } else {
                    appNavigator.navigateHomeMyBookings()
                }
            },
            onDismiss = {
                showSuccessDialog.value = false
                appNavigator.navigateHomeAndClearStack()
            }
        )
    }
}

@Composable
private fun ClubCard(summary: BookingConfirmUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Colors.Pink1AF425F4)
            .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(20.dp))
    ) {
        AsyncImage(
            model = summary.clubImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = summary.clubName,
            color = Colors.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun DateTimeRow(summary: BookingConfirmUi) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DateTimeItem(
            title = stringResource(R.string.booking_date),
            value = summary.dateText,
            icon = Icons.Outlined.CalendarToday,
            modifier = Modifier.weight(1f)
        )
        DateTimeItem(
            title = stringResource(R.string.booking_time),
            value = summary.timeText,
            icon = Icons.Outlined.Schedule,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DateTimeItem(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Colors.White1AFFFFFF)
            .border(1.dp, Colors.White1AFFFFFF, RoundedCornerShape(14.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Colors.Pink26F425F4),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Colors.Primary,
                modifier = Modifier.size(18.dp)
            )
        }
        Column {
            Text(title, color = Colors.Gray9CA3AF, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, color = Colors.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RoomCard(summary: BookingConfirmUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.White1AFFFFFF)
            .border(1.dp, Colors.White1AFFFFFF, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Colors.Pink26F425F4)
                .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Bed,
                contentDescription = null,
                tint = Colors.Primary
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.booking_selected_room_type),
                color = Colors.Gray9CA3AF,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (summary.roomId.isBlank()) summary.roomName else "${summary.roomName} (#${
                    summary.roomId.takeLast(
                        6
                    )
                })",
                color = Colors.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = stringResource(R.string.booking_private),
            color = Colors.Primary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(Colors.Pink26F425F4)
                .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(999.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
private fun DancersCard(summary: BookingConfirmUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Colors.Pink1AF425F4)
            .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.booking_selected_performers),
            color = Colors.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            summary.dancers.forEach { dancer ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .border(2.dp, Colors.Primary, CircleShape)
                            .padding(2.dp)
                    ) {
                        AsyncImage(
                            model = dancer.avatar,
                            contentDescription = dancer.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        dancer.name,
                        color = Colors.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AmountCard(summary: BookingConfirmUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Colors.White1AFFFFFF)
            .border(1.dp, Colors.White1AFFFFFF, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                stringResource(R.string.booking_songs_title),
                color = Colors.GrayCBD5E1,
                fontSize = 14.sp
            )
            Text(
                summary.songs.toString(),
                color = Colors.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                stringResource(R.string.booking_guests_title),
                color = Colors.GrayCBD5E1,
                fontSize = 14.sp
            )
            Text(
                summary.guests.toString(),
                color = Colors.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.White1AFFFFFF)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.booking_total_amount_due),
                    color = Colors.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.booking_collected_at_venue),
                    color = Colors.Gray9CA3AF,
                    fontSize = 11.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.booking_cash_only),
                    color = Colors.Orange251,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Colors.Pink1AF425F4)
                        .border(1.dp, Colors.Orange251, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    summary.totalText,
                    color = Colors.Primary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text(stringResource(R.string.booking_base_fee_295), color = Colors.Gray9CA3AF, fontSize = 11.sp)
//            Text(stringResource(R.string.booking_addons_fee_zero), color = Colors.Gray9CA3AF, fontSize = 11.sp)
//        }
    }
}

data class BookingConfirmUi(
    val clubName: String,
    val clubImage: String,
    val dateText: String,
    val timeText: String,
    val roomName: String,
    val songs: Int,
    val guests: Int,
    val totalText: String,
    val dancers: List<BookingConfirmDancerUi>,
    val roomId: String,
    val dancerIds: List<String>,
    val hasNow: Boolean
)

data class BookingConfirmDancerUi(
    val id: String,
    val name: String,
    val avatar: String
)

class BookingConfirmVM(
    private val bookingConfirmRepo: BookingConfirmRepo,
    private val appEvent: AppEvent
) : AppViewModel() {
    private val _state = MutableStateFlow(
        BookingConfirmUi(
            clubName = "",
            clubImage = "",
            dateText = "",
            timeText = "",
            roomName = "",
            songs = 1,
            guests = 1,
            totalText = "",
            dancers = emptyList(),
            roomId = "",
            dancerIds = emptyList(),
            hasNow = true
        )
    )
    val state: StateFlow<BookingConfirmUi> = _state

    fun bindArgs(
        dancerIds: String,
        dancerNames: String,
        dancerAvatars: String,
        roomId: String,
        clubName: String,
        clubImage: String,
        bookingDate: String,
        bookingTime: String,
        roomName: String,
        songs: Int,
        guests: Int,
        totalAmount: String,
        hasNow: Boolean
    ) {
        val ids = dancerIds.split(",").map { it.trim() }.filter { it.isNotBlank() }
        val names = dancerNames.split("|,|").map { it.trim() }.filter { it.isNotBlank() }
        val avatars = dancerAvatars.split("|,|").map { it.trim() }
        val dancers = ids.mapIndexed { index, id ->
            BookingConfirmDancerUi(
                id = id,
                name = names.getOrNull(index).orEmpty().ifBlank { "Dancer ${index + 1}" },
                avatar = avatars.getOrNull(index).orEmpty()
            )
        }
        val totalValue = totalAmount.toDoubleOrNull() ?: 0.0
        val totalNumber = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }.format(totalValue)
        val totalText = "$$totalNumber"
        _state.value = BookingConfirmUi(
            clubName = clubName.ifBlank { roomId },
            clubImage = clubImage,
            dateText = bookingDate,
            timeText = bookingTime,
            roomName = roomName,
            songs = songs,
            guests = guests,
            totalText = totalText,
            dancers = dancers,
            roomId = roomId,
            dancerIds = ids,
            hasNow = hasNow
        )
    }

    fun submit(onSuccess: suspend (bookingId: String) -> Unit) = launch(loading, error) {
        val current = _state.value
        if (current.dancerIds.isEmpty() || current.roomId.isBlank()) return@launch
        val bookingId = bookingConfirmRepo(
            dancerIds = current.dancerIds,
            roomId = current.roomId,
            songs = current.songs,
            guests = current.guests,
            bookingDate = current.dateText,
            bookingTime = current.timeText,
            hasNow = current.hasNow
        )
        appEvent.onRefreshMyBooking.emit(true)
        onSuccess(bookingId)
    }
}

class BookingConfirmRepo(
    private val bookingApi: BookingApi
) {
    suspend operator fun invoke(
        dancerIds: List<String>,
        roomId: String,
        songs: Int,
        guests: Int,
        bookingDate: String,
        bookingTime: String,
        hasNow: Boolean
    ): String {
        if (hasNow) {
            return bookingApi.bookNow(
                BookingForm(
                    dancerIds = dancerIds,
                    roomId = roomId,
                    numberOfSongs = songs,
                    numberOfGuests = guests
                )
            ).await().firstOrNull()?.id.orEmpty()
        }

        val startTime = toApiTime(bookingTime)
        return bookingApi.reserve(
            BookingForm(
                dancerIds = dancerIds,
                roomId = roomId,
                bookingDate = bookingDate,
                startTime = startTime,
                endTime = addMinutes(startTime, 20),
                numberOfSongs = songs,
                numberOfGuests = guests
            )
        ).await().firstOrNull()?.id.orEmpty()
    }

    private fun toApiTime(displayTime: String): String {
        val value = displayTime.trim()
        if (value.isBlank()) return "00:00"
        val regex = Regex("^(\\d{1,2}):(\\d{2})\\s*([AP]M)$", RegexOption.IGNORE_CASE)
        val match = regex.find(value) ?: return value
        val hourRaw = match.groupValues[1].toIntOrNull() ?: return value
        val minute = match.groupValues[2]
        val period = match.groupValues[3].uppercase(Locale.getDefault())
        var hour24 = hourRaw % 12
        if (period == "PM") hour24 += 12
        return String.format(Locale.US, "%02d:%s", hour24, minute)
    }

    private fun addMinutes(time: String, minutesToAdd: Int): String {
        val match = Regex("^(\\d{1,2}):(\\d{2})$").find(time.trim()) ?: return time
        val hour = match.groupValues[1].toIntOrNull() ?: return time
        val minute = match.groupValues[2].toIntOrNull() ?: return time
        val totalMinutes = ((hour * 60 + minute + minutesToAdd) % (24 * 60) + (24 * 60)) % (24 * 60)
        val newHour = totalMinutes / 60
        val newMinute = totalMinutes % 60
        return String.format(Locale.US, "%02d:%02d", newHour, newMinute)
    }
}

