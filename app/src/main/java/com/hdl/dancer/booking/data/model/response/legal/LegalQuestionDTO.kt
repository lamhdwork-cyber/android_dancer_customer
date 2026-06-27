package com.hdl.dancer.booking.data.model.response.legal

data class LegalQuestionDTO(
    val description: String,
    val id: Int,
    val status: Int,
    val title: String,
    val type_service_id: Int
)