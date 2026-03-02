package com.kantek.dancer.booking.domain.model.ui.review

interface IReview {
    val id: Int get() = 0
    val avatarURL: String get() = ""
    val name: String get() = ""
    val createAt get() = ""
    val rating get() = 0f
    val contents get() = ""
}