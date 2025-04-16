package com.developing.charityapplication.data.authentication

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Những endpoint không cần Bearer token
        val excludedEndpoints = listOf(
            "/auth/token",
            "identity/users/registration",
            "identity/auth/send-verify-email-with-otp",
            "identity/auth/confirm-verify-email-otp",

        )

        // Nếu URL KHÔNG nằm trong danh sách loại trừ => thêm token
        val shouldAddToken = excludedEndpoints.none { request.url.encodedPath.contains(it) }

        val newRequest = if (shouldAddToken) {
            val token = tokenProvider.getToken() ?: ""
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request // Không thêm token nếu URL là auth/token
        }

        return chain.proceed(newRequest)
    }
}