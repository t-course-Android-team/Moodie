package com.example.search_feature.data


import com.example.search_feature.domain.RemoteRepository
import retrofit2.Response

class RemoteRepositoryImpl (
    private val api: OpenRouterAPI
    ): RemoteRepository
{
    override suspend fun getChatResponse(request: ChatRequest): Response<ChatResponse> {
        return api.searchMove(
            request = request
        )
    }

    override suspend fun getAvailableModels(): List<ModelData> {
        return api.getModels().data
    }

}