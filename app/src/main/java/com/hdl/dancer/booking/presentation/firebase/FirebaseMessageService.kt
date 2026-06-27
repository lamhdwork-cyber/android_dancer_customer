package com.hdl.dancer.booking.presentation.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hdl.dancer.booking.app.AppNotifications
import com.hdl.dancer.booking.data.event.AppEvent
import com.hdl.dancer.booking.data.local.UserLocalSource
import com.hdl.dancer.booking.data.extension.toJson
import com.hdl.dancer.booking.data.extension.toObject
import com.hdl.dancer.booking.data.model.firebase.FireBaseCloudMessage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.android.ext.android.inject

class FirebaseMessageService : FirebaseMessagingService() {

    private val TAG: String = FirebaseMessageService::class.java.simpleName

    private val appEvent: AppEvent by inject()
    private val userLocalSource: UserLocalSource by inject()
    private val appNotification: AppNotifications by inject()

    @SuppressLint("LongLogTag", "SuspiciousIndentation")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            Log.e(TAG, remoteMessage.data.toJson())
            val params = remoteMessage.data
            val obj = JSONObject(params as Map<*, *>)
            val cloudMessage = obj.toString().toObject<FireBaseCloudMessage>()
            if (cloudMessage.bookingId.isNotEmpty()) {
                appNotification.bookingNotify(cloudMessage)
                appEvent.apply {
                    onRefreshMyBooking.value = true
                    onRefreshNotification.value = true
                }
            } else {
                appNotification.defaultNotify(cloudMessage)
                appEvent.onRefreshNotification.value = true
            }
//            when (cloudMessage.type) {
//                AppConfig.NotificationType.Push.CONTACT_REQUEST_COMPLETED -> {
//                    Log.e(TAG, "CONTACT_REQUEST_COMPLETED")
//                    appNotification.bookingNotify(cloudMessage)
//                    appEvent.apply {
//                        onPushBookingCompleted.value = cloudMessage
//                        onRefreshMyBooking.value = true
//                        onRefreshNotification.value = true
//                    }
//                }
//                AppConfig.NotificationType.Push.CHAT -> {
//                    Log.e(TAG, "CHAT")
//                    if (chatRepo.getChatRoomCurrent() != cloudMessage.contact_request_id) {
//                        appNotification.chatNotify(cloudMessage)
//                    }
//                }
//
//                else -> {
//                    Log.e(TAG, "DEFAULT")
//                    appNotification.defaultNotify(cloudMessage)
//                }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("LongLogTag")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        GlobalScope.launch(Dispatchers.IO) {
            userLocalSource.saveTokenPush(p0)
        }
        Log.e(TAG, "onNewToken: $p0 ")
    }
}