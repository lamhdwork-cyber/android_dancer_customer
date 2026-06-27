package com.hdl.dancer.booking.data.repo

import android.support.core.extensions.safe
import com.hdl.dancer.booking.data.factory.ClubFactory
import com.hdl.dancer.booking.data.remote.api.ClubApi
import com.hdl.dancer.booking.domain.model.search.ClubLocationSummary
import com.hdl.dancer.booking.domain.model.search.IClub
import com.hdl.dancer.booking.domain.repo.ClubRepo

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
