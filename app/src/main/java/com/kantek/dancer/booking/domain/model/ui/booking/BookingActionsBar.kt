package com.kantek.dancer.booking.domain.model.ui.booking

/**
 * Which action row to show on booking list / detail for the current app user role and booking status.
 */
enum class BookingActionsBar {
    /** Guest: cancel when allowed; request again when booking is cancelled. */
    USER_STANDARD,

    /** Club manager on pending booking: Accept + Reject. */
    CLUB_MANAGER_ACCEPT_REJECT,

    /** Club manager on confirmed booking: Complete + Cancel. */
    CLUB_MANAGER_COMPLETE_CANCEL,

    /** No primary actions (e.g. manager on completed, or other terminal states). */
    NONE,
}
