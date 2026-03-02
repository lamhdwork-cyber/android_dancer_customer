package com.kantek.dancer.booking.domain.model.ui.booking

import com.kantek.dancer.booking.domain.model.ui.user.IUser

interface IBookingDetail : IBooking {
    val language get() = ""
    val hasReview get() = false
    val user: IUser? get() = null
}