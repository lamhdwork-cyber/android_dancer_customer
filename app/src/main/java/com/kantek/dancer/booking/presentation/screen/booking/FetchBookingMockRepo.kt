package com.kantek.dancer.booking.presentation.screen.booking

import com.kantek.dancer.booking.domain.factory.BookingMockFactory
import com.kantek.dancer.booking.domain.factory.BookingRoomFactory

class FetchBookingMockRepo(
    private val bookingRoomFactory: BookingRoomFactory,
    private val bookingMockFactory: BookingMockFactory
) {
    suspend operator fun invoke(): BookingMockSeed {
        return BookingMockSeed(
            performers = bookingMockFactory.createPerformers(),
            rooms = bookingRoomFactory.createMockRooms(),
            scheduleDays = bookingMockFactory.createScheduleDays(),
            scheduleTimes = bookingMockFactory.createScheduleTimes()
        )
    }
}
