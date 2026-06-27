package com.hdl.dancer.booking.data.model.response

data class LanguageDTO(
    val id: Int,
    val lang_key: String,
    val name: String,
    val pivotDTO: PivotDTO
)