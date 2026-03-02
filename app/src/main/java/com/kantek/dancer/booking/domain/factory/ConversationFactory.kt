package com.kantek.dancer.booking.domain.factory

import android.net.Uri
import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.formatter.TimeFormatter
import com.kantek.dancer.booking.domain.model.response.conversation.MessageDTO
import com.kantek.dancer.booking.domain.model.ui.conversation.Message
import org.json.JSONArray
import java.util.UUID

class ConversationFactory(
    private val timeFormatter: TimeFormatter,
    private val textFormatter: TextFormatter
) {

    fun createMessage(
        it: MessageDTO,
        nextMessage: MessageDTO? = null
    ): Message {
        val createdBy = it.sender?.id.safe()
        val isShowTime = timeFormatter.checkShowTimeMessage(it.created_at, nextMessage?.created_at)
        return Message(
            id = it.id.safe(),
            owner = it,
            createdBy = createdBy,
            localId = it.local_id ?: UUID.randomUUID().toString(),
            message = textFormatter.decodeEmoji(it.message).safe(),
            name = it.sender?.name.safe(),
            avatar = it.sender?.avatar_url.safe(),
            isShowTime = timeFormatter.checkShowTimeMessage(it.created_at, nextMessage?.created_at),
            isMe = it.sender?.is_admin != 1,
            isShowAvatar = (createdBy != nextMessage?.sender?.id) || isShowTime,
            photos = it.images?.map { it.source_url.safe() },
            createdAt = if (isShowTime && !it.created_at.isNullOrEmpty()) timeFormatter.convertToMessage(
                it.created_at,
                nextMessage?.created_at
            ) else ""
        )
    }

    private fun getPhotos(image: String?): List<String> {
        if (image.isNullOrEmpty()) return listOf()
        if (image == "null") return listOf()
        val jsonArray = JSONArray(image)
        return List(jsonArray.length()) { i -> jsonArray.getString(i) }
    }

    private fun getNextMessage(messages: List<MessageDTO>, index: Int): MessageDTO? {
        if (messages.size - 1 == index) return null
        return messages[index + 1]
    }

    fun createMessageList(it: List<MessageDTO>?): List<Message> {
        val items = mutableListOf<Message>()
        it?.forEachIndexed { index, messageDTO ->
            val item =
                createMessage(
                    messageDTO,
                    getNextMessage(it, index)
                )
            items.add(item)
        }
        return items
    }

    fun encodeEmoji(msg: String): String {
        return textFormatter.encodeEmoji(msg)
    }

    fun createTextMessage(textMsg: String): Message {
        return Message(
            localId = UUID.randomUUID().toString(),
            message = textMsg,
            isMe = true,
            isSending = true
        )
    }

    fun createPhotosMessage(photos: List<Uri>): Message {
        return Message(
            localId = UUID.randomUUID().toString(),
            photos = photos.map { it.toString() },
            isMe = true,
            isSending = true
        )
    }
}