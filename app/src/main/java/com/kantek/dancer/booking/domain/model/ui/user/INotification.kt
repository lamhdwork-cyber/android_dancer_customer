package com.kantek.dancer.booking.domain.model.ui.user

import android.graphics.Typeface

interface INotification {
    val id: Long get() = 0
    val bookingID: Int get() = 0
    val hasUnRead: Boolean get() = true
    val title: String get() = ""
    val datetime: String get() = ""
    val contents: String get() = ""
    val image: String get() = ""
    val dataID: String get() = ""
    val hasContactRequest: Boolean get() = false
    val typeFace: Int get() = Typeface.NORMAL
}