package com.kantek.dancer.booking.data.event

import com.kantek.dancer.booking.domain.model.firebase.FireBaseCloudMessage
import kotlinx.coroutines.flow.MutableStateFlow

class AppEvent {
    val onRefreshMyCases = MutableStateFlow(false)
    val onRefreshNotification = MutableStateFlow(false)
    val onPushBookingCompleted = MutableStateFlow<FireBaseCloudMessage?>(null)
}