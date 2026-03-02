package com.kantek.dancer.booking.domain.model.form

import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.extension.isEmail
import com.kantek.dancer.booking.domain.extension.resourceError
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage

data class BookingForm(
    var address: String = "",
    var description: String = "",
    var language_skill: List<Int>? = null,
    var languageSelected: ILanguage? = null,
    var first_name: String? = "",
    var last_name: String? = "",
    var customer_email: String? = "",
    var customer_phone: String? = "",
    var partner_id: Int? = null,
) {
    fun valid() {
        if (address.isBlank()) resourceError(R.string.error_blank_address)
        if (description.isBlank()) resourceError(R.string.error_blank_description)
        if (first_name.isNullOrEmpty()) resourceError(R.string.error_blank_first_name)
        if (last_name.isNullOrEmpty()) resourceError(R.string.error_blank_last_name)
        if (customer_email.isNullOrEmpty()) resourceError(R.string.error_blank_email)
        if (customer_email?.isEmail() == false) resourceError(R.string.error_valid_email)
        if (customer_phone.isNullOrEmpty()) resourceError(R.string.error_blank_phone)
        if (customer_phone?.length != 10) resourceError(R.string.error_valid_phone)
        if (partner_id != null) language_skill = null
    }
}