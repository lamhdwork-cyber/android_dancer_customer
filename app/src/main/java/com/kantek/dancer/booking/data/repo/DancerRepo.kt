package com.kantek.dancer.booking.data.repo

import com.google.gson.Gson
import com.kantek.dancer.booking.data.remote.api.DancerApi
import com.kantek.dancer.booking.domain.model.response.dancer.DancerDTO

interface DancerRepo {
    suspend fun fetchByPage(clubId: String, page: Int): List<DancerDTO>
    suspend fun fetchDetail(dancerId: String): DancerDTO?
}

class FetchDancerByPageRepoImpl(private val dancerApi: DancerApi) : DancerRepo {

    override suspend fun fetchByPage(clubId: String, page: Int): List<DancerDTO> {
        return dancerApi.fetchByPage(clubId = clubId, page = page).awaitNullable()?.data ?: emptyList()
    }

    override suspend fun fetchDetail(dancerId: String): DancerDTO? {
        return dancerApi.fetchDetail(dancerId = dancerId).awaitNullable()
    }
}
