package com.kantek.dancer.booking.domain.model.search

interface IDancer {
    val id: String
    val name: String
    val avatar: String
    val danceStyle: String
    val rating: String
    val bio: String
    val isAvailableNow: Boolean
    val clubOpenTime: String get() = ""
    val clubCloseTime: String get() = ""
}
