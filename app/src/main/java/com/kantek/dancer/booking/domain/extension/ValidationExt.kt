package com.kantek.dancer.booking.domain.extension

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.util.regex.Pattern

fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS
        .matcher(this)
        .matches()
}

fun String.isURL(): Boolean {
    return this.toHttpUrlOrNull() != null
}