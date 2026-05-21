package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.data.model.response.NotificationDTO
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationApi {

    @GET("notifications")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<NotificationDTO>>

    @PATCH("notifications/read-all")
    fun readAll(): ApiAsync<Any>

    @PATCH("notifications/read/{id}")
    fun readById(@Path("id") notificationId: String): ApiAsync<Any>
}