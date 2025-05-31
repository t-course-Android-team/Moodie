package com.example.feature_search.data.remote


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenRouterUserAPI {
    @POST("chat/completions")
    @Headers("Content-Type: application/json")
    suspend fun searchMove(
        @Body request: ChatRequest
    ): Response<ChatResponse>

    @GET("models")
    suspend fun getModels(): ModelsResponse

}

interface OpenRouterAdminAPI {
    @POST("keys")
    suspend fun createApiKey(
        @Body request: CreateKeyRequest
    ): Response<ApiKeyResponse>
}