package com.kantek.dancer.booking.domain.model.ui.user

import com.kantek.dancer.booking.R

interface ILanguage {
    val id: Int get() = 0
    val nameRes: Int get() = R.string.language_en
    val nameString: String get() = ""
    val code: String get() = "en"
}