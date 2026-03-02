package com.kantek.dancer.booking.domain.model.support

interface Updatable {
    fun update(value: Any?, notify: Boolean = false)
}