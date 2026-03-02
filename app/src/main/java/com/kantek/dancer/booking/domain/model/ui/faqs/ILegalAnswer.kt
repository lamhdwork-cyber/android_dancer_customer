package com.kantek.dancer.booking.domain.model.ui.faqs

interface ILegalAnswer {
    val id: Int get() = 0
    val avatarURL get() = ""
    val name get() = ""
    val timeAgo get() = ""
    val content get() = ""
}