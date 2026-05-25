package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.factory.RoomFactory
import com.kantek.dancer.booking.data.remote.api.RoomApi
import com.kantek.dancer.booking.domain.model.booking.IRoom
import com.kantek.dancer.booking.domain.repo.RoomRepo

class RoomRepoImpl(
    private val roomApi: RoomApi,
    private val roomFactory: RoomFactory,
) : RoomRepo {

    override suspend fun fetchAllByClub(clubId: String): List<IRoom> {
        return roomFactory.createList(roomApi.fetchByClub(clubId = clubId).awaitNullable()?.data)
    }
}
