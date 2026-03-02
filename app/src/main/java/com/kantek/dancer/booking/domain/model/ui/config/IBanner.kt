package com.kantek.dancer.booking.domain.model.ui.config

interface IBanner {
    val id: Long get() = 0L
    val title: String get() = ""
    val dataURL: String get() = ""
    val linkURL: String get() = ""
    val hasVideo: Boolean get() = false
}