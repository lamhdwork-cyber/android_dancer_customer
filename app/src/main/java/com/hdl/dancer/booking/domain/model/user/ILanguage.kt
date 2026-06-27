package com.hdl.dancer.booking.domain.model.user

import com.hdl.dancer.booking.R

interface ILanguage {
    val id: Int get() = 0
    val nameRes: Int get() = R.string.language_en
    val nameString: String get() = ""
    val code: String get() = "en"
}