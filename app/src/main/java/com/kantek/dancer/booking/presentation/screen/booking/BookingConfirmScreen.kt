package com.kantek.dancer.booking.presentation.screen.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
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
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton

@Composable
fun BookingConfirmScreen(
    dancerIds: String = "",
    roomId: String = ""
) = ScopeProvider(Scopes.BookingConfirm) {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val dancerIdList = dancerIds.split(",").filter { it.isNotBlank() }

    val summary = BookingConfirmUi(
        clubName = stringResource(R.string.booking_confirm_mock_club_name),
        district = stringResource(R.string.booking_confirm_mock_district),
        dateText = stringResource(R.string.booking_confirm_mock_date),
        timeText = stringResource(R.string.booking_confirm_mock_time),
        roomName = stringResource(R.string.booking_room_suite_label),
        songs = 12,
        guests = 4,
        totalText = stringResource(R.string.booking_confirm_mock_total),
        dancers = if (dancerIdList.isEmpty()) {
            listOf("Elena", "Jade", "Sasha")
        } else {
            dancerIdList.mapIndexed { index, _ ->
                "Dancer ${index + 1}"
            }
        },
        roomId = roomId
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.DarkFF0A050A)
    ) {
        ActionBarBackAndTitleView(R.string.booking_confirm_title) { appNavigator.back() }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ClubCard(summary = summary)
            DateTimeRow(summary = summary)
            RoomCard(summary = summary)
            DancersCard(summary = summary)
            AmountCard(summary = summary)
            Spacer(modifier = Modifier.height(100.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.OverlayCC120812)
                .padding(16.dp)
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
                onClick = {}
            )
        }
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
            model = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?q=80&w=1200&auto=format&fit=crop",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(summary.clubName, color = Colors.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text(summary.district, color = Colors.Primary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            Text(
                text = stringResource(R.string.booking_premium_venue),
                color = Colors.Primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Colors.Pink26F425F4)
                    .border(1.dp, Colors.Pink33F425F4, RoundedCornerShape(999.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
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
            Icon(icon, contentDescription = null, tint = Colors.Primary, modifier = Modifier.size(18.dp))
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
                text = if (summary.roomId.isBlank()) summary.roomName else "${summary.roomName} (${summary.roomId})",
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
            summary.dancers.forEach { name ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .border(2.dp, Colors.Primary, CircleShape)
                            .padding(2.dp)
                    ) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=400&auto=format&fit=crop",
                            contentDescription = name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(name, color = Colors.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            Text(stringResource(R.string.booking_songs_title), color = Colors.GrayCBD5E1, fontSize = 14.sp)
            Text(summary.songs.toString(), color = Colors.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.booking_guests_title), color = Colors.GrayCBD5E1, fontSize = 14.sp)
            Text(summary.guests.toString(), color = Colors.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
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
                Text(summary.totalText, color = Colors.Primary, fontSize = 32.sp, fontWeight = FontWeight.Black)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.booking_base_fee_295), color = Colors.Gray9CA3AF, fontSize = 11.sp)
            Text(stringResource(R.string.booking_addons_fee_zero), color = Colors.Gray9CA3AF, fontSize = 11.sp)
        }
    }
}

private data class BookingConfirmUi(
    val clubName: String,
    val district: String,
    val dateText: String,
    val timeText: String,
    val roomName: String,
    val songs: Int,
    val guests: Int,
    val totalText: String,
    val dancers: List<String>,
    val roomId: String
)

