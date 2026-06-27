package com.hdl.dancer.booking.domain.usecase

import android.support.core.extensions.withIO
import com.hdl.dancer.booking.domain.model.booking.IRoom
import com.hdl.dancer.booking.domain.repo.RoomRepo

class FetchRoomsByClubCase(
    private val roomRepo: RoomRepo,
) {
    suspend operator fun invoke(clubId: String): List<IRoom> {
        return withIO { roomRepo.fetchAllByClub(clubId = clubId) }
    }
}
