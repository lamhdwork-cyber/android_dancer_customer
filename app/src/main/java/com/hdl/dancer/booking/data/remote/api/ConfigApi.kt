package com.hdl.dancer.booking.data.remote.api

import com.hdl.dancer.booking.app.AppConfig
import com.hdl.dancer.booking.data.helper.network.ApiAsync
import com.hdl.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.hdl.dancer.booking.data.model.response.config.BannerDTO
import com.hdl.dancer.booking.data.model.response.config.FAQsDTO
import com.hdl.dancer.booking.data.model.response.config.LinkDTO
import com.hdl.dancer.booking.data.model.response.config.SettingDTO
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
