package com.hdl.dancer.booking.presentation.model.support

import com.hdl.dancer.booking.domain.model.IChars

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