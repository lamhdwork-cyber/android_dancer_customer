package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.form.BookingForm
import com.kantek.dancer.booking.domain.model.response.BookingConfirmDTO
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingApi {

    @POST("bookings/book-now")
    fun bookNow(@Body form: BookingForm): ApiAsync<List<BookingConfirmDTO>>

    @POST("bookings/reserve")
    fun reserve(@Body form: BookingForm): ApiAsync<List<BookingConfirmDTO>>

    @FormUrlEncoded
    @POST("contact-request/recreate")
    fun recreate(@Field("contact_request_id") id: String): ApiAsync<Any>

    @FormUrlEncoded
    @PATCH("bookings/{id}/cancel")
    fun cancel(
        @Path("id") id: String,
        @Field("reason") reason: String
    ): ApiAsync<Any>

    @FormUrlEncoded
    @PATCH("bookings/{id}/confirm")
    fun accept(@Path("id") id: String): ApiAsync<Any>

    @FormUrlEncoded
    @PATCH("bookings/{id}/complete")
    fun complete(@Path("id") id: String): ApiAsync<Any>

    @GET("bookings")
    fun fetchByPage(
        @Query("page") page: Int,
        @Query("status") status: String? = null,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<BookingDTO>>

    @GET("bookings/{id}")
    fun details(@Path("id") id: String): ApiAsync<BookingDTO>
}

