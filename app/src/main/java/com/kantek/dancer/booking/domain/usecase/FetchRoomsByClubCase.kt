package com.kantek.dancer.booking.domain.usecase

import com.kantek.dancer.booking.data.repo.RoomRepo
import com.kantek.dancer.booking.domain.factory.RoomFactory
import com.kantek.dancer.booking.domain.model.ui.booking.IRoom

class FetchRoomsByClubCase(
    private val roomRepo: RoomRepo,
    private val roomFactory: RoomFactory,
) {
    suspend operator fun invoke(clubId: String): List<IRoom> {
        val rooms = roomRepo.fetchAllByClub(clubId = clubId)
        return roomFactory.createList(rooms)
    }
}

