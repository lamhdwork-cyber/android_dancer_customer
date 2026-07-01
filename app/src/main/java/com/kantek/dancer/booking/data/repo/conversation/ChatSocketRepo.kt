package com.kantek.dancer.booking.data.repo.conversation

import android.support.core.extensions.safe
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.socket.ChatSocketClient
import android.support.ui.extension.toObject
import com.kantek.dancer.booking.data.model.response.conversation.MessageDTO
import com.kantek.dancer.booking.data.model.response.conversation.TypingDTO
import android.support.ui.extension.loge
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID

class ChatSocketRepo(
    private val chatSocketClient: ChatSocketClient,
    private val userLocalSource: UserLocalSource
) {
    val leave = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val typing = MutableSharedFlow<String?>(extraBufferCapacity = 1)
    val receiveMessage = MutableSharedFlow<MessageDTO?>(extraBufferCapacity = 1)

    init {
        chatSocketClient.observeLeave {
            loge(">>>>>>>>>>>>>>> Leave ${it[0]}")
            leave.tryEmit(true)
        }

        chatSocketClient.observeTyping {
            loge(">>>>>>>>>>>>>>> Typing ${it[0]}")
            typing.tryEmit((it[0].toString().toObject<TypingDTO>().avatar_url))
        }

        chatSocketClient.observeMessage {
            val rs = it[0].toString()
            loge(">>>>>>>>>>>>>>> Message receive ${it[0]}")
            try {
                var newMessage = rs.toObject<MessageDTO>()
                if (newMessage.local_id.isNullOrEmpty())
                    newMessage = newMessage.copy(local_id = UUID.randomUUID().toString())
                receiveMessage.tryEmit(newMessage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun emitJoinRoom(bookingID: Int) {
        chatSocketClient.emitJoinRoom(
            bookingID.toString(),
            userLocalSource.getUserDto()?.id ?: "",
            userLocalSource.getToken()
        )
    }

    suspend fun emitLeaveRoom(bookingID: Int) {
        chatSocketClient.emitLeaveRoom(
            bookingID.toString(),
            userLocalSource.getUserDto()?.id ?: ""
        )
    }

    suspend fun emitTyping(bookingID: Int, msg: String) {
        val user = userLocalSource.getUserDto()
        chatSocketClient.emitTyping(
            bookingID.toString(),
            user?.id ?: "",
            user?.avatar.safe(),
            msg
        )
    }

    fun disconnect() {
        chatSocketClient.unObserve()
    }

    suspend fun sendMessage(
        bookingID: Int,
        textMsg: String,
        localID: String
    ) {
        chatSocketClient.emitSendMessage(
            userID = userLocalSource.getUserDto()?.id ?: "",
            token = userLocalSource.getToken(),
            roomId = bookingID.toString(),
            localID = localID,
            message = textMsg
        )
    }

    suspend fun sendPhotoMessage(
        bookingID: Long,
        photo: List<String>
    ) {
        chatSocketClient.emitSendPhotos(
            userID = userLocalSource.getUserDto()?.id ?: "",
            token = userLocalSource.getToken(),
            roomId = bookingID.toString(),
            photo = photo
        )
    }
}