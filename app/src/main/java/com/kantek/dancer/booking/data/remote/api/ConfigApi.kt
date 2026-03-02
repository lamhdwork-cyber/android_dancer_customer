package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.domain.model.response.config.BannerDTO
import com.kantek.dancer.booking.domain.model.response.config.FAQsDTO
import com.kantek.dancer.booking.domain.model.response.config.LinkDTO
import com.kantek.dancer.booking.domain.model.response.config.SettingDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigApi {

    @GET("general/ads?type=0")
    fun banners(): ApiAsync<List<BannerDTO>>

    @GET("general/faq")
    fun faqs(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = AppConfig.PER_PAGE
    ): ApiAsync<ApiResponsePaging<FAQsDTO>>

    @GET("general/setting")
    fun settings(): ApiAsync<SettingDTO>

    @GET("general/term-policy?type=about-us")
    fun aboutUs(): ApiAsync<LinkDTO>

    @GET("general/term-policy?type=term")
    fun terms(): ApiAsync<LinkDTO>

}
