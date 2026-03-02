package com.kantek.dancer.booking.data.repo.conversation

import com.kantek.dancer.booking.data.local.UserLocalSource

class ChatRepo(private val userLocalSource: UserLocalSource) {

    fun setChatRoomCurrent(bookingID: Int) {
        userLocalSource.setChatRoomIDCurrent(bookingID)
    }

    fun getChatRoomCurrent(): Int = userLocalSource.getChatRoomIDCurrent()

}