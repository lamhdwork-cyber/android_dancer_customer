package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.factory.DancerFactory
import com.kantek.dancer.booking.data.remote.api.DancerApi
import com.kantek.dancer.booking.domain.model.search.IDancer
import com.kantek.dancer.booking.domain.model.search.IDancerDetail
import com.kantek.dancer.booking.domain.repo.DancerRepo

class DancerRepoImpl(
    private val dancerApi: DancerApi,
    private val dancerFactory: DancerFactory,
) : DancerRepo {

    override suspend fun fetchByPage(clubId: String, page: Int): List<IDancer> {
        return dancerFactory.createList(
            dancerApi.fetchByPage(clubId = clubId, page = page).awaitNullable()?.data
        )
    }

    override suspend fun availableNow(clubId: String, page: Int): List<IDancer> {
        return dancerFactory.createList(
            dancerApi.availableNow(clubId = clubId, page = page).awaitNullable()?.data
        )
    }

    override suspend fun fetchByPageWithTotal(clubId: String, page: Int): Pair<List<IDancer>, Int?> {
        val body = dancerApi.fetchByPage(clubId = clubId, page = page).awaitNullable()
        val list = dancerFactory.createList(body?.data)
        val total = body?.meta?.totalItems
        return list to total
    }

    override suspend fun fetchDetail(dancerId: String): IDancerDetail? {
        return dancerFactory.createDetail(dancerApi.fetchDetail(dancerId = dancerId).awaitNullable())
    }

    override suspend fun toggleAvailability(dancerId: String) {
        dancerApi.toggleAvailability(dancerId).awaitNullable()
    }
}
