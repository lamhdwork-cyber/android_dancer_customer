package com.kantek.dancer.booking.domain.model.entity

data class FilterEntity(
    val stateID: Int? = -1,
    val cityID: Int? = -1,
    val languageID: Int? = -1,
    val specialities: List<Int>? = null
)
