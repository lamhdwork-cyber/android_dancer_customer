package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.response.NotificationDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApi {

    @GET("notification/my-notification")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<NotificationDTO>>
}