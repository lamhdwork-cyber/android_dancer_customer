package com.hdl.dancer.booking.data.model.response.legal

import com.hdl.dancer.booking.data.model.response.UserDTO

data class LegalAnswerDTO(
    val description: String,
    val id: Int,
    val status: Int,
    val time: String,
    val user: UserDTO
)