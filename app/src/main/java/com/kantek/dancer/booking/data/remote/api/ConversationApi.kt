package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.response.conversation.MessageDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ConversationApi {

    @GET("chat/chat-by-room")
    fun messages(
        @Query("contact_request_id") id: Int,
        @Query("page") page: Int,
        @Query("per_page") numberPerPage: Int = AppConfig.PER_PAGE,
    ): ApiAsync<ApiResponsePaging<MessageDTO>>

    @POST("chat/upload-image")
    @Multipart
    fun uploadPhotos(
        @Part photos: Array<MultipartBody.Part?>? = null,
        @PartMap buildMultipart: Map<String, @JvmSuppressWildcards RequestBody?>
    ): ApiAsync<Any>
}

class ConversationApiImpl(private val retrofit: Retrofit) :
    ConversationApi by retrofit.create(ConversationApi::class.java)