package com.hdl.dancer.booking.data.model.response

data class SpecialityDTO(
    val id: Int,
    val name: String,
    val pivot: Pivot,
    val price: Int
)