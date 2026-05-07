package com.kantek.dancer.booking.domain.model.response

import com.google.gson.annotations.SerializedName

data class NotificationDTO(
    val id: String?,
    val userId: String?,
    val type: String?,
    val title: String?,
    val message: String?,
    @SerializedName(value = "isRead", alternate = ["is_read"])
    val isRead: Boolean?,
    val data: NotificationDataDTO?,
    @SerializedName(value = "createdAt", alternate = ["created_at"])
    val createdAt: String?,
    @SerializedName(value = "updatedAt", alternate = ["updated_at"])
    val updatedAt: String?,
    @SerializedName(value = "avatar", alternate = ["avatar_url"])
    val avatar: String? = null,
    @SerializedName(value = "isContactRequest", alternate = ["is_contact_request"])
    val isContactRequest: Boolean? = null
)

data class NotificationDataDTO(
    @SerializedName(value = "bookingId", alternate = ["booking_id"])
    val bookingId: String?
)