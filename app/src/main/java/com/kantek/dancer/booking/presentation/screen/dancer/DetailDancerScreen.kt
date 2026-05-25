package com.kantek.dancer.booking.presentation.screen.dancer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.presentation.model.support.Scopes
import com.kantek.dancer.booking.domain.model.search.IDancerDetail
import com.kantek.dancer.booking.domain.usecase.FetchDancerDetailCase
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.sheetTopSideBorder
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DetailDancerScreen(
    dancerId: String,
    hasShowButtons: Boolean = true,
    viewModel: DetailDancerVM = koinViewModel()
) = ScopeProvider(Scopes.Dancer) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val navBarBottomDp = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val appNavigator = use<AppNavigator>()
    val detail by viewModel.details.collectAsState()
    val hasLoaded by viewModel.hasLoaded.collectAsState()

    LaunchedEffect(dancerId) {
        viewModel.setData(dancerId)
    }

    if (!hasLoaded || detail == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Dark120812),
            contentAlignment = Alignment.Center
        ) {
            NoDataView(htmlRes = R.string.no_data)
        }
        return@ScopeProvider
    }

    val dancer = detail!!
    val photos = dancer.gallery.ifEmpty { listOf(dancer.avatar) }
    val ratingBadgeText = dancer.rating.toFloatOrNull()
        ?.let { "Rating: %.2f".format(Locale.US, it) }
        ?: "Rating: ${dancer.rating}"

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        val heroHeight = maxHeight * 0.6f
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(photos.firstOrNull())
                .crossfade(true)
                .build(),
            contentDescription = dancer.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(heroHeight)
                .clickable { appNavigator.navigatePhotoViewer(photos) },
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heroHeight)
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
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconButton(Icons.Outlined.ArrowBackIosNew) { appNavigator.back() }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                CircleIconButton(Icons.Outlined.Favorite) {}
//                CircleIconButton(Icons.Outlined.Share) {}
//            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = maxHeight * 0.46f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .then(
                    if (hasShowButtons) {
                        Modifier.border(
                            width = 1.dp,
                            color = Colors.Pink33F425F4,
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                    } else {
                        Modifier.sheetTopSideBorder(color = Colors.Pink33F425F4)
                    }
                )
                .background(Colors.OverlayCC120812)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = if (hasShowButtons) 184.dp else 32.dp + navBarBottomDp
                ).navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .width(44.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Colors.White33FFFFFF)
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = dancer.name,
                        color = Colors.White,
                        fontSize = 34.sp,
                        lineHeight = 42.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier.padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Colors.Primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = stringResource(R.string.dancer_detail_premium_performer),
                            color = Colors.Primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                Text(
                    text = ratingBadgeText,
                    color = Colors.Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Colors.Pink26F425F4)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailTag(
                    title = stringResource(R.string.dancer_detail_age),
                    value = stringResource(R.string.dancer_detail_age_format, dancer.age),
                    modifier = Modifier.weight(1f)
                )
                DetailTag(
                    title = stringResource(R.string.dancer_detail_style),
                    value = dancer.danceStyle.ifBlank { stringResource(R.string.dancer_style_unknown) },
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = stringResource(R.string.dancer_detail_about),
                color = Colors.GrayCBD5E1,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = dancer.bio,
                color = Colors.White,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

        }

        if (hasShowButtons) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Colors.Overlay99120812,
                                Colors.Overlay99120812,
                                Colors.OverlayCC120812
                            )
                        )
                    )
                    .padding(16.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        appNavigator.navigateBooking(
                            dancerId = dancer.id,
                            hasNow = true,
                            clubId = dancer.clubId
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colors.Primary,
                        contentColor = Colors.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.EventAvailable,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 20.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = stringResource(R.string.dancer_detail_book_now),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Button(
                    onClick = {
                        appNavigator.navigateBooking(
                            dancerId = dancer.id,
                            hasNow = false,
                            clubId = dancer.clubId
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colors.Dark120812,
                        contentColor = Colors.Primary
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Colors.Pink66F425F4),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 20.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = stringResource(R.string.dancer_detail_book_late),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CircleIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Colors.Dark660F172A)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Colors.White,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun DetailTag(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Colors.White1AFFFFFF)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(text = title, color = Colors.GrayCBD5E1, fontSize = 10.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = Colors.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

class DetailDancerVM(
    private val fetchDancerDetailCase: FetchDancerDetailCase,
) : AppViewModel() {
    val details = MutableStateFlow<IDancerDetail?>(null)
    val hasLoaded = MutableStateFlow(false)

    fun setData(dancerId: String) = launch(loading, error) {
        hasLoaded.emit(false)
        if (dancerId.isBlank()) {
            details.emit(null)
            hasLoaded.emit(true)
            return@launch
        }
        details.emit(fetchDancerDetailCase(dancerId))
        hasLoaded.emit(true)
    }
}

