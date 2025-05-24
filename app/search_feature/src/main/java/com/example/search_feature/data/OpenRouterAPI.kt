package com.example.search_feature.data


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OpenRouterAPI {
@POST("chat/completions")
suspend fun searchMove(
   @Body request: ChatRequest
):  Response<ChatResponse>
@GET("models")
suspend fun getModels() : ModelsResponse
}

