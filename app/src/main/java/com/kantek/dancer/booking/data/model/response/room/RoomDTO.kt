package com.kantek.dancer.booking.data.model.response.room

data class RoomDTO(
    val id: String?,
    val name: String?,
    val description: String?,
    val type: String?,
    val image: String?,
    val capacity: Int?,
    val availabilityStatus: String?,
    val hourlyRate: String?,
    val services: List<String>?,
    val clubId: String?,
)

