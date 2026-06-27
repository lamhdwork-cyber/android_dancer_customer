package com.hdl.dancer.booking.presentation.screen.introduce

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.presentation.model.support.Scopes
import com.hdl.dancer.booking.domain.model.introduce.IIntroduce
import com.hdl.dancer.booking.presentation.extensions.ScopeProvider
import com.hdl.dancer.booking.presentation.theme.Colors
import com.hdl.dancer.booking.presentation.widget.AppButton
import com.hdl.dancer.booking.presentation.widget.AppNextButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroduceScreen(
    viewModel: IntroduceVM = koinViewModel(),
    onGetStarted: () -> Unit = {}
) = ScopeProvider(Scopes.Introduce) {
    val items by viewModel.items.collectAsState()
    val currentPage by viewModel.page.collectAsState()
    val pagerState = rememberPagerState(initialPage = currentPage) { items.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentPage) {
        if (items.isNotEmpty() && pagerState.currentPage != currentPage) {
            pagerState.animateScrollToPage(currentPage)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updatePage(pagerState.currentPage)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val item = items.getOrNull(page) ?: return@HorizontalPager
            IntroducePageItem(item, page)
        }

//        if (!viewModel.isLastPage()) {
            Text(
                text = stringResource(R.string.all_skip),
                color = Colors.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 40.dp, end = 22.dp)
                    .clickable { onGetStarted() }
            )
//        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                items.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .height(8.dp)
                            .size(if (index == currentPage) 34.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentPage) Colors.PinkFF00F5
                                else Colors.PinkFF00F5.copy(alpha = 0.35f)
                            )
                    )
                }
            }

            if (viewModel.isLastPage()) {
                AppButton(
                    nameRes = R.string.all_get_started,
                    modifier = Modifier
                        .padding(top = 26.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp)),
                ) { onGetStarted() }
            } else {
                AppNextButton(
                    nameRes = R.string.all_next,
                    modifier = Modifier
                        .padding(top = 26.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp)),
                ) {
                    val nextPage = viewModel.nextPage()
                    if (nextPage != pagerState.currentPage) {
                        scope.launch { pagerState.animateScrollToPage(nextPage) }
                    }
                }
            }
        }
    }
}

@Composable
private fun IntroducePageItem(
    item: IIntroduce,
    page: Int
) {
    val gradientColors = when (page) {
        0 -> listOf(
            Colors.Dark210514,
            Colors.Dark3C0B2A,
            Colors.Dark1D0512
        )
        1 -> listOf(
            Colors.Dark24031B,
            Colors.Dark3A0F2D,
            Colors.Dark11020E
        )
        else -> listOf(
            Colors.Dark111111,
            Colors.Dark2A1323,
            Colors.Dark0C0B0E
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        if (item.backgroundRes != 0) {
            Image(
                painter = painterResource(item.backgroundRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(Colors.PinkFF00F5.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(item.iconRes),
                    contentDescription = null,
                    tint = Colors.PinkFF00F5,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = stringResource(item.titleRes),
                color = Colors.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 18.dp)
            )
            Text(
                text = stringResource(item.descriptionRes),
                color = Colors.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
