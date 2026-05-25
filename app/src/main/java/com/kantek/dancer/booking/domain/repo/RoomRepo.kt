package com.kantek.dancer.booking.domain.repo

import com.kantek.dancer.booking.domain.model.booking.IRoom

interface RoomRepo {
    suspend fun fetchAllByClub(clubId: String): List<IRoom>
}
