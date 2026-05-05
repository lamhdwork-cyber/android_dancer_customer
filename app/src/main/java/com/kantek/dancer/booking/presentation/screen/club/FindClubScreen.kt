package com.kantek.dancer.booking.presentation.screen.club

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.ui.search.IClub
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.viewmodel.FindClubVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun FindClubScreen(viewModel: FindClubVM = koinViewModel()) = ScopeProvider(Scopes.Search) {
    val appNavigator = use<AppNavigator>()
    val clubs by viewModel.items.collectAsState()
    val isEmpty by viewModel.isEmpty.collectAsState()
    val isLoading by viewModel.customLoading.isLoading().collectAsState()
    val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()

    LaunchedEffect(Unit) { viewModel.onFetch() }

    Column(
        modifier = Modifier
            .background(Colors.DarkFF0A050A)
    ) {
        ActionBarMainView(R.string.top_bar_select_club)
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CurrentLocationCard()
            NearbyHeader()
        }
        AppLazyColumn(
            items = clubs,
            keyItem = { it.id },
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.onRefresh() },
            onLoadMore = { viewModel.onFetch() }
        ) { club, _, _ ->
            ClubItemCard(
                club = club,
                onSelectClub = { appNavigator.navigateDancerList(club.id) }
            )
        }
        if (isEmpty) NoDataView(htmlRes = R.string.no_data_notifications)
    }
}

@Composable
private fun CurrentLocationCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Colors.Dark120812)
            .border(1.dp, Colors.White1AFFFFFF, RoundedCornerShape(18.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Colors.Primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Colors.White
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.club_current_location).uppercase(),
                color = Colors.Primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
            Text(
                text = stringResource(R.string.club_current_location_placeholder),
                color = Colors.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Colors.Dark64748B
        )
    }
}

@Composable
private fun NearbyHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.club_nearby_clubs),
            color = Colors.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(R.string.club_view_map),
            color = Colors.Primary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable {}
        )
    }
}

@Composable
private fun ClubItemCard(
    club: IClub,
    onSelectClub: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Dark120812),
        border = androidx.compose.foundation.BorderStroke(1.dp, Colors.White1AFFFFFF)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(club.coverImage)
                    .crossfade(true)
                    .build(),
                contentDescription = club.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Colors.White33FFFFFF, Colors.DarkAA1A0D18, Colors.Dark120812)
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .clip(CircleShape)
                    .background(Colors.Dark5E453E)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(
                    text = stringResource(
                        if (club.isOpen) R.string.club_open_now else R.string.club_closed
                    ).uppercase(),
                    color = Colors.Primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 0.7.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = club.name,
                color = Colors.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = club.distance,
                    color = Colors.Gray9CA3AF,
                    fontSize = 13.sp
                )
                Text(text = "  •  ", color = Colors.Dark64748B, fontSize = 13.sp)
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Colors.GoldFFD700,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${club.rating}",
                    color = Colors.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "  •  ", color = Colors.Dark64748B, fontSize = 13.sp)
                Text(
                    text = stringResource(R.string.club_open_hours_format, club.openTime, club.closeTime),
                    color = Colors.Gray9CA3AF,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            AppButton(
                nameRes = R.string.club_select_this_club,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(28.dp)),
                textColor = Colors.White,
                onClick = onSelectClub
            )
        }
    }
}