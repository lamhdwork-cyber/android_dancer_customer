package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.ConfigApi
import kotlinx.coroutines.flow.MutableStateFlow

class GetLinkTermsRepo(private val configApi: ConfigApi) {
    val result = MutableStateFlow("")
    suspend operator fun invoke() {
        result.emit(configApi.terms().await().url)
    }
}