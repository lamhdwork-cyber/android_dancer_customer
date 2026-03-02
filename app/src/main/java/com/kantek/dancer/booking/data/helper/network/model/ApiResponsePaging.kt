package com.kantek.dancer.booking.data.helper.network.model

class ApiResponsePaging<T>(
    val data: List<T>,
    val total:Int
)