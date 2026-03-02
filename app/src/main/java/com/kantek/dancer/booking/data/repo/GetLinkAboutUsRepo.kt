package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.ConfigApi
import kotlinx.coroutines.flow.MutableStateFlow

class GetLinkAboutUsRepo(private val configApi: ConfigApi) {
    val result = MutableStateFlow("")
    suspend operator fun invoke() {
        result.emit(configApi.aboutUs().await().url)
    }
}