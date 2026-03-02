package com.kantek.dancer.booking.domain.model.response

data class UserResponse(
    val token: String = "",
    val user: UserDTO?
)

data class UserDTO(
    val avatar_url: String?,
    val device: String?,
    val dob: String?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val first_name: String?,
    val last_name: String?,
    val phone: String?,
    val verified_at: String?,
    val language: String?,
    val gender: Int?
)