package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.ClubApi
import com.kantek.dancer.booking.domain.model.response.club.ClubDTO

interface ClubRepo {
    suspend fun fetchByPage(page: Int): List<ClubDTO>
}

class FetchClubByPageRepoImpl(
    private val clubApi: ClubApi
) : ClubRepo {

    override suspend fun fetchByPage(page: Int): List<ClubDTO> {
        return clubApi.fetchByPage(page).awaitNullable()?.data ?: emptyList()
    }
}
