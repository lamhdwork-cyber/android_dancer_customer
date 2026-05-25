package com.kantek.dancer.booking.presentation.model.support

interface Updatable {
    fun update(value: Any?, notify: Boolean = false)
}