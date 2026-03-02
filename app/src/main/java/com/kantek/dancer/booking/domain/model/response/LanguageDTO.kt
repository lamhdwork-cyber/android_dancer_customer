package com.kantek.dancer.booking.domain.model.response

data class LanguageDTO(
    val id: Int,
    val lang_key: String,
    val name: String,
    val pivotDTO: PivotDTO
)