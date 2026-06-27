package com.hdl.dancer.booking.data.extension

import android.util.Patterns
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS
        .matcher(this)
        .matches()
}

fun String.isURL(): Boolean {
    return this.toHttpUrlOrNull() != null
}