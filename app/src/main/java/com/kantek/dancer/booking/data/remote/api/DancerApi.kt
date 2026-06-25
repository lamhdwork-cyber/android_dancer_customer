package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import android.support.core.network.ApiAsync
import android.support.core.network.model.ApiResponsePaging
import com.kantek.dancer.booking.data.model.response.dancer.DancerDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DancerApi {

    @GET("dancers")
    fun fetchByPage(
        @Query("clubId") clubId: String,
        @Query("page") page: Int,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<DancerDTO>>

    @GET("dancers/available-now")
    fun availableNow(
        @Query("clubId") clubId: String,
        @Query("page") page: Int,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<DancerDTO>>

    @GET("dancers/{id}")
    fun fetchDetail(
        @Path("id") dancerId: String
    ): ApiAsync<DancerDTO>

    @GET("dancers/{id}/availability")
    fun toggleAvailability(
        @Path("id") dancerId: String
    ): ApiAsync<Any>
}
