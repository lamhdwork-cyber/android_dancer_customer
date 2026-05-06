package com.kantek.dancer.booking.domain.model.support

import com.kantek.dancer.booking.domain.model.ui.IChars

enum class Scopes : IChars {
    App,
    Introduce,
    AppRole,
    Account,
    Search,
    MyBooking,
    Notification,
    Dancer,
    Booking,
    BookingConfirm,
    Conversation,
    FAQsThreads,
    Home;

    override fun toString(): String {
        return name
    }
}