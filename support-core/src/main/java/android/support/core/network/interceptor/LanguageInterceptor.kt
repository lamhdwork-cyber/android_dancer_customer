package android.support.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoLanguageRequired

/**
 * Adds a `content-language` header to every request unless the called method is
 * annotated with [NoLanguageRequired].
 *
 * The language is resolved lazily through [languageProvider] so this interceptor
 * stays decoupled from any app-specific storage and can be copied as-is.
 */
class LanguageInterceptor(private val languageProvider: () -> String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val isNoTokenRequired = originRequest.tag(Invocation::class.java)?.method()
            ?.getAnnotation(NoLanguageRequired::class.java) != null
        if (isNoTokenRequired) return chain.proceed(originRequest)

        var request = originRequest
        request = request.newBuilder()
            .addHeader("content-language", languageProvider())
            .build()
        return chain.proceed(request)
    }
}
