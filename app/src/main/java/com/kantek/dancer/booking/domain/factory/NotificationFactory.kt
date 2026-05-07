package com.kantek.dancer.booking.domain.factory

import android.graphics.Typeface
import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.NotificationDTO
import com.kantek.dancer.booking.domain.model.ui.user.INotification

class NotificationFactory(private val textFormatter: TextFormatter) {
    fun createList(its: List<NotificationDTO>?): List<INotification> {
        return its?.map(::create) ?: listOf()
    }

    private fun create(it: NotificationDTO): INotification {
        return object : INotification {
            override val id: String
                get() = it.id.safe()
            override val bookingID: String
                get() = it.data?.bookingId.safe()
            override val hasUnRead: Boolean
                get() = it.isRead == false
            override val title: String
                get() = it.title.safe()
            override val datetime: String
                get() = textFormatter.formatNotificationDateTime(it.createdAt)
            override val contents: String
                get() = it.message.safe()
            override val image: String
                get() = it.avatar.safe()
            override val typeFace: Int
                get() = if (hasUnRead) Typeface.NORMAL else Typeface.BOLD
            override val hasContactRequest: Boolean
                get() = it.isContactRequest.safe()
            override val dataID: String
                get() = textFormatter.formatNotificationID(it.data?.bookingId)
        }
    }

}