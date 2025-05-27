package com.example.feature_search.data.remote



import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import kotlin.apply
import kotlin.jvm.java

internal object RetrofitHelper {
    private  fun createConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            coerceInputValues = true

        }
        return json.asConverterFactory(contentType)
    }

    fun createOpenRouterAPI(apiKey: String): com.example.feature_search.data.remote.OpenRouterAPI {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
              .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://openrouter.ai/api/v1/")
            .addConverterFactory(createConverterFactory())
            .client(okHttpClient)
            .build()
            .create(OpenRouterAPI::class.java)
    }
}