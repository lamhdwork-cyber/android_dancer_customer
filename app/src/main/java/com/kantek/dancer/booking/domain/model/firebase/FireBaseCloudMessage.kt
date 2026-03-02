package com.kantek.dancer.booking.domain.model.firebase
import com.google.gson.annotations.SerializedName
import com.kantek.dancer.booking.app.AppConfig

data class
FireBaseCloudMessage(
    val body: String?,
    val user_id: Long?,
    val contact_request_id: Int,
    @SerializedName("data_id")
    val data_id: Int,
    val image: String?,
    val title: String?,
    val type: Int = AppConfig.NotificationType.Push.NONE,
    val created_at: String,
    val room_id: Int
)