package com.hdl.dancer.booking.domain.repo

import com.hdl.dancer.booking.domain.model.booking.IRoom

interface RoomRepo {
    suspend fun fetchAllByClub(clubId: String): List<IRoom>
}
