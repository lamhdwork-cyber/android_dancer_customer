package com.kantek.dancer.booking.data.helper.network.interceptor

import com.kantek.dancer.booking.data.local.LanguageLocalSource
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoLanguageRequired

class LanguageInterceptor(private val languageLocalSource: LanguageLocalSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val isNoTokenRequired = originRequest.tag(Invocation::class.java)?.method()
            ?.getAnnotation(NoLanguageRequired::class.java) != null
        if (isNoTokenRequired) return chain.proceed(originRequest)

        var request = originRequest
        request = request.newBuilder()
            .addHeader("content-language", languageLocalSource.get())
            .build()
        return chain.proceed(request)
    }
}
