package com.kantek.dancer.booking.domain.model.response

data class NotificationDTO(
    val avatar_url: String?,
    val created_at: String?,
    val data_id: Int?,
    val id: Long?,
    val is_read: Boolean?,
    val message: String?,
    val meta_data: String?,
    val sender_id: Any?,
    val title: String?,
    val type: Int?,
    val type_description: String?,
    val user_id: Int?,
    val is_contact_request: Boolean?
)