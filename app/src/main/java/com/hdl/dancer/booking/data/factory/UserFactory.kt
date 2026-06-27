package com.hdl.dancer.booking.data.factory

import android.support.core.extensions.safe
import com.hdl.dancer.booking.data.formatter.TextFormatter
import com.hdl.dancer.booking.data.model.response.UserDTO
import com.hdl.dancer.booking.domain.model.user.IUser

class UserFactory(private val textFormatter: TextFormatter) {
    fun create(
        it: UserDTO?,
        languageDisplayRes: Int=0
    ): IUser? {
        if (it == null) return null
        return object : IUser {
            override val id: Int
                get() = it.id?.toIntOrNull().safe()
            override val avatarURL: String
                get() = it.avatar.safe()
            override val firstName: String
                get() = it.firstName.safe()
            override val lastName: String
                get() = it.lastName.safe()
            override val fullName: String
                get() = "$lastName $firstName"
            override val email: String
                get() = it.email.safe()
            override val phoneNumber: String
                get() = it.phone.safe()
            override val phoneDisplay: String
                get() = textFormatter.formatPhone(phoneNumber).safe()
            override val languageRes: Int
                get() = languageDisplayRes
            override val role: String?
                get() = it.role
        }
    }

    fun cleanPhoneNumber(phoneNumber: String): String {
        return textFormatter.cleanPhoneNumber(phoneNumber)
    }
}