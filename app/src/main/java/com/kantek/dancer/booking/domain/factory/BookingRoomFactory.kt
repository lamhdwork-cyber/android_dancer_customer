package com.kantek.dancer.booking.domain.factory

import com.kantek.dancer.booking.domain.model.ui.booking.IBookingRoom

class BookingRoomFactory {
    fun createMockRooms(): List<IBookingRoom> {
        return listOf(
            createRoom(
                id = "vip_room",
                nameKey = "vip_room",
                descriptionKey = "vip_room_desc",
                price = "500",
                icon = "king_bed"
            ),
            createRoom(
                id = "private_suite",
                nameKey = "private_suite",
                descriptionKey = "private_suite_desc",
                price = "850",
                icon = "meeting_room"
            )
        )
    }

    private fun createRoom(
        id: String,
        nameKey: String,
        descriptionKey: String,
        price: String,
        icon: String
    ): IBookingRoom {
        return object : IBookingRoom {
            override val id: String = id
            override val nameKey: String = nameKey
            override val descriptionKey: String = descriptionKey
            override val price: String = price
            override val icon: String = icon
        }
    }
}
