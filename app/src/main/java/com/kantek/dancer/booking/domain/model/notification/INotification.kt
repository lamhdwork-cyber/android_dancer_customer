package com.kantek.dancer.booking.domain.model.notification

import android.graphics.Typeface

interface INotification {
    val id: String get() = ""
    val bookingID: String get() = ""
    val hasUnRead: Boolean get() = true
    val title: String get() = ""
    val datetime: String get() = ""
    val contents: String get() = ""
    val image: String get() = ""
    val dataID: String get() = ""
    val hasContactRequest: Boolean get() = false
    val typeFace: Int get() = Typeface.NORMAL
}