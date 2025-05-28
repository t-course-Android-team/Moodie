package com.example.feature_search.data.remote



import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenRouterAPI {
    @POST("chat/completions")
    @Headers("Content-Type: application/json")
    suspend fun searchMove(
        @Body request: ChatRequest
    ):  Response<ChatResponse>
    @GET("models")
    suspend fun getModels() : ModelsResponse

    @POST("/")
    @Headers("Content-Type: application/json")
    suspend fun updateServiceKey(
        @Body requestBody: ServiceRequest,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>
}
