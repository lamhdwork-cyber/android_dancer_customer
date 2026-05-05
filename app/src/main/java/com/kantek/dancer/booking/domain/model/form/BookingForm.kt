package com.kantek.dancer.booking.domain.model.form

import com.google.gson.annotations.SerializedName

data class BookingForm(
    @SerializedName("dancerIds")
    val dancerIds: List<String>,
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("numberOfSongs")
    val numberOfSongs: Int,
    @SerializedName("numberOfGuests")
    val numberOfGuests: Int,
    @SerializedName("bookingDate")
    val bookingDate: String? = null,
    @SerializedName("startTime")
    val startTime: String? = null,
    @SerializedName("endTime")
    val endTime: String? = null,
    @SerializedName("notes")
    val notes: String? = null
)