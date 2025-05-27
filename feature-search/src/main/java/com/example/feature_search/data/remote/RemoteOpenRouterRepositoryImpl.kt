package com.example.feature_search.data.remote


import com.example.feature_search.domain.RemoteOpenRouterRepository
import retrofit2.Response

class RemoteOpenRouterRepositoryImpl(
    private val api: OpenRouterAPI
) : RemoteOpenRouterRepository {
    override suspend fun getChatResponse(request: ChatRequest): Response<ChatResponse> {
        return api.searchMove(
            request = request
        )
    }

    override suspend fun getAvailableModels(): List<ModelData> {
        return api.getModels().data
    }

}