package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.form.BookingForm
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookingApi {

    @POST("contact-request/create")
    fun create(@Body form: BookingForm): ApiAsync<BookingDTO>

    @FormUrlEncoded
    @POST("contact-request/recreate")
    fun recreate(@Field("contact_request_id") id: Int): ApiAsync<Any>

    @POST("contact-request/guest-create")
    fun createWithoutAuth(@Body form: BookingForm): ApiAsync<Any>

    @FormUrlEncoded
    @POST("contact-request/user-cancel")
    fun cancel(
        @Field("contact_request_id") id: Int,
        @Field("reason_cancel") reason: String
    ): ApiAsync<Any>

    @GET("contact-request")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<BookingDTO>>

    @GET("contact-request/detail")
    fun details(@Query("id") id: Int): ApiAsync<BookingDTO>
}

