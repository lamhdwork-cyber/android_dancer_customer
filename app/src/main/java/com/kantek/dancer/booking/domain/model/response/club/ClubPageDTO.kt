package com.kantek.dancer.booking.domain.model.response.club

data class ClubDTO(
    val id: String?,
    val name: String?,
    val description: String?,
    val address: String?,
    val city: String?,
    val district: String?,
    val latitude: String?,
    val longitude: String?,
    val coverImage: String?,
    val gallery: List<String>?,
    val phone: String?,
    val email: String?,
    val openTime: String?,
    val closeTime: String?,
    val status: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?
)
