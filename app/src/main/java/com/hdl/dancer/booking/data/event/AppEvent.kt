package com.hdl.dancer.booking.data.event

import com.hdl.dancer.booking.data.model.firebase.FireBaseCloudMessage
import kotlinx.coroutines.flow.MutableStateFlow

class AppEvent {
    val onRefreshMyBooking = MutableStateFlow(false)
    val onRefreshNotification = MutableStateFlow(false)
    val onPushBookingCompleted = MutableStateFlow<FireBaseCloudMessage?>(null)
}