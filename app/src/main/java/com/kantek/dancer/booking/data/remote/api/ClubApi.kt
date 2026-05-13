package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.response.club.ClubDTO
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
