package com.hdl.dancer.booking.data.remote.api

import com.hdl.dancer.booking.data.helper.network.ApiAsync
import com.hdl.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.hdl.dancer.booking.data.model.response.room.RoomDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApi {
    @GET("rooms/by-club/{clubId}")
    fun fetchByClub(@Path("clubId") clubId: String): ApiAsync<ApiResponsePaging<RoomDTO>>
}

