package com.kantek.dancer.booking.domain.model.response.conversation

data class TypingDTO(
    val avatar_url: String?,
    val contact_request_id: String?,
    val message: String?,
    val typing: Boolean?,
    val user_id: String?
)