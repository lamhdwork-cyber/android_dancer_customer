package com.hdl.dancer.booking.data.model.form

import com.google.gson.annotations.SerializedName

data class BookingForm(
    @SerializedName("dancerIds")
    val dancerIds: List<String>,
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("tableNumber")
    val tableNumber: String="",
//    @SerializedName("numberOfSongs")
//    val numberOfSongs: Int,
//    @SerializedName("numberOfGuests")
//    val numberOfGuests: Int,
//    @SerializedName("bookingDate")
//    val bookingDate: String? = null,
    @SerializedName("startTime")
    val startTime: String? = null,
//    @SerializedName("endTime")
//    val endTime: String? = null,
    @SerializedName("customerName")
    val customerName: String? = null,
    @SerializedName("customerPhone")
    val customerPhone: String? = null,
    @SerializedName("notes")
    val notes: String? = null
)