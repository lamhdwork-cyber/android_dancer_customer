package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.form.ReviewForm
import com.kantek.dancer.booking.domain.model.response.lawyer.LawyerDTO
import com.kantek.dancer.booking.domain.model.response.lawyer.ReviewDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LawyerApi {

    @GET("partner")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE,
        @Query("state_id") stateID: Int? = null,
        @Query("city_id") cityID: Int? = null,
        @Query("language_skill") languageID: Int? = null,
        @Query("service_id") jsonSpeciality: String? = null
    ): ApiAsync<ApiResponsePaging<LawyerDTO>>

    @GET("partner/detail")
    fun detail(
        @Query("id") id: Int,
    ): ApiAsync<LawyerDTO>

    @GET("review")
    fun reviewByPage(
        @Query("partner_id") lawyerID: Int,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<ReviewDTO>>

    @POST("review/create")
    fun createReview(@Body form: ReviewForm): ApiAsync<Any>
}