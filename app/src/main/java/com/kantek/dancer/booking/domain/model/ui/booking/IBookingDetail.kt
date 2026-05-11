package com.kantek.dancer.booking.domain.model.ui.booking

import com.kantek.dancer.booking.domain.model.ui.user.IUser

interface IBookingDetail : IBooking {
    val language get() = ""
    val hasReview get() = false
    val user: IUser? get() = null
    /** Club name for booking detail / confirmations. */
    val clubNameDisplay get() = ""
    /** Cover image from club payload. */
    val clubCoverImage get() = ""
    /** Short date for slot (e.g. Oct 27); empty if unknown. */
    val bookingDateShort get() = ""
    /** Formatted start time (e.g. 10:00 PM); empty if unknown. */
    val bookingTimeFormatted get() = ""
    /** One subtitle per selected dancer (e.g. styles), aligned with [dancersDisplayList]. */
    val dancerStyleLines get() = listOf<String>()
    /** Secondary badge (e.g. VIP room). */
    val showVipGuestBadge get() = false
    val roomType get() = ""
}