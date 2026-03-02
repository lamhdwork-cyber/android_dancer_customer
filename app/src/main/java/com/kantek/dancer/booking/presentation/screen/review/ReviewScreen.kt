package com.kantek.dancer.booking.presentation.screen.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.viewmodel.ReviewVM
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.ReviewItem
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReviewScreen(
    reviewTotal: String = "",
    lawyerID: Int = -1,
    viewModel: ReviewVM = koinViewModel()
) = ScopeProvider(Scopes.Lawyer) {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val reviews by viewModel.items.collectAsState()
    val isEmpty by viewModel.isEmpty.collectAsState()
    val isLoading by viewModel.customLoading.isLoading().collectAsState()
    val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setData(lawyerID)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ActionBarBackAndTitleView(R.string.all_reviews) { appNavigator.back() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.all_reviews),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start
                        )
                        SpaceHorizontal(10.dp)
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = reviewTotal,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Box(modifier = Modifier.padding(top = 2.dp)) {
                        AppLazyColumn(
                            items = reviews,
                            keyItem = { it.id },
                            contentPadding = PaddingValues(
                                horizontal = 14.dp,
                                vertical = 14.dp
                            ),
                            backgroundColor = Color.White,
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            isLoading = isLoading,
                            isRefreshing = isRefreshing,
                            onRefresh = { viewModel.onRefresh() },
                            onLoadMore = { viewModel.onFetch() }
                        ) { item, _, _ ->
                            ReviewItem(item)
                        }
                        if (isEmpty)
                            NoDataView(htmlRes = R.string.no_data_reviews)
                    }
                }
            }
        }
    }
}