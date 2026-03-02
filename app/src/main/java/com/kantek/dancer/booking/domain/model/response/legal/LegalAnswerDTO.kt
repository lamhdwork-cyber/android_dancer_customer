package com.kantek.dancer.booking.domain.model.response.legal

import com.kantek.dancer.booking.domain.model.response.UserDTO

data class LegalAnswerDTO(
    val description: String,
    val id: Int,
    val status: Int,
    val time: String,
    val user: UserDTO
)