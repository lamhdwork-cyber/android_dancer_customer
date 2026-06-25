package android.support.core.network.extensions

import retrofit2.Retrofit

inline fun <reified T> provideApi(retrofit: Retrofit): T = retrofit.create(T::class.java)