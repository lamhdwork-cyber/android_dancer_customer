package com.kantek.dancer.booking.data.remote.socket

import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.factory.ConversationFactory
import com.kantek.dancer.booking.presentation.extensions.loge
import io.socket.emitter.Emitter
import org.json.JSONObject

class ChatSocketClient(
    private val socketClient: SocketClient,
    private val conversationFactory: ConversationFactory
) {
    companion object {
        private const val NEW_MESSAGE = "new-message"
        private const val TYPING = "display-typing"
        private const val JOIN_ROOM = "join-room"
        private const val LEAVE_ROOM = "leave-room"
    }

    init {
        observeJoinRoom()
    }

    private fun observeJoinRoom() {
        loge(">>>>>>>> Socket Chat observe")
        socketClient.mSocket?.on(JOIN_ROOM) {
            loge(">>>>>>>>>>>>>>> JoinRoom ${it[0]}")
        }
    }

    fun observeLeave(fn: Emitter.Listener) {
        socketClient.mSocket?.on(LEAVE_ROOM, fn)
    }

    fun observeTyping(fn: Emitter.Listener) {
        socketClient.mSocket?.on(TYPING, fn)
    }

    fun observeMessage(fn: Emitter.Listener) {
        socketClient.mSocket?.on(NEW_MESSAGE, fn)
    }


    fun emitJoinRoom(
        roomId: String,
        userID: String,
        token: String
    ) {
        socketClient.connectIfNeed {
            val obj = JSONObject()
            obj.put("contact_request_id", roomId)
            obj.put("user_id", userID)
            obj.put("token", token)
            if (socketClient.mSocket?.connected() == true) {
                loge(">>>>>>>>>>>>>>> emit join-room: $obj")
                socketClient.mSocket?.emit("join-room", obj) //message
            }
        }
    }

    fun emitSendMessage(
        roomId: String,
        userID: String,
        token: String,
        localID: String,
        message: String
    ) {
        socketClient.connectIfNeed {
            val obj = JSONObject()
            obj.put("contact_request_id", roomId)
            obj.put("user_id", userID)
            obj.put("token", token)
            obj.put("local_id", localID)
            obj.put("message", conversationFactory.encodeEmoji(message.trim()))
            if (socketClient.mSocket?.connected() == true) {
                loge(">>>>>>>>>>>>>>> emit send-message: $obj")
                socketClient.mSocket?.emit("send-message", obj) //message
            }
        }
    }

    fun emitSendPhotos(
        roomId: String,
        userID: String,
        token: String,
        photo: List<String>
    ) {
        socketClient.connectIfNeed {
            val obj = JSONObject()
            obj.put("contact_request_id", roomId)
            obj.put("user_id", userID)
            obj.put("token", token)
            obj.put("image", photo.toJson())
            if (socketClient.mSocket?.connected() == true) {
                loge(">>>>>>>>>>>>>>> emit send-message: $obj")
                socketClient.mSocket?.emit("send-message", obj) //message
            }
        }
    }

    fun emitTyping(
        roomId: String,
        userID: String,
        avatarURL: String,
        input: String
    ) {
        socketClient.connectIfNeed {
            val obj = JSONObject()
            obj.put("contact_request_id", roomId)
            obj.put("user_id", userID)
            obj.put("avatar_url", avatarURL)
            obj.put("typing", input.isNotEmpty())
            if (socketClient.mSocket?.connected() == true) {
                loge(">>>>>>>>>>>>>>> emit typing: $obj")
                socketClient.mSocket?.emit("typing", obj)
            }
        }
    }

    fun emitLeaveRoom(
        roomId: String,
        userID: String
    ) {
        val obj = JSONObject()
        obj.put("contact_request_id", roomId)
        obj.put("user_id", userID)
        if (socketClient.mSocket?.connected() == true) {
            loge(">>>>>>>>>>>>>>> emit leave-room: $obj")
            socketClient.mSocket?.emit("leave-room", obj) //message
        }
    }

    fun unObserve() {
        loge(">>>>>>>>>>>>>>> Socket Chat unObserve")
        socketClient.mSocket?.off(JOIN_ROOM)
        socketClient.mSocket?.off(NEW_MESSAGE)
        socketClient.mSocket?.off(TYPING)
        socketClient.mSocket?.off(LEAVE_ROOM)
    }
}