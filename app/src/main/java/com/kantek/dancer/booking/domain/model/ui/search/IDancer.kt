package com.kantek.dancer.booking.domain.model.ui.search

interface IDancer {
    val id: String
    val name: String
    val avatar: String
    val danceStyle: String
    val rating: String
    val bio: String
    val isAvailableNow: Boolean
}
