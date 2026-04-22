package com.kantek.dancer.booking.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView

private data class ClubUiModel(
    val name: String,
    val distance: String,
    val rating: String,
    val openHours: String,
    val badge: String,
    val badgeColor: Color
)

@Composable
fun FindClubScreen() = ScopeProvider(Scopes.Search) {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val clubs = listOf(
        ClubUiModel(
            name = "Neon Velvet",
            distance = "0.5 miles",
            rating = "4.8",
            openHours = "Open: 8:00 PM - 4:00 AM",
            badge = "Open now",
            badgeColor = Colors.Primary
        ),
        ClubUiModel(
            name = "The Onyx Lounge",
            distance = "1.2 miles",
            rating = "4.6",
            openHours = "Open: 9:00 PM - 5:00 AM",
            badge = "Popular",
            badgeColor = Colors.Orange251
        ),
        ClubUiModel(
            name = "Starlight Atrium",
            distance = "2.1 miles",
            rating = "4.9",
            openHours = "Open: 8:00 PM - 4:00 AM",
            badge = "New",
            badgeColor = Colors.Blue75
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark0A050A)
    ) {
        ActionBarMainView(R.string.top_bar_select_club)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CurrentLocationCard()
            NearbyHeader()
            clubs.forEachIndexed { index, club ->
                ClubItemCard(
                    club = club,
                    onSelectClub = {
                        if (index == 1) {
                            appNavigator.navigateQuickRequest()
                        } else {
                            appNavigator.navigateLawyerList()
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
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
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = "Current location",
                color = Colors.Gray9CA3AF,
                fontSize = 11.sp,
                letterSpacing = 0.8.sp
            )
            Text(
                text = "123 Neon District, City",
                color = Color.White,
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
            text = "Nearby clubs",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "View map",
            color = Colors.Primary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable {}
        )
    }
}

@Composable
private fun ClubItemCard(
    club: ClubUiModel,
    onSelectClub: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Dark120812),
        border = androidx.compose.foundation.BorderStroke(1.dp, Colors.White1AFFFFFF)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Colors.Dark3C0B2A, Colors.Dark120812)
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .clip(CircleShape)
                    .background(club.badgeColor.copy(alpha = 0.2f))
                    .border(1.dp, club.badgeColor.copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(
                    text = club.badge.uppercase(),
                    color = club.badgeColor,
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
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = club.distance, color = Colors.Gray9CA3AF, fontSize = 13.sp)
                Text(text = "   •   ", color = Colors.Dark64748B, fontSize = 13.sp)
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Colors.GoldFFD700,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = " ${club.rating}",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
                Text(text = "   •   ", color = Colors.Dark64748B, fontSize = 13.sp)
                Text(
                    text = club.openHours,
                    color = Colors.Gray9CA3AF,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(
                onClick = onSelectClub,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.all_select_dancer),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}