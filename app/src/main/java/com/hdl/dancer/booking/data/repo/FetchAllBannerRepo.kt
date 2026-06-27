package com.hdl.dancer.booking.data.repo

import com.hdl.dancer.booking.data.remote.api.ConfigApi
import com.hdl.dancer.booking.data.factory.ConfigFactory
import com.hdl.dancer.booking.domain.model.config.IBanner
import kotlinx.coroutines.flow.MutableStateFlow

class FetchAllBannerRepo(
    private val configApi: ConfigApi,
    private val configFactory: ConfigFactory
) {
    val results = MutableStateFlow<List<IBanner>>(listOf())
    suspend operator fun invoke() {
        results.emit(configFactory.createBanners(configApi.banners().await()))
    }
}