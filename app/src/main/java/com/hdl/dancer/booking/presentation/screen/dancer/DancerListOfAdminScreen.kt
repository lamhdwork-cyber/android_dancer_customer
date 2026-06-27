package com.hdl.dancer.booking.presentation.screen.dancer

import android.content.Context
import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.app.AppConfig
import com.hdl.dancer.booking.app.AppViewModel
import com.hdl.dancer.booking.data.local.UserLocalSource
import com.hdl.dancer.booking.domain.repo.ClubRepo
import com.hdl.dancer.booking.domain.repo.DancerRepo
import com.hdl.dancer.booking.data.factory.DancerFactory
import com.hdl.dancer.booking.presentation.model.support.Scopes
import com.hdl.dancer.booking.domain.model.search.IDancer
import com.hdl.dancer.booking.domain.usecase.FetchClubDancersAdminPageCase
import com.hdl.dancer.booking.presentation.extensions.ScopeProvider
import com.hdl.dancer.booking.presentation.extensions.launch
import com.hdl.dancer.booking.presentation.extensions.use
import com.hdl.dancer.booking.presentation.helper.AppNavigator
import com.hdl.dancer.booking.presentation.theme.Colors
import com.hdl.dancer.booking.presentation.widget.ActionBarDancerAdmin
import com.hdl.dancer.booking.presentation.widget.AppLazyColumn
import com.hdl.dancer.booking.presentation.widget.AvatarImage
import com.hdl.dancer.booking.presentation.widget.NoDataView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DancerListOfAdminScreen(viewModel: DancerListOfAdminVM = koinViewModel()) =
    ScopeProvider(Scopes.Dancer) {
        val appNavigator = use<AppNavigator>()
        val items by viewModel.items.collectAsState()
        val totalItems by viewModel.totalItems.collectAsState()
        val clubId by viewModel.clubId.collectAsState()
        val isLoading by viewModel.customLoading.isLoading().collectAsState()
        val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()
        val togglingIds by viewModel.togglingIds.collectAsState()
        val clubDisplayName by viewModel.clubDisplayName.collectAsState()
        val clubDisplayAddress by viewModel.clubDisplayAddress.collectAsState()

        val readyCount = items.count { it.isAvailableNow }
        val floorCount = items.size - readyCount
        val rosterTotal = totalItems ?: items.size
        val showNoData = clubId.isNotBlank() && items.isEmpty()

        LaunchedEffect(Unit) {
            viewModel.ensureClubIdAndLoad()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Dark120812)
        ) {
            ActionBarDancerAdmin(
                clubId = clubId,
                clubName = clubDisplayName,
                clubAddress = clubDisplayAddress,
            )
            if (clubId.isBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    NoDataView(htmlRes = R.string.dancer_admin_no_club)
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AdminStatCard(
                        label = stringResource(R.string.dancer_admin_ready),
                        value = readyCount,
                        accent = Colors.DancerAdminStatusReady,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        label = stringResource(R.string.dancer_admin_floor),
                        value = floorCount,
                        accent = Colors.DancerAdminStatusFloor,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dancer_admin_active_roster),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = stringResource(R.string.dancer_admin_total_format, rosterTotal),
                        color = Colors.Primary.copy(alpha = 0.85f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    AppLazyColumn(
                        items = items,
                        keyItem = { it.id },
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 8.dp,
                            end = 16.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        isLoading = isLoading,
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.onRefresh() },
                        onLoadMore = { viewModel.onFetch() },
                        emptyHtmlRes = R.string.no_data_dancer,
                        isEmpty = showNoData,
                        modifier = Modifier.fillMaxSize()
                    ) { dancer, _, _ ->
                        AdminDancerRow(
                            dancer = dancer,
                            isToggling = togglingIds.contains(dancer.id),
                            onOpenDetail = {
                                appNavigator.navigateDetailDancer(
                                    dancer.id,
                                    hasShowButtons = false
                                )
                            },
                            onToggleAvailability = { viewModel.toggleDancerAvailability(dancer.id) }
                        )
                    }
                }
            }
        }
    }

@Composable
private fun AdminStatCard(
    label: String,
    value: Int,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(accent.copy(alpha = 0.12f))
            .border(1.dp, accent.copy(alpha = 0.22f), RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label.uppercase(),
            color = accent,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.toString(),
            color = accent,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AdminDancerRow(
    dancer: IDancer,
    isToggling: Boolean,
    onOpenDetail: () -> Unit,
    onToggleAvailability: () -> Unit,
) {
    val isReady = dancer.isAvailableNow
    val accent = if (isReady) Colors.DancerAdminStatusReady else Colors.DancerAdminStatusFloor
    val statusLabel = stringResource(
        if (isReady) R.string.dancer_admin_ready else R.string.dancer_admin_floor
    ).uppercase()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.Pink0DF425F4)
            .border(1.dp, Colors.Pink1AF425F4, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onOpenDetail)
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(2.dp, accent, CircleShape)
                ) {
                    AvatarImage(url = dancer.avatar, size = 56.dp)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 2.dp, bottom = 2.dp)
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(accent)
                        .border(2.dp, Colors.DarkFF0A050A, CircleShape)
                )
            }
            Column(modifier = Modifier.weight(1f, fill = false)) {
                Text(
                    text = dancer.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = statusLabel,
                    color = accent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
        Switch(
            checked = isReady,
            onCheckedChange = { if (!isToggling) onToggleAvailability() },
            enabled = !isToggling,
            modifier = Modifier.padding(start = 8.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = accent,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = accent,
                disabledCheckedThumbColor = Color.White,
                disabledCheckedTrackColor = accent,
                disabledUncheckedThumbColor = Color.White,
                disabledUncheckedTrackColor = accent,
            )
        )
    }
}

class DancerListOfAdminVM(
    private val fetchClubDancersAdminPageCase: FetchClubDancersAdminPageCase,
    private val userLocalSource: UserLocalSource,
    private val dancerRepo: DancerRepo,
    private val dancerFactory: DancerFactory,
    private val clubRepo: ClubRepo,
    private val appContext: Context,
) : AppViewModel() {
    val customLoading: LoadingEvent = LoadingFlow()
    val isRefreshLoading: LoadingEvent = LoadingFlow()

    private var page = 1
    private var hasMoreData = true

    private val _clubId = MutableStateFlow("")
    val clubId: StateFlow<String> = _clubId

    private val _items = MutableStateFlow<List<IDancer>>(emptyList())
    val items: StateFlow<List<IDancer>> = _items

    private val _totalItems = MutableStateFlow<Int?>(null)
    val totalItems: StateFlow<Int?> = _totalItems

    private val _togglingIds = MutableStateFlow<Set<String>>(emptySet())
    val togglingIds: StateFlow<Set<String>> = _togglingIds

    private val _clubDisplayName = MutableStateFlow("")
    val clubDisplayName: StateFlow<String> = _clubDisplayName

    private val _clubDisplayAddress = MutableStateFlow("")
    val clubDisplayAddress: StateFlow<String> = _clubDisplayAddress

    private fun clearClubDisplay() {
        _clubDisplayName.value = ""
        _clubDisplayAddress.value = ""
    }

    private fun loadClubDetail(clubId: String) {
        if (clubId.isBlank()) return
        launch(loading, error) {
            val summary = clubRepo.fetchLocationSummary(clubId)
            val placeholder = appContext.getString(R.string.club_current_location_placeholder)
            if (summary != null) {
                _clubDisplayName.value = summary.name.trim().ifBlank { clubId }
                val addr = summary.address.trim()
                _clubDisplayAddress.value = addr.ifBlank { placeholder }
            } else {
                _clubDisplayName.value = clubId
                _clubDisplayAddress.value = placeholder
            }
        }
    }

    fun toggleDancerAvailability(dancerId: String) {
        if (_togglingIds.value.contains(dancerId)) return
        launch(loading, error) {
            _togglingIds.value += dancerId
            try {
                dancerRepo.toggleAvailability(dancerId)
                _items.value = _items.value.map { item ->
                    if (item.id == dancerId) {
                        dancerFactory.withAvailability(item, !item.isAvailableNow)
                    } else {
                        item
                    }
                }
            } finally {
                _togglingIds.value -= dancerId
            }
        }
    }

    fun ensureClubIdAndLoad() {
        val id = userLocalSource.getUserDto()?.clubId?.trim().orEmpty()
        if (id.isBlank()) {
            _clubId.value = ""
            _items.value = emptyList()
            _totalItems.value = null
            clearClubDisplay()
            return
        }
        if (_clubId.value == id && _items.value.isNotEmpty()) {
            if (_clubDisplayName.value.isBlank()) loadClubDetail(id)
            return
        }
        if (_clubId.value != id) {
            _clubId.value = id
            clearClubDisplay()
            onRefresh()
            return
        }
        if (_items.value.isEmpty() && !isRefreshLoading.isLoading().value && !customLoading.isLoading().value) {
            onRefresh()
        }
    }

    fun onRefresh() {
        page = 1
        hasMoreData = true
        _items.value = emptyList()
        _totalItems.value = null
        if (_clubId.value.isNotBlank()) loadClubDetail(_clubId.value)
        onFetch()
    }

    fun onFetch() {
        val cid = _clubId.value
        if (cid.isBlank()) return
        if (isRefreshLoading.isLoading().value || customLoading.isLoading().value || !hasMoreData) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val (dancers, totalFromMeta) = fetchClubDancersAdminPageCase(clubId = cid, page = page)
            if (page == 1) {
                _totalItems.value = totalFromMeta
            }
            val rs = dancers
            if (rs.isEmpty()) {
                hasMoreData = false
            } else {
                if (rs.size < AppConfig.PER_PAGE) hasMoreData = false
                val current = _items.value
                val newItems = rs.filterNot { item -> current.any { it.id == item.id } }
                if (newItems.isEmpty()) {
                    hasMoreData = false
                    return@launch
                }
                _items.value = current + newItems
                page++
            }
        }
    }
}
