package com.kantek.dancer.booking.presentation.screen.introduce

import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.domain.model.introduce.IIntroduce
import android.support.ui.extension.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntroduceVM(
    private val fetchIntroduceRepo: FetchIntroduceRepo
) : AppViewModel() {
    private val _items = MutableStateFlow<List<IIntroduce>>(emptyList())
    val items: StateFlow<List<IIntroduce>> = _items.asStateFlow()

    private val _page = MutableStateFlow(0)
    val page: StateFlow<Int> = _page.asStateFlow()

    init {
        fetchIntroduce()
    }

    private fun fetchIntroduce() = launch(loading, error) {
        _items.value = fetchIntroduceRepo()
    }

    fun updatePage(it: Int) {
        if (_page.value != it) {
            _page.value = it
        }
    }

    fun nextPage(): Int {
        val maxPage = (_items.value.size - 1).coerceAtLeast(0)
        val nextPage = (_page.value + 1).coerceAtMost(maxPage)
        _page.value = nextPage
        return nextPage
    }

    fun isLastPage(): Boolean {
        return _items.value.isNotEmpty() && _page.value == _items.value.lastIndex
    }
}
