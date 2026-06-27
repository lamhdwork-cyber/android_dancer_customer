package com.hdl.dancer.booking.domain.model.faqs

interface ILegalAnswer {
    val id: Int get() = 0
    val avatarURL get() = ""
    val name get() = ""
    val timeAgo get() = ""
    val content get() = ""
}