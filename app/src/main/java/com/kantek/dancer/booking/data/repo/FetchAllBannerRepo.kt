package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.ConfigApi
import com.kantek.dancer.booking.domain.factory.ConfigFactory
import com.kantek.dancer.booking.domain.model.ui.config.IBanner
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