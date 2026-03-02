package com.kantek.dancer.booking.domain.model.response.conversation

data class ImageDTO(
    val chat_id: Int?=0,
    val created_at: String?="",
    val id: Int?=0,
    val mime_type: Any?="",
    val source: String?="",
    val source_url: String?="",
    val updated_at: String?=""
)