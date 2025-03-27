package com.developing.charityapplication.data.authentication

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Danh sách API cần Bearer Token
        val authRequiredEndpoints = listOf("/auth")

        // Nếu API cần Bearer Token, thêm vào header
        val newRequest = if (authRequiredEndpoints.any { request.url.encodedPath.contains(it) }) {
            val token = tokenProvider.getToken() ?: ""
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request // Giữ nguyên nếu API không cần auth
        }

        return chain.proceed(newRequest)
    }
}