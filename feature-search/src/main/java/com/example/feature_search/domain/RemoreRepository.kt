package com.example.feature_search.domain


import com.example.feature_search.data.ChatRequest
import com.example.feature_search.data.ChatResponse
import com.example.feature_search.data.ModelData
import retrofit2.Response

interface RemoteRepository {
    suspend fun getChatResponse(request: ChatRequest): Response<ChatResponse>
    suspend fun getAvailableModels(): List<ModelData>
}