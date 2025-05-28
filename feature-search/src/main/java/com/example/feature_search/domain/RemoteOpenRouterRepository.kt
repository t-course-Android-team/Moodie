package com.example.feature_search.domain


import com.example.feature_search.data.remote.ChatRequest
import com.example.feature_search.data.remote.ChatResponse
import com.example.feature_search.data.remote.ModelData
import retrofit2.Response

interface RemoteOpenRouterRepository {
    suspend fun getChatResponse(request: ChatRequest): Response<ChatResponse>
    suspend fun getAvailableModels(): List<ModelData>
//    suspend fun updateApiKey(): String
}