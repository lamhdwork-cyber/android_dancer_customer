package com.kantek.dancer.booking.domain.model.support

import com.kantek.dancer.booking.domain.model.ui.IChars

enum class Scopes : IChars {
    App,
    Account,
    Search,
    MyCase,
    Notification,
    Lawyer,
    Conversation,
    FAQsThreads,
    Home;

    override fun toString(): String {
        return name
    }
}