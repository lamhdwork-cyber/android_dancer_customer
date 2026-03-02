package com.kantek.dancer.booking.presentation.viewmodel

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.LawyerApi
import com.kantek.dancer.booking.domain.factory.LawyerFactory
import com.kantek.dancer.booking.domain.model.ui.review.IReview
import com.kantek.dancer.booking.presentation.extensions.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewVM(
    private val fetchReviewByPageRepo: FetchReviewByPageRepo
) : AppViewModel() {

    private val _items = MutableStateFlow<List<IReview>>(emptyList())
    val items: StateFlow<List<IReview>> = _items

    private val _isEmpty = MutableStateFlow(true)
    val isEmpty: StateFlow<Boolean> = _isEmpty

    val customLoading: LoadingEvent = LoadingFlow()
    val isRefreshLoading: LoadingEvent = LoadingFlow()

    private var page = 1
    private var hasMoreData = true
    private var id = -1

    fun setData(lawyerID: Int) = launch(loading, error) {
        id = lawyerID
        onRefresh()
    }

    fun onRefresh() {
        page = 1
        hasMoreData = true
        _items.value = emptyList()
        onFetch()
    }


    fun onFetch() {
        if (isRefreshLoading.isLoading().value
            || customLoading.isLoading().value
            || !hasMoreData
        ) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val rs = fetchReviewByPageRepo(id, page)
            _isEmpty.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) hasMoreData = false
            else {
                if (rs.size < AppConfig.PER_PAGE) hasMoreData = false
                val current = _items.value
                val newItems = rs.filterNot { newItem ->
                    current.any { it.id == newItem.id }
                }
                _items.value = current + newItems
                page++
            }
        }
    }
}

class FetchReviewByPageRepo(
    private val lawyerApi: LawyerApi,
    private val lawyerFactory: LawyerFactory
) {
    suspend operator fun invoke(
        lawyerID: Int,
        page: Int
    ): List<IReview> {
        return lawyerFactory.createReviews(
            lawyerApi.reviewByPage(lawyerID, page).awaitNullable()?.data
        )
    }

}
