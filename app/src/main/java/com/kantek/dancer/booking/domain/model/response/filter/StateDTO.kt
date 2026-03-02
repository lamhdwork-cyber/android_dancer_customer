package com.kantek.dancer.booking.domain.model.response.filter

import com.google.gson.annotations.SerializedName

data class StateDTO(
    val id: Int,
    @SerializedName("state_name")
    val name: String
)
