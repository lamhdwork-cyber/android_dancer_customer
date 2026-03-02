package com.kantek.dancer.booking.domain.model.response.lawyer

import com.kantek.dancer.booking.domain.model.response.LanguageDTO

data class LawyerDTO(
    val address: String,
    val avatar_url: String,
    val cases: Int,
    val device: String,
    val dob: String,
    val education: String,
    val email: String,
    val exp: Int,
    val experience: String,
    val gender: Int,
    val id: Int,
    val languages: List<LanguageDTO>,
    val last_login: String,
    val license_url: String,
    val name: String,
    val phone: String,
    val remember_token: Any,
    val timezone: Any,
    val verified_at: String,
    val rating_avg: Float,
    val total_reviews: Int
)