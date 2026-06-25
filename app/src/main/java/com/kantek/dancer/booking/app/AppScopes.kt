package com.kantek.dancer.booking.app

import android.support.ui.helper.IChars

enum class AppScopes : IChars {
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