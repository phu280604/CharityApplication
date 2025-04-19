package com.developing.charityapplication.di.module.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.developing.charityapplication.data.authentication.AuthInterceptor
import com.developing.charityapplication.data.authentication.TokenProvider
import com.developing.charityapplication.di.cert.getOkHttpClientWithCertificate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    // region --- Methods ---

    @Provides
    @Singleton
    fun provideCustomLoggingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val token = request.header("Authorization") ?: "No Token"

            val responseBody = response.body
            val content = responseBody?.string() ?: "No content"
            val mediaType = responseBody?.contentType()

            Log.d("HTTP_LOG", "========== START ==========")
            Log.d("HTTP_LOG", "URL: ${response.request.url}")
            Log.d("HTTP_LOG", "Token: $token")
            Log.d("HTTP_LOG", "Code: ${response.code}")
            Log.d("HTTP_LOG", "Message: ${response.message}")
            Log.d("HTTP_LOG", "Body: $content")
            Log.d("HTTP_LOG", "========== END ==========")

            // Phải build lại response body vì .string() chỉ đọc một lần
            val newResponseBody = content.toByteArray().toResponseBody(mediaType)
            response.newBuilder().body(newResponseBody).build()
        }
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenProvider(sharedPreferences: SharedPreferences): TokenProvider {
        return TokenProvider(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): AuthInterceptor {
        return AuthInterceptor(tokenProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val sslClient = getOkHttpClientWithCertificate()
        return sslClient.newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(provideCustomLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
                LocalDateTime.parse(json.asString)
            })
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REMOTE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    // endregion

    // region --- Fields ---

    private const val LOCAL_BASE_URL = "https://192.168.3.228:80/"
    private const val REMOTE_BASE_URL = "https://baodungkhoaphu.online/api/v1.0.0/"

    // endregion

}