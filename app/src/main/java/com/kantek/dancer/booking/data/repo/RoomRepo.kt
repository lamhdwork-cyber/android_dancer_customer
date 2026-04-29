package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.remote.api.RoomApi
import com.kantek.dancer.booking.domain.model.response.room.RoomDTO

interface RoomRepo {
    suspend fun fetchAllByClub(clubId: String): List<RoomDTO>
}

class FetchRoomByClubRepoImpl(
    private val roomApi: RoomApi,
) : RoomRepo {
    override suspend fun fetchAllByClub(clubId: String): List<RoomDTO> {
        return roomApi.fetchByClub(clubId = clubId).awaitNullable()?.data ?: emptyList()
    }
}

