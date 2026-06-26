package com.kantek.dancer.booking.presentation.screen.dancer
import android.support.ui.extension.onClick

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.app.AppScopes
import com.kantek.dancer.booking.domain.model.search.IDancer
import com.kantek.dancer.booking.domain.usecase.FetchDancerByClubCase
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import android.support.ui.extension.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.navigation.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import android.support.ui.widget.AppLazyVerticalGrid
import android.support.ui.widget.NoDataView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DancerListScreen(
    clubId: String,
    pickForBooking: Boolean = false,
    excludeDancerIds: Set<String> = emptySet(),
    viewModel: DancerListVM = koinViewModel()
) = ScopeProvider(AppScopes.Dancer) {
    val appNavigator = use<AppNavigator>()
    val dancers by viewModel.items.collectAsState()
    val isLoading by viewModel.customLoading.isLoading().collectAsState()
    val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()

    val visibleDancers = remember(dancers, excludeDancerIds) {
        dancers.filter { it.id !in excludeDancerIds }
    }
    val showNoData = visibleDancers.isEmpty()

    LaunchedEffect(clubId) {
        viewModel.updateClubId(clubId)
        viewModel.onRefresh()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        ActionBarBackAndTitleView(
            if (pickForBooking) R.string.booking_pick_performer_title else R.string.top_bar_dancer_list
        ) { appNavigator.back() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Dark120812)
        ) {
            AppLazyVerticalGrid(
                items = visibleDancers,
                keyItem = { it.id },
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                isIndicatorRefreshing = isRefreshing || isLoading,
                onLoadMore = { viewModel.onFetch() },
                onRefresh = { viewModel.onRefresh() },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Dark120812)
            ) { item, _, _ ->
                DancerCard(item) {
                    if (pickForBooking) {
                        appNavigator.finishBookingDancerPick(item.id)
                    } else {
                        appNavigator.navigateDetailDancer(item.id)
                    }
                }
            }
            if (showNoData) {
                NoDataView(htmlRes = R.string.no_data_dancer)
            }
        }
    }
}

@Composable
private fun DancerCard(
    dancer: IDancer,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.76f)
            .onClick { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Dark120812)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(dancer.avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = dancer.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Colors.White33FFFFFF,
                                Colors.DarkAA1A0D18,
                                Colors.Dark120812
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clip(CircleShape)
                    .background(Colors.Dark660F172A)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Colors.Green103)
                )
                Text(
                    text = stringResource(
                        if (dancer.isAvailableNow) R.string.dancer_available_now else R.string.club_closed
                    ).uppercase(),
                    color = Colors.Green103,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = dancer.name,
                    color = Colors.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Colors.Primary,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = dancer.danceStyle.ifBlank {
                            stringResource(R.string.dancer_style_unknown)
                        },
                        color = Colors.Primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

class DancerListVM(
    private val fetchDancerByClubCase: FetchDancerByClubCase
) : AppViewModel() {
    val customLoading: LoadingEvent = LoadingFlow()
    val isRefreshLoading: LoadingEvent = LoadingFlow()

    private var clubId = ""
    private var page = 1
    private var hasMoreData = true

    private val _items = MutableStateFlow<List<IDancer>>(emptyList())
    val items: StateFlow<List<IDancer>> = _items

    private val _isEmpty = MutableStateFlow(true)
    val isEmpty: StateFlow<Boolean> = _isEmpty

    fun updateClubId(value: String) {
        if (clubId == value) return
        clubId = value
        page = 1
        hasMoreData = true
        _items.value = emptyList()
    }

    fun onRefresh() {
        page = 1
        hasMoreData = true
        _items.value = emptyList()
        onFetch()
    }

    fun onFetch() {
        if (clubId.isBlank()) return
        if (isRefreshLoading.isLoading().value || customLoading.isLoading().value || !hasMoreData) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val rs = fetchDancerByClubCase.availableNow(clubId = clubId, page = page)
            _isEmpty.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) {
                hasMoreData = false
            } else {
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
