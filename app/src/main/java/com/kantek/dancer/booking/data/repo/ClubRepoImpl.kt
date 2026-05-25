package com.kantek.dancer.booking.data.repo

import android.support.core.extensions.safe
import com.kantek.dancer.booking.data.factory.ClubFactory
import com.kantek.dancer.booking.data.remote.api.ClubApi
import com.kantek.dancer.booking.domain.model.search.ClubLocationSummary
import com.kantek.dancer.booking.domain.model.search.IClub
import com.kantek.dancer.booking.domain.repo.ClubRepo

class ClubRepoImpl(
    private val clubApi: ClubApi,
    private val clubFactory: ClubFactory,
) : ClubRepo {

    override suspend fun fetchByPage(page: Int): List<IClub> {
        return clubFactory.createList(clubApi.fetchByPage(page).awaitNullable()?.data)
    }

    override suspend fun fetchLocationSummary(clubId: String): ClubLocationSummary? {
        val dto = clubApi.detail(id = clubId).awaitNullable() ?: return null
        return ClubLocationSummary(
            name = dto.name.safe(),
            address = dto.address.safe(),
        )
    }
}
