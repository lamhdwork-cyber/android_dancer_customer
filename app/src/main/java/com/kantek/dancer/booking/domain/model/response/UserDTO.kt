package com.kantek.dancer.booking.domain.model.response

import com.google.gson.annotations.SerializedName

data class AuthTokens(
    val accessToken: String?,
    val refreshToken: String?
)

data class UserResponse(
    val user: UserDTO?,
    val tokens: AuthTokens?
)

data class UserDTO(
    val email: String? = null,
    @SerializedName(value = "firstName", alternate = ["first_name"])
    val firstName: String? = null,
    @SerializedName(value = "lastName", alternate = ["last_name"])
    val lastName: String? = null,
    @SerializedName("fcmToken")
    val fcmToken: String? = null,
    @SerializedName(value = "avatar", alternate = ["avatar_url"])
    val avatar: String? = null,
    val phone: String? = null,
    val clubId: String? = null,
    val id: String? = null,
    val role: String? = null,
    val status: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null,
    val name: String? = null,
    val device: String? = null,
    val dob: String? = null,
    val verified_at: String? = null,
    val language: String? = null,
    val gender: Int? = null
)
