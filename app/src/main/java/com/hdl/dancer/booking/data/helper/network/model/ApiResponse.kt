package com.hdl.dancer.booking.data.helper.network.model

class ApiResponse<T>(
    val data: T,
    val success: Boolean,
    val message: String
)