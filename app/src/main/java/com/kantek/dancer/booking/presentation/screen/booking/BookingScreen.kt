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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.domain.factory.BookingFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingPerformer
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingScheduleDay
import com.kantek.dancer.booking.domain.model.ui.booking.IRoom
import com.kantek.dancer.booking.domain.model.ui.search.IDancerDetail
import com.kantek.dancer.booking.domain.usecase.FetchRoomsByClubCase
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PICKED_DANCER_ID
import com.kantek.dancer.booking.presentation.helper.AppPopup
import com.kantek.dancer.booking.presentation.screen.dancer.FetchDetailDancerRepo
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingScreen(
    dancerId: String = "",
    clubId: String = "",
    hasNow: Boolean = true,
    navBackStackEntry: NavBackStackEntry,
    viewModel: BookingVM = koinViewModel()
) = ScopeProvider(Scopes.Booking) {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val appPopup = use<AppPopup>(Scopes.App)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()

    val pickedFlow = remember(navBackStackEntry) {
        navBackStackEntry.savedStateHandle.getStateFlow<String?>(PICKED_DANCER_ID, null)
    }
    val pickedDancerId by pickedFlow.collectAsState()

    LaunchedEffect(dancerId, clubId, hasNow) {
        viewModel.loadMockData(dancerId = dancerId, clubId = clubId, hasNow = hasNow)
    }

    LaunchedEffect(pickedDancerId) {
        val id = pickedDancerId ?: return@LaunchedEffect
        navBackStackEntry.savedStateHandle.remove<String>(PICKED_DANCER_ID)
        val messageRes = viewModel.addPerformerFromPick(id)
        if (messageRes != null) {
            appPopup.show(context.getString(messageRes))
        }
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
                .padding(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                PerformerSection(
                    performers = state.performers,
                    canAddMore = state.performers.size < BookingVM.MAX_PERFORMERS,
                    onAddClick = {
                        when {
                            state.performers.size >= BookingVM.MAX_PERFORMERS ->
                                coroutineScope.launch {
                                    appPopup.show(context.getString(R.string.booking_max_performers_message))
                                }

                            state.clubId.isBlank() ->
                                coroutineScope.launch {
                                    appPopup.show(context.getString(R.string.booking_add_performer_no_club))
                                }

                            else ->
                                appNavigator.navigateDancerListForBookingPick(
                                    clubId = state.clubId,
                                    excludeDancerIds = state.performers.map { it.id }
                                )
                        }
                    }
                )
            }

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

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                RoomSection(
                    rooms = state.rooms,
                    selectedRoomId = state.selectedRoomId,
                    onSelectRoom = { viewModel.selectRoom(it) }
                )
            }

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                StepperCard(
                    title = stringResource(R.string.booking_songs_title),
                    subtitle = stringResource(R.string.booking_songs_subtitle),
                    value = state.songs,
                    icon = Icons.Outlined.MusicNote,
                    onIncrease = { viewModel.increaseSongs() },
                    onDecrease = { viewModel.decreaseSongs() }
                )
            }
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                StepperCard(
                    title = stringResource(R.string.booking_guests_title),
                    subtitle = stringResource(R.string.booking_guests_subtitle),
                    value = state.guests,
                    icon = Icons.Outlined.PersonAdd,
                    onIncrease = { viewModel.increaseGuests() },
                    onDecrease = { viewModel.decreaseGuests() }
                )
            }

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                val collectRaw = remember(state.performers.size, state.selectedRoomId, state.rooms) {
                    val performerCount = state.performers.size
                    val selectedRoomPrice = state.rooms
                        .firstOrNull { it.id == state.selectedRoomId }
                        ?.price
                        ?.toDoubleOrNull()
                        ?: 0.0

                    val collectAmount = performerCount * selectedRoomPrice
                    val format = NumberFormat.getNumberInstance(Locale.US).apply {
                        minimumFractionDigits = 0
                        maximumFractionDigits = 2
                    }
                    format.format(collectAmount)
                }

                val collectText = stringResource(R.string.booking_collect_format, collectRaw)

                SummarySection(collectText = collectText)
            }
            Spacer(modifier = Modifier.height(100.dp))
        }

        AppButton(
            nameRes = R.string.booking_confirm,
            isEnabled = state.performers.isNotEmpty(),
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
private fun PerformerSection(
    performers: List<IBookingPerformer>,
    canAddMore: Boolean,
    onAddClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.booking_selected_performers),
                color = Colors.Gray9CA3AF,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(
                    R.string.booking_total_format,
                    performers.size,
                    BookingVM.MAX_PERFORMERS
                ),
                color = Colors.Primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            performers.forEach { performer ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                2.dp,
                                Colors.Primary,
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
                        color = Colors.Primary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (canAddMore) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(2.dp, Colors.White1AFFFFFF, RoundedCornerShape(14.dp))
                            .clickable { onAddClick() }
                            .background(Colors.White1AFFFFFF),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null,
                            tint = Colors.GrayCBD5E1
                        )
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
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(R.string.booking_schedule).uppercase(),
            color = Colors.Gray9CA3AF,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            modifier = Modifier
                .graphicsLayer(clip = false),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(days.size) { index ->
                val day = days[index]
                val selected = index == selectedDay
                Column(
                    modifier = Modifier
                        .widthIn(min = 58.dp)
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
                    Text(
                        day.label,
                        color = if (selected) Colors.White else Colors.GrayCBD5E1,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        day.dayNumber,
                        color = Colors.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
        LazyRow(
            modifier = Modifier
                .graphicsLayer(clip = false),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(times.size) { index ->
                val time = times[index]
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
        }
    }
}

@Composable
private fun RoomSection(
    rooms: List<IRoom>,
    selectedRoomId: String,
    onSelectRoom: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(R.string.booking_select_room).uppercase(),
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
                        AsyncImage(
                            colorFilter = ColorFilter.tint(
                                if (selected) Colors.Primary else Colors.Blue148
                            ),
                            model = room.imageURL,
                            contentDescription = null,
                            placeholder = rememberVectorPainter(room.imagePlaceholder),
                            error = rememberVectorPainter(room.imagePlaceholder),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = room.name,
                            color = Colors.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = room.services,
                            color = Colors.GrayCBD5E1,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
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
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Colors.Primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, color = Colors.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(
                    subtitle,
                    color = Colors.GrayCBD5E1,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
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
        Icon(
            icon,
            contentDescription = null,
            tint = Colors.Primary,
            modifier = Modifier.size(25.dp)
        )
    }
}

@Composable
private fun SummarySection(
    collectText: String
) {
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
            text = collectText,
            color = Colors.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text(
//                stringResource(R.string.booking_base_fee),
//                color = Colors.GrayCBD5E1,
//                fontSize = 10.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                stringResource(R.string.booking_addons_fee),
//                color = Colors.GrayCBD5E1,
//                fontSize = 10.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
    }
}

data class BookingState(
    val performers: List<IBookingPerformer> = emptyList(),
    val rooms: List<IRoom> = emptyList(),
    val scheduleDays: List<IBookingScheduleDay> = emptyList(),
    val scheduleTimes: List<String> = emptyList(),
    val selectedDay: Int = 0,
    val selectedTime: String = "",
    val selectedRoomId: String = "",
    val songs: Int = 4,
    val guests: Int = 2,
    val dancerId: String = "",
    val clubId: String = "",
    val hasNow: Boolean = true
)

class BookingVM(
    private val fetchDetailDancerRepo: FetchDetailDancerRepo,
    private val fetchRoomsByClubCase: FetchRoomsByClubCase,
    private val bookingFactory: BookingFactory
) : AppViewModel() {
    private val _state = MutableStateFlow(BookingState())
    val state: StateFlow<BookingState> = _state

    fun loadMockData(dancerId: String, clubId: String, hasNow: Boolean) = launch(loading, error) {
        val current = _state.value
        val hasLoadedSameInput =
            current.dancerId == dancerId &&
                    current.clubId == clubId &&
                    current.hasNow == hasNow &&
                    current.scheduleDays.isNotEmpty()
        if (hasLoadedSameInput) return@launch

        val initialPerformers = if (dancerId.isNotBlank()) {
            fetchDetailDancerRepo(dancerId)?.toBookingPerformer()?.let { listOf(it) } ?: emptyList()
        } else {
            emptyList()
        }

        val rooms = if (clubId.isNotBlank()) {
            fetchRoomsByClubCase(clubId = clubId)
        } else emptyList()

        val scheduleDays = bookingFactory.createScheduleDays()
        val scheduleTimes = bookingFactory.createScheduleTimes()

        _state.value = BookingState(
            performers = initialPerformers,
            rooms = rooms,
            scheduleDays = scheduleDays,
            scheduleTimes = scheduleTimes,
            selectedRoomId = rooms.firstOrNull()?.id.orEmpty(),
            selectedTime = scheduleTimes.firstOrNull().orEmpty(),
            dancerId = dancerId,
            clubId = clubId,
            hasNow = hasNow
        )
    }

    suspend fun addPerformerFromPick(dancerId: String): Int? {
        val cur = _state.value
        if (cur.performers.size >= MAX_PERFORMERS) return R.string.booking_max_performers_message
        if (cur.performers.any { it.id == dancerId }) return null
        val detail = fetchDetailDancerRepo(dancerId) ?: return null
        _state.value = cur.copy(performers = cur.performers + detail.toBookingPerformer())
        return null
    }

    companion object {
        const val MAX_PERFORMERS = 5
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

private fun IDancerDetail.toBookingPerformer(): IBookingPerformer {
    val d = this
    return object : IBookingPerformer {
        override val id: String = d.id
        override val name: String = d.name
        override val avatar: String = d.avatar
    }
}


