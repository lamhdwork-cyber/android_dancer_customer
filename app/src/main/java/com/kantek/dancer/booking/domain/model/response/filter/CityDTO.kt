package com.kantek.dancer.booking.domain.model.response.filter

import com.google.gson.annotations.SerializedName

data class CityDTO(
    val id: Int,
    @SerializedName("city_name")
    val name: String
)
