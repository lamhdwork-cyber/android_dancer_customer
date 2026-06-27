package com.hdl.dancer.booking.data.extension

import org.koin.mp.KoinPlatform


inline fun <reified T : Any> getBy(scopeName: CharSequence): T {
    val scopeId = "rememberScope:$scopeName"
    val koin = KoinPlatform.getKoin()
    return koin.getOrCreateScope<Any>(scopeId).get()
}
