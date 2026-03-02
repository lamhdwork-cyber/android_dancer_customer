package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.UserDTO
import com.kantek.dancer.booking.domain.model.ui.user.IUser

class UserFactory(private val textFormatter: TextFormatter) {
    fun create(
        it: UserDTO?,
        languageDisplayRes: Int=0
    ): IUser? {
        if (it == null) return null
        return object : IUser {
            override val id: Int
                get() = it.id.safe()
            override val avatarURL: String
                get() = it.avatar_url.safe()
            override val firstName: String
                get() = it.first_name.safe()
            override val lastName: String
                get() = it.last_name.safe()
            override val fullName: String
                get() = "$firstName $lastName"
            override val email: String
                get() = it.email.safe()
            override val phoneNumber: String
                get() = it.phone.safe()
            override val phoneDisplay: String
                get() = textFormatter.formatPhone(phoneNumber).safe()
            override val languageRes: Int
                get() = languageDisplayRes
        }
    }

    fun cleanPhoneNumber(phoneNumber: String): String {
        return textFormatter.cleanPhoneNumber(phoneNumber)
    }
}