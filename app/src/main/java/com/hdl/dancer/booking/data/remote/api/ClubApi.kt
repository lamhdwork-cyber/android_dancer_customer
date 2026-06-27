package com.hdl.dancer.booking.data.remote.api

import com.hdl.dancer.booking.app.AppConfig
import com.hdl.dancer.booking.data.helper.network.ApiAsync
import com.hdl.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.hdl.dancer.booking.data.model.response.club.ClubDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClubApi {

    @GET("clubs")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<ClubDTO>>

    @GET("clubs/{id}")
    fun detail(
        @Path("id") id: String,
    ): ApiAsync<ClubDTO>
}
