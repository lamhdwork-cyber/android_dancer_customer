package com.kantek.dancer.booking.data.repo.conversation

import com.kantek.dancer.booking.data.remote.api.ConversationApi
import com.kantek.dancer.booking.domain.factory.ConversationFactory
import com.kantek.dancer.booking.domain.model.response.conversation.MessageDTO
import com.kantek.dancer.booking.domain.model.ui.conversation.Message

class FetchMessageByPageRepo(
    private val conversationApi: ConversationApi,
    private val conversationFactory: ConversationFactory
) {
    suspend operator fun invoke(id: Int, page: Int): List<Message> {
        return conversationFactory.createMessageList(
            conversationApi.messages(id, page).await().data
        )
    }

    fun newMessage(it: MessageDTO, items: List<Message>?): Message {
        return if (items.isNullOrEmpty()) {
            conversationFactory.createMessage(it, null)
        } else {
            conversationFactory.createMessage(
                it,
                (items[0]).owner
            )
        }
    }
}