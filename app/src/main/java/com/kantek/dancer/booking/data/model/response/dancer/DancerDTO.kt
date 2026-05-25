package com.kantek.dancer.booking.data.model.response.dancer

data class DancerDTO(
    val id: String?,
    val name: String?,
    val dateOfBirth: String?,
    val avatar: String?,
    val clubId: String?,
    val danceStyles: List<String>?,
    val bio: String?,
    val experience: Int?,
    val hourlyRate: String?,
    val rating: String?,
    val totalReviews: Int?,
    val tos: Int?,
    val status: String?,
    val isAvailableNow: Boolean?,
    val gallery: List<String>?,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?,
    val club: DancerClubDTO?,
    val age: Int?
)

data class DancerClubDTO(
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
