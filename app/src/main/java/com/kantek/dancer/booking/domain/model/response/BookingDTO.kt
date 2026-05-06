package com.kantek.dancer.booking.domain.model.response

import com.google.gson.annotations.SerializedName
import com.kantek.dancer.booking.domain.model.response.club.ClubDTO
import com.kantek.dancer.booking.domain.model.response.dancer.DancerDTO
import com.kantek.dancer.booking.domain.model.response.room.RoomDTO

data class BookingDTO(
    val id: String? = null,
    val userId: String? = null,
    val dancerId: String? = null,
    val clubId: String? = null,
    val roomId: String? = null,
    val bookingDate: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val pricePerHour: String? = null,
    val bookingType: String? = null,
    val status: String? = null,
    val totalAmount: String? = null,
    val numberOfSongs: Int? = null,
    val numberOfGuests: Int? = null,
    val notes: String? = null,
    val cancelReason: String? = null,
    val cancelledAt: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null,
    val user: UserDTO? = null,
    val dancer: DancerDTO? = null,
    val dancers: List<DancerDTO>? = null,
    val room: RoomDTO? = null,
    val review: Any? = null,
    @SerializedName("is_review")
    val isReview: Boolean = false,
    val club: ClubDTO? = null
)