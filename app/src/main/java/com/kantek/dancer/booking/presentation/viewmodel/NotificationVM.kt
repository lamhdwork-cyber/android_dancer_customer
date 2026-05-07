package com.kantek.dancer.booking.presentation.viewmodel

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.domain.model.ui.user.INotification
import com.kantek.dancer.booking.domain.usecase.NotificationUseCase
import com.kantek.dancer.booking.presentation.extensions.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationVM(
    private val notificationUseCase: NotificationUseCase
) : AppViewModel() {

    private val _items = MutableStateFlow<List<INotification>>(emptyList())
    val items: StateFlow<List<INotification>> = _items

    private val _isEmpty = MutableStateFlow(true)
    val isEmpty: StateFlow<Boolean> = _isEmpty

    val customLoading: LoadingEvent = LoadingFlow()
    val isRefreshLoading: LoadingEvent = LoadingFlow()

    private var page = 1
    private var hasMoreData = true


    fun onChangeLanguage() {
        if (currentLanguageBackup != getCurrentLanguage()) {
            currentLanguageBackup = getCurrentLanguage()
            onRefresh()
        }
    }

    fun onChangeUser() {
        if (currentUserBackup != getCurrentUser()) {
            currentUserBackup = getCurrentUser()
            onRefresh()
        }
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
            || userLive.value == null
        ) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val rs = notificationUseCase(page)
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

    fun readAll() = launch(loading, error) {
        notificationUseCase.readAll()
        _items.value = _items.value.map { item ->
            object : INotification by item {
                override val hasUnRead: Boolean
                    get() = false
            }
        }
    }

    /**
     * Marks notification read on server; on success updates local list and runs [onSuccessNavigate]
     * with [INotification.bookingID] when it is non-blank.
     */
    fun onNotificationItemClick(
        item: INotification,
        onSuccessNavigate: (bookingId: String) -> Unit
    ) = launch(loading, error) {
        if (item.id.isBlank()) return@launch
        if (item.hasUnRead)
            notificationUseCase.readById(item.id)
        markNotificationReadLocally(item.id)
        if (item.bookingID.isNotBlank()) {
            onSuccessNavigate(item.bookingID)
        }
    }

    private fun markNotificationReadLocally(notificationId: String) {
        _items.value = _items.value.map { existing ->
            if (existing.id != notificationId) existing
            else object : INotification by existing {
                override val hasUnRead: Boolean
                    get() = false
            }
        }
    }
}