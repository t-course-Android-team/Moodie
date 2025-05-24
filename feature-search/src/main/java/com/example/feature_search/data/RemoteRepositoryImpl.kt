package com.example.feature_search.data



import com.example.feature_search.domain.RemoteRepository
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