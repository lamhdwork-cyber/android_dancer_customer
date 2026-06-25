package com.kantek.dancer.booking.presentation.screen.faqs

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.FAQsThreadsApi
import com.kantek.dancer.booking.data.factory.FAQsThreadsFactory
import com.kantek.dancer.booking.app.AppScopes
import com.kantek.dancer.booking.domain.model.faqs.ILegalCategory
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import android.support.ui.extension.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import android.support.ui.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.FAQThreadsCategoryItem
import com.kantek.dancer.booking.presentation.widget.FQAsLoading
import android.support.ui.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun FAQsThreadsScreen(viewModel: FAQsThreadsVM = koinViewModel()) =
    ScopeProvider(AppScopes.FAQsThreads) {
        val appNavigator = use<AppNavigator>()

        LaunchedEffect(Unit) {
            viewModel.refreshFAQs()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            ActionBarBackAndTitleView(R.string.account_faq_threads) { appNavigator.back() }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White)
            ) {
                FAQsView(appNavigator, viewModel)
            }
        }
    }

@Composable
fun FAQsView(appNavigator: AppNavigator, viewModel: FAQsThreadsVM) {
    val faqsList by viewModel.categories.collectAsState()
    val isEmpty by viewModel.isEmptyFAQs.collectAsState()
    val isLoading by viewModel.loadingFAQs.isLoading().collectAsState()
    val isMoreLoading by viewModel.loadingMoreFAQs.isLoading().collectAsState()

    when {
        isLoading -> FQAsLoading()
        isEmpty -> {
            AppLazyColumn(
                modifier = Modifier.fillMaxSize(),
                items = emptyList<ILegalCategory>(),
                backgroundColor = Color.White,
                emptyHtmlRes = R.string.no_data_faqs,
                isEmpty = true,
            ) { _, _, _ -> }
        }
        faqsList.isNotEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .background(Color.White)
            ) {

                SpaceVertical(14.dp)
                Text(
                    text = stringResource(R.string.faqs_threads_title),
                    color = Color.Black,
                    fontSize = 16.sp
                )

                SpaceVertical(12.dp)

                AppLazyColumn(
                    items = faqsList,
                    keyItem = { it.id },
                    isLoading = isMoreLoading,
                    onLoadMore = { viewModel.fetchFAQs() },
                    emptyHtmlRes = R.string.no_data_faqs,
                    isEmpty = isEmpty,
                ) { item, _, _ ->
                    FAQThreadsCategoryItem(item) {
                        appNavigator.navigateQuestion(
                            item.id,
                            item.name
                        )
                    }
                }
            }
        }
    }
}

class FAQsThreadsVM(
    private val fetchFAQsPagingRepo: FetchFAQsThreadsPagingRepo
) : AppViewModel() {

    private var page = 1
    private var hasMoreData = true
    val loadingFAQs: LoadingEvent = LoadingFlow()
    val loadingMoreFAQs: LoadingEvent = LoadingFlow()
    private val _faqs = MutableStateFlow<List<ILegalCategory>>(emptyList())
    val categories: StateFlow<List<ILegalCategory>> = _faqs
    val isEmptyFAQs = MutableStateFlow(false)

    fun refreshFAQs() {
        page = 1
        hasMoreData = true
        isEmptyFAQs.value = false
        _faqs.value = emptyList()
        fetchFAQs()
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

class FetchFAQsThreadsPagingRepo(
    private val faqsAPI: FAQsThreadsApi,
    private val faqsFactory: FAQsThreadsFactory
) {
    suspend operator fun invoke(page: Int): List<ILegalCategory> {
        return faqsFactory.createCategories(faqsAPI.fetchCategoriesByPage(page).await().data)
    }
}
