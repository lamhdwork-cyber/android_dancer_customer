package com.kantek.dancer.booking.domain.model.ui.conversation

import com.kantek.dancer.booking.domain.model.response.conversation.MessageDTO

data class Message(
    val id: Long = 0L,
    val localId: String = "",
    val message: String = "",
    val createdBy: Long = 0,
    val createdAt: String = "",
    val isRead: Boolean = false,
    val isLike: Boolean = false,
    val isUnsent: Boolean = false,
    val name: String = "",
    val avatar: String = "",
    val isShowTime: Boolean = false,
    val isShowAvatar: Boolean = true,
    val isMe: Boolean = false,
    val messageUnsent: String = "",
    val titleLink: String? = "",
    val descriptionLink: String? = "",
    val photos: List<String>? = listOf(),
    val isSending: Boolean = false,
    val owner: MessageDTO? = null
)