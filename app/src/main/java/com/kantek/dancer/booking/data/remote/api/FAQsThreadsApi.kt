package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.response.legal.LegalAnswerDTO
import com.kantek.dancer.booking.domain.model.response.legal.LegalCategoryDTO
import com.kantek.dancer.booking.domain.model.response.legal.LegalQuestionDTO
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FAQsThreadsApi {

    @GET("type_service")
    fun fetchCategoriesByPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<LegalCategoryDTO>>

    @GET("forum/list-question")
    fun fetchQuestionOfCategoriesByPage(
        @Query("type_id") categoryID: Int,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<LegalQuestionDTO>>

    @POST("forum/create-question")
    @FormUrlEncoded
    fun createQuestion(
        @Field("type_id") id: Int,
        @Field("description") contents: String
    ): ApiAsync<Any>

    @GET("forum/list-reply")
    fun fetchAnswersByPage(
        @Query("id") questionID: Int,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = -1
    ): ApiAsync<List<LegalAnswerDTO>>

    @POST("forum/reply-question")
    @FormUrlEncoded
    fun createAnswer(
        @Field("id") id: Int,
        @Field("description") contents: String
    ): ApiAsync<Any>
}