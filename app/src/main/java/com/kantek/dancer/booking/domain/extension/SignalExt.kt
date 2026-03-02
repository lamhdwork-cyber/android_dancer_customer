package com.kantek.dancer.booking.domain.extension

import com.kantek.dancer.booking.domain.model.support.Signal

fun <T : Signal> T.update(block: T.() -> Unit) {
    block(this)
    this.emit()
}