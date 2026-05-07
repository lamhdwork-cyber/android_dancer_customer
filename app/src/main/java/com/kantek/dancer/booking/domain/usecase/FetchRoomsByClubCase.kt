package com.kantek.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.kantek.dancer.booking.data.repo.RoomRepo
import com.kantek.dancer.booking.domain.factory.RoomFactory
import com.kantek.dancer.booking.domain.model.ui.booking.IRoom

class FetchRoomsByClubCase(
    private val roomRepo: RoomRepo,
    private val roomFactory: RoomFactory,
) {
    suspend operator fun invoke(clubId: String): List<IRoom> {
        return withIO {
            val rooms = roomRepo.fetchAllByClub(clubId = clubId)
            roomFactory.createList(rooms)
        }
    }
}

