package com.kantek.dancer.booking.presentation.screen.home

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import android.support.core.extensions.safe
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.ConfigApi
import com.kantek.dancer.booking.data.repo.FetchAllBannerRepo
import com.kantek.dancer.booking.domain.factory.ConfigFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.config.IFAQs
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppImage
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.AppNextButton
import com.kantek.dancer.booking.presentation.widget.FQAsLoading
import com.kantek.dancer.booking.presentation.widget.HomeBannerLoading
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel

@Composable
fun FAQsScreen(viewModel: HomeVM = koinViewModel()) = ScopeProvider {
    val appNavigator = use<AppNavigator>(Scopes.Home)

    val user by viewModel.userLive.collectAsState()
    val banner by viewModel.banner.collectAsState(null)
    val isLoadingBanner by viewModel.loadingBanner.isLoading().collectAsState()
    val refreshData by remember { mutableStateOf(viewModel.getCurrentLanguage()) }

    LaunchedEffect(refreshData) {
        Log.e("Language: ", refreshData)
        viewModel.fetchAll()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ActionBarMainView(R.string.nav_home)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            if (!isLoadingBanner && banner != null) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.2f), Color.Transparent)
                        )
                    )
            ) {
                AppImage(
                    banner?.dataURL,
                    placeholderRes = R.drawable.img_home_banner,
                    errorRes = R.drawable.img_home_banner,
                    isShowLoading = false,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (!user?.fullName.isNullOrEmpty())
                            stringResource(R.string.hello_s, user?.fullName.safe())
                        else "",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    SpaceVertical(8.dp)

                    Text(
//                        text = banner?.title.safe(),
                        text = stringResource(R.string.home_need_legal_help),
                        color = Color.White,
                        fontSize = 32.sp,
                        lineHeight = 35.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    SpaceVertical(16.dp)

                    AppNextButton(R.string.all_request_dancer) {
                        appNavigator.navigateSearch()
                    }
                }
            }
            if (isLoadingBanner) HomeBannerLoading()

            FAQsView(viewModel)
        }
    }
}

@Composable
fun FAQsView(viewModel: HomeVM) {
    val faqsList by viewModel.faqs.collectAsState()
    val isEmpty by viewModel.isEmptyFAQs.collectAsState()
    val isLoading by viewModel.loadingFAQs.isLoading().collectAsState()
    val isMoreLoading by viewModel.loadingMoreFAQs.isLoading().collectAsState()
    val expandedStates = remember(faqsList) {
        faqsList.map { mutableStateOf(false) }
    }

    when {
        isLoading -> FQAsLoading()
        isEmpty -> NoDataView(htmlRes = R.string.no_data_faqs)
        faqsList.isNotEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .background(Color.White)
            ) {

                SpaceVertical(20.dp)
                Text(
                    text = stringResource(R.string.faqs_title),
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )

                SpaceVertical(16.dp)

                AppLazyColumn(
                    items = faqsList,
                    keyItem = { it.id },
                    isLoading = isMoreLoading,
                    onLoadMore = { viewModel.fetchFAQs() }) { item, index, _ ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .padding(bottom = 8.dp, top = 8.dp, start = 2.dp, end = 2.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(Colors.Gray238)
                                .clickable {
                                    expandedStates[index].value = !expandedStates[index].value
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.question,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.Transparent)
                                        .weight(1f)
                                )

                                SpaceHorizontal(10.dp)

                                Image(
                                    painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                                    contentDescription = "Expand/Collapse",
                                    modifier = Modifier
                                        .graphicsLayer(
                                            rotationZ = if (expandedStates[index].value) 180f else 0f
                                        )
                                )
                            }
                        }

                        AnimatedVisibility(visible = expandedStates[index].value) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                                    .background(Color.White)
                            ) {
                                Text(
                                    text = item.answer,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 16.dp, 16.dp, 16.dp)
                                        .background(Color.Transparent)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

class HomeVM(
    private val fetchFAQsPagingRepo: FetchFAQsPagingRepo,
    private val fetchAllBannerRepo: FetchAllBannerRepo
) : AppViewModel() {
    val banner = fetchAllBannerRepo.results.map { it.firstOrNull() }
    val loadingBanner: LoadingEvent = LoadingFlow()

    //FAQs
    private var page = 1
    private var hasMoreData = true
    val loadingFAQs: LoadingEvent = LoadingFlow()
    val loadingMoreFAQs: LoadingEvent = LoadingFlow()
    private val _faqs = MutableStateFlow<List<IFAQs>>(emptyList())
    val faqs: StateFlow<List<IFAQs>> = _faqs
    val isEmptyFAQs = MutableStateFlow(false)

    fun fetchAll() {
        if (currentLanguageBackup != getCurrentLanguage()) {
            currentLanguageBackup = getCurrentLanguage()
            fetchBanner()
            refreshFAQs()
        }
    }

    private fun refreshFAQs() {
        page = 1
        hasMoreData = true
        isEmptyFAQs.value = false
        _faqs.value = emptyList()
        fetchFAQs()
    }

    private fun fetchBanner() = launch(loadingBanner, error) {
        fetchAllBannerRepo()
    }

    fun fetchFAQs() {
        if ((loadingFAQs.isLoading().value && page == 1)
            || loadingMoreFAQs.isLoading().value
            || !hasMoreData
        ) return
        launch(if (page == 1) loadingFAQs else loadingMoreFAQs, error) {
            val rs = fetchFAQsPagingRepo(page)
            isEmptyFAQs.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) {
                hasMoreData = false
            } else {
                val current = _faqs.value
                val newItems = rs.filterNot { newItem ->
                    current.any { it.id == newItem.id }
                }
                _faqs.value = current + newItems
                page++
            }
        }
    }
}

class FetchFAQsPagingRepo(
    private val configApi: ConfigApi, private val configFactory: ConfigFactory
) {
    suspend operator fun invoke(page: Int): List<IFAQs> {
        return configFactory.createFAQsList(configApi.faqs(page).await().data)
    }
}
