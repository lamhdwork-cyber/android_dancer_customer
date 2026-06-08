package com.kantek.dancer.booking.domain.model.booking

/**
 * Which action row to show on booking list / detail for the current app user role and booking status.
 */
enum class BookingActionsBar {
    /** Guest: cancel when allowed; request again when booking is cancelled. */
    USER_STANDARD,

    /** Club manager on pending booking: ready + wait. */
    CLUB_MANAGER_READY_WAIT,

    /** Club manager on wait booking: Ready. */
    CLUB_MANAGER_READY,

    /** No primary actions (e.g. manager on completed, or other terminal states). */
    NONE,
}
