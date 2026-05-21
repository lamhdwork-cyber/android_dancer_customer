package com.kantek.dancer.booking.domain.repo

import com.kantek.dancer.booking.domain.model.search.ClubLocationSummary
import com.kantek.dancer.booking.domain.model.search.IClub

interface ClubRepo {
    suspend fun fetchByPage(page: Int): List<IClub>
    suspend fun fetchLocationSummary(clubId: String): ClubLocationSummary?
}
