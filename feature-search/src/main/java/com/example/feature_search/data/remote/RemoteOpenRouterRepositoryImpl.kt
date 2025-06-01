package com.example.feature_search.data.remote


import com.example.feature_search.domain.RemoteOpenRouterRepository
import retrofit2.Response

class RemoteOpenRouterRepositoryImpl(
    private val userApi: OpenRouterUserAPI, private val adminAPI: OpenRouterAdminAPI
) : RemoteOpenRouterRepository {
    override suspend fun getChatResponse(request: ChatRequest): Response<ChatResponse> {
        return userApi.searchMove(
            request = request
        )
    }

    override suspend fun getAvailableModels(): List<ModelData> {
        return userApi.getModels().data
    }

    override suspend fun createApiKey(request: CreateKeyRequest): Response<ApiKeyResponse> {
        return adminAPI.createApiKey(request)
    }

}