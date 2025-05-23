package com.example.search_feature.domain

import com.example.search_feature.data.ChatRequest
import com.example.search_feature.data.ChatResponse
import com.example.search_feature.data.ModelData
import retrofit2.Response

interface RemoteRepository {
    suspend fun getChatResponse(request: ChatRequest): Response<ChatResponse>
    suspend fun getAvailableModels(): List<ModelData>
}