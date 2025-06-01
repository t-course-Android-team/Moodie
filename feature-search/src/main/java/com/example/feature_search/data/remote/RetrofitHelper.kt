package com.example.feature_search.data.remote


import com.example.feature_search.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

internal object RetrofitHelper {
    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun createUserAPI(apiKey: String): OpenRouterUserAPI {
        return createRetrofitClient(apiKey).create(OpenRouterUserAPI::class.java)
    }


    fun createAdminAPI(): OpenRouterAdminAPI {
        return Retrofit.Builder().baseUrl("https://openrouter.ai/api/v1/")
            .client(createOkHttpClient(BuildConfig.ADMIN_OPENROUTER_API_KEY_2))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()
            .create(OpenRouterAdminAPI::class.java)
    }


    private fun createRetrofitClient(
        apiKey: String
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://openrouter.ai/api/v1/")
            .client(createOkHttpClient(apiKey))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()
    }

    private fun createOkHttpClient(
        apiKey: String,
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Authorization", "Bearer $apiKey").apply {

                }.build()
            chain.proceed(request)
        }.build()
    }
}
