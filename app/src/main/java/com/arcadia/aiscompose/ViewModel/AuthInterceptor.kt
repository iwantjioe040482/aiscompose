package com.arcadia.aiscompose.ViewModel

import androidx.compose.runtime.MutableState
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenState: MutableState<String?>,
    private val onLogout: () -> Unit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenState.value
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)

        if (response.code() == 401) {
            // Token expired
            tokenState.value = null
            onLogout()
        }

        return response
    }
}
