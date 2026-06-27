package com.hdl.dancer.booking.domain.repo

import com.hdl.dancer.booking.domain.model.search.ClubLocationSummary
import com.hdl.dancer.booking.domain.model.search.IClub

interface ClubRepo {
    suspend fun fetchByPage(page: Int): List<IClub>
    suspend fun fetchLocationSummary(clubId: String): ClubLocationSummary?
}
