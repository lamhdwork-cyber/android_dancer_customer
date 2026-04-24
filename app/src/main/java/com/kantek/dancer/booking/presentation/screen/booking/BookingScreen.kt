package com.kantek.dancer.booking.presentation.screen.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingPerformer
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingRoom
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingScheduleDay
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingScreen(
    dancerId: String = "",
    hasNow: Boolean = true,
    viewModel: BookingVM = koinViewModel()
) = ScopeProvider(Scopes.Booking) {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val state by viewModel.state.collectAsState()

    LaunchedEffect(dancerId, hasNow) {
        viewModel.loadMockData(dancerId = dancerId, hasNow = hasNow)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.DarkFF0A050A)
    ) {
        ActionBarBackAndTitleView(R.string.booking_title) { appNavigator.back() }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            PerformerSection(performers = state.performers)

            if (!state.hasNow) {
                ScheduleSection(
                    days = state.scheduleDays,
                    selectedDay = state.selectedDay,
                    selectedTime = state.selectedTime,
                    times = state.scheduleTimes,
                    onSelectDay = { viewModel.selectDay(it) },
                    onSelectTime = { viewModel.selectTime(it) }
                )
            }

            RoomSection(
                rooms = state.rooms,
                selectedRoomId = state.selectedRoomId,
                onSelectRoom = { viewModel.selectRoom(it) }
            )

            StepperCard(
                title = stringResource(R.string.booking_songs_title),
                subtitle = stringResource(R.string.booking_songs_subtitle),
                value = state.songs,
                icon = Icons.Outlined.MusicNote,
                onIncrease = { viewModel.increaseSongs() },
                onDecrease = { viewModel.decreaseSongs() }
            )
            StepperCard(
                title = stringResource(R.string.booking_guests_title),
                subtitle = stringResource(R.string.booking_guests_subtitle),
                value = state.guests,
                icon = Icons.Outlined.PersonAdd,
                onIncrease = { viewModel.increaseGuests() },
                onDecrease = { viewModel.decreaseGuests() }
            )

            SummarySection()
            Spacer(modifier = Modifier.height(100.dp))
        }

        AppButton(
            nameRes = R.string.booking_confirm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            textColor = Colors.White,
            iconStartVector = Icons.Outlined.Verified,
            iconStartTint = Colors.White,
            onClick = {
                appNavigator.navigateBookingConfirm(
                    dancerIds = state.performers.map { it.id },
                    roomId = state.selectedRoomId
                )
            }
        )
    }
}

@Composable
private fun PerformerSection(performers: List<IBookingPerformer>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.booking_selected_performers),
                color = Colors.Gray9CA3AF,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.booking_total_format, performers.size, 5),
                color = Colors.Primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            performers.forEachIndexed { index, performer ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                2.dp,
                                if (index == 0) Colors.Primary else Colors.Pink33F425F4,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(3.dp)
                    ) {
                        AsyncImage(
                            model = performer.avatar,
                            contentDescription = performer.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(11.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = performer.name,
                        color = if (index == 0) Colors.Primary else Colors.GrayCBD5E1,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .border(2.dp, Colors.White1AFFFFFF, RoundedCornerShape(14.dp))
                        .clickable {}
                        .background(Colors.White1AFFFFFF),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Add, contentDescription = null, tint = Colors.GrayCBD5E1)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.booking_add),
                    color = Colors.GrayCBD5E1,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ScheduleSection(
    days: List<IBookingScheduleDay>,
    selectedDay: Int,
    selectedTime: String,
    times: List<String>,
    onSelectDay: (Int) -> Unit,
    onSelectTime: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(R.string.booking_schedule),
            color = Colors.Gray9CA3AF,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .requiredWidth(screenWidth)
                .offset(x = (-16).dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            days.forEachIndexed { index, day ->
                val selected = index == selectedDay
                Column(
                    modifier = Modifier
                        .width(58.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (selected) Colors.Primary else Colors.White1AFFFFFF)
                        .border(
                            1.dp,
                            if (selected) Colors.Primary else Colors.White1AFFFFFF,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { onSelectDay(index) }
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(day.label, color = if (selected) Colors.White else Colors.GrayCBD5E1, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(day.dayNumber, color = Colors.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        Row(
            modifier = Modifier
                .requiredWidth(screenWidth)
                .offset(x = (-16).dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            times.forEach { time ->
                val selected = time == selectedTime
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            if (selected) Colors.Pink66F425F4 else Colors.White1AFFFFFF,
                            RoundedCornerShape(10.dp)
                        )
                        .background(if (selected) Colors.Pink26F425F4 else Colors.White1AFFFFFF)
                        .clickable { onSelectTime(time) }
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ) {
                    Text(
                        text = time,
                        color = if (selected) Colors.Primary else Colors.GrayCBD5E1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
private fun RoomSection(
    rooms: List<IBookingRoom>,
    selectedRoomId: String,
    onSelectRoom: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(R.string.booking_select_room),
            color = Colors.Gray9CA3AF,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        rooms.forEach { room ->
            val selected = room.id == selectedRoomId
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        1.dp,
                        if (selected) Colors.Pink66F425F4 else Colors.White1AFFFFFF,
                        RoundedCornerShape(20.dp)
                    )
                    .background(if (selected) Colors.Pink26F425F4 else Colors.White1AFFFFFF)
                    .clickable { onSelectRoom(room.id) }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Colors.Pink26F425F4),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (room.icon == "king_bed") Icons.Outlined.Bed else Icons.Outlined.MeetingRoom,
                            contentDescription = null,
                            tint = Colors.Primary
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = stringResource(if (room.nameKey == "vip_room") R.string.booking_room_vip else R.string.booking_room_suite),
                            color = Colors.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(
                                if (room.descriptionKey == "vip_room_desc") R.string.booking_room_vip_desc else R.string.booking_room_suite_desc
                            ),
                            color = Colors.GrayCBD5E1,
                            fontSize = 11.sp
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(R.string.booking_price_format, room.price),
                        color = if (selected) Colors.Primary else Colors.White,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(if (selected) Colors.Primary else Colors.White1AFFFFFF),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selected) {
                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = null,
                                tint = Colors.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepperCard(
    title: String,
    subtitle: String,
    value: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.White1AFFFFFF)
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Colors.Pink26F425F4),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Colors.Primary, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, color = Colors.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(subtitle, color = Colors.GrayCBD5E1, fontWeight = FontWeight.Medium, fontSize = 10.sp)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            StepButton(Icons.Outlined.Remove, onDecrease)
            Text(
                text = value.toString(),
                color = Colors.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .widthIn(min = 24.dp)
            )
            StepButton(Icons.Outlined.Add, onIncrease)
        }
    }
}

@Composable
private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(2.dp, Colors.Pink4DF425F4, CircleShape)
            .background(Colors.Pink26F425F4)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = Colors.Primary, modifier = Modifier.size(25.dp))
    }
}

@Composable
private fun SummarySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Colors.Pink26F425F4,
                        Colors.Pink0DF425F4,
                        Colors.White1AFFFFFF
                    )
                )
            )
            .border(1.dp, Colors.Pink66F425F4, RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.booking_total_due),
                color = Colors.Primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Colors.Green.copy(alpha = 0.20f))
                    .border(1.dp, Colors.Green.copy(alpha = 0.45f), RoundedCornerShape(999.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Colors.Green103)
                )
                Text(
                    text = stringResource(R.string.booking_cash_only),
                    color = Colors.Green103,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.booking_collect),
            color = Colors.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.booking_base_fee), color = Colors.GrayCBD5E1, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.booking_addons_fee), color = Colors.GrayCBD5E1, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

data class BookingState(
    val performers: List<IBookingPerformer> = emptyList(),
    val rooms: List<IBookingRoom> = emptyList(),
    val scheduleDays: List<IBookingScheduleDay> = emptyList(),
    val scheduleTimes: List<String> = emptyList(),
    val selectedDay: Int = 0,
    val selectedTime: String = "",
    val selectedRoomId: String = "",
    val songs: Int = 4,
    val guests: Int = 2,
    val dancerId: String = "",
    val hasNow: Boolean = true
)

class BookingVM(
    private val fetchBookingMockRepo: FetchBookingMockRepo
) : AppViewModel() {
    private val _state = MutableStateFlow(BookingState())
    val state: StateFlow<BookingState> = _state

    fun loadMockData(dancerId: String, hasNow: Boolean) = launch(loading, error) {
        val seed = fetchBookingMockRepo()
        _state.value = _state.value.copy(
            performers = seed.performers,
            rooms = seed.rooms,
            scheduleDays = seed.scheduleDays,
            scheduleTimes = seed.scheduleTimes,
            selectedRoomId = seed.rooms.firstOrNull()?.id.orEmpty(),
            selectedTime = seed.scheduleTimes.firstOrNull().orEmpty(),
            dancerId = dancerId,
            hasNow = hasNow
        )
    }

    fun selectDay(index: Int) {
        _state.value = _state.value.copy(selectedDay = index)
    }

    fun selectTime(value: String) {
        _state.value = _state.value.copy(selectedTime = value)
    }

    fun selectRoom(id: String) {
        _state.value = _state.value.copy(selectedRoomId = id)
    }

    fun increaseSongs() {
        _state.value = _state.value.copy(songs = _state.value.songs + 1)
    }

    fun decreaseSongs() {
        if (_state.value.songs <= 1) return
        _state.value = _state.value.copy(songs = _state.value.songs - 1)
    }

    fun increaseGuests() {
        _state.value = _state.value.copy(guests = _state.value.guests + 1)
    }

    fun decreaseGuests() {
        if (_state.value.guests <= 1) return
        _state.value = _state.value.copy(guests = _state.value.guests - 1)
    }
}

data class BookingMockSeed(
    val performers: List<IBookingPerformer>,
    val rooms: List<IBookingRoom>,
    val scheduleDays: List<IBookingScheduleDay>,
    val scheduleTimes: List<String>
)
