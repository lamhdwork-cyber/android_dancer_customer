package com.kantek.dancer.booking.data.extensions

import retrofit2.Retrofit

inline fun <reified T> provideApi(retrofit: Retrofit): T = retrofit.create(T::class.java)
