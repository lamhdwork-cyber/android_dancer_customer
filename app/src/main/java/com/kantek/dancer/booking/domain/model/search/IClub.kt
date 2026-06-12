package com.kantek.dancer.booking.domain.model.search

interface IClub {
    val id: String
    val name: String
    val description: String
    val address: String
    val fullAddress: String
    val coverImage: String
    val distance: String
    val rating: String
    val openTime: String
    val closeTime: String
    val isOpen: Boolean
}
