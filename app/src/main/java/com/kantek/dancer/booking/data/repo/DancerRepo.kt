package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.helper.network.model.ApiResponsePaging
import com.kantek.dancer.booking.data.remote.api.DancerApi
import com.kantek.dancer.booking.domain.model.response.dancer.DancerDTO

interface DancerRepo {
    suspend fun fetchByPage(clubId: String, page: Int): List<DancerDTO>
    suspend fun fetchByPageWithTotal(clubId: String, page: Int): ApiResponsePaging<DancerDTO>?
    suspend fun fetchDetail(dancerId: String): DancerDTO?
    suspend fun toggleAvailability(dancerId: String)
}

class FetchDancerByPageRepoImpl(private val dancerApi: DancerApi) : DancerRepo {

    override suspend fun fetchByPage(clubId: String, page: Int): List<DancerDTO> {
        return dancerApi.fetchByPage(clubId = clubId, page = page).awaitNullable()?.data ?: emptyList()
    }

    override suspend fun fetchByPageWithTotal(clubId: String, page: Int): ApiResponsePaging<DancerDTO>? {
        return dancerApi.fetchByPage(clubId = clubId, page = page).awaitNullable()
    }

    override suspend fun fetchDetail(dancerId: String): DancerDTO? {
        return dancerApi.fetchDetail(dancerId = dancerId).awaitNullable()
    }

    override suspend fun toggleAvailability(dancerId: String) {
        dancerApi.toggleAvailability(dancerId).awaitNullable()
    }
}
