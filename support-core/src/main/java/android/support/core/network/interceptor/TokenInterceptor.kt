package android.support.core.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoTokenRequired

/**
 * Adds a `Bearer` authorization header to every request unless the called
 * method is annotated with [NoTokenRequired].
 *
 * The token is resolved lazily through [tokenProvider] so this interceptor stays
 * decoupled from any app-specific storage and can be copied as-is.
 */
class TokenInterceptor(private val tokenProvider: () -> String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val isNoTokenRequired = originRequest.tag(Invocation::class.java)?.method()
            ?.getAnnotation(NoTokenRequired::class.java) != null
        if (isNoTokenRequired) return chain.proceed(originRequest)

        var request = originRequest
        val token = tokenProvider()
        if (token.isNotEmpty()) {
            val bearer = "Bearer $token"
            Log.e("Token", bearer)
            request = request.newBuilder()
                .addHeader("Authorization", bearer)
                .build()
        }
        return chain.proceed(request)
    }
}
