package com.kantek.dancer.booking.domain.model.ui.config

import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.extension.isEmail
import com.kantek.dancer.booking.domain.extension.resourceError

interface ISetting {
    val address: String get() = ""
    val phone: String get() = ""
    val phoneDisplay: String get() = ""
    val email get() = ""
}

data class ContactForm(
    var firstName: String = "",
    var lastname: String = "",
    var fullname: String = "",
    var email: String = "",
    var phone: String = "",
    var content: String = ""
) {
    fun valid() {
        if (firstName.isEmpty()) resourceError(R.string.error_blank_first_name)
        if (lastname.isEmpty()) resourceError(R.string.error_blank_last_name)
        if (email.isEmpty()) resourceError(R.string.error_blank_email)
        if (!email.isEmail()) resourceError(R.string.error_valid_email)
        if (phone.isEmpty()) resourceError(R.string.error_blank_phone)
        if (phone.length != 10) resourceError(R.string.error_valid_phone)
        if (content.isEmpty()) resourceError(R.string.error_blank_message)
        fullname = "$firstName $lastname"
    }
}