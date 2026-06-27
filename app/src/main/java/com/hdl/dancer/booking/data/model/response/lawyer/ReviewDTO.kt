package com.hdl.dancer.booking.data.model.response.lawyer

import com.hdl.dancer.booking.data.model.response.UserDTO

data class ReviewDTO(
    val created_at: String,
    val id: Int,
    val partner_id: Int,
    val rating: Float,
    val reference_id: Any,
    val reference_type: Any,
    val review_text: String,
    val status: Int,
    val type: Int,
    val updated_at: String,
    val user: UserDTO,
    val user_id: Int,
    val user_name: String
)