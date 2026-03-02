package com.kantek.dancer.booking.domain.factory

import android.graphics.Typeface
import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_TIME
import com.kantek.dancer.booking.domain.extension.formatWith
import com.kantek.dancer.booking.domain.extension.utcToDateLocal
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.NotificationDTO
import com.kantek.dancer.booking.domain.model.ui.user.INotification

class NotificationFactory(private val textFormatter: TextFormatter) {
    fun createList(its: List<NotificationDTO>?): List<INotification> {
        return its?.map(::create) ?: listOf()
    }

    private fun create(it: NotificationDTO): INotification {
        return object : INotification {
            override val id: Long
                get() = it.id.safe()
            override val bookingID: Int
                get() = it.data_id.safe()
            override val hasUnRead: Boolean
                get() = it.is_read == false
            override val title: String
                get() = it.title.safe()
            override val datetime: String
                get() = it.created_at?.utcToDateLocal().formatWith(FORMAT_DATE_TIME)
            override val contents: String
                get() = it.message.safe()
            override val image: String
                get() = it.avatar_url.safe()
            override val typeFace: Int
                get() = if (hasUnRead) Typeface.NORMAL else Typeface.BOLD
            override val hasContactRequest: Boolean
                get() = it.is_contact_request.safe()
            override val dataID: String
                get() = textFormatter.formatNotificationID(it.data_id)
        }
    }

}