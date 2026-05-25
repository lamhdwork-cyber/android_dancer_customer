package com.kantek.dancer.booking.data.extension

import com.kantek.dancer.booking.presentation.model.support.Signal

fun <T : Signal> T.update(block: T.() -> Unit) {
    block(this)
    this.emit()
}