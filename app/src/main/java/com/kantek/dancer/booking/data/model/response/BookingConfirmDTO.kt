package com.kantek.dancer.booking.data.model.response

data class BookingConfirmDTO(
    val id: String?,
    val roomId: String?,
    val bookingDate: String?,
    val startTime: String?,
    val endTime: String?,
    val bookingType: String?,
    val status: String?,
    val numberOfSongs: Int?,
    val numberOfGuests: Int?,
    val totalAmount: String?
)
