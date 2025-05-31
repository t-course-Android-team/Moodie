package com.example.feature_search.domain

import com.example.feature_search.data.remote.CreateKeyRequest
import java.io.IOException
import javax.inject.Inject

class CreateNewApiKeyUseCase @Inject constructor(
    private val serviceRepository: RemoteOpenRouterRepository
) {
    suspend operator fun invoke(): String {

        val response = serviceRepository.createApiKey(
            CreateKeyRequest(
                name = "temp_${System.currentTimeMillis()}",
                label = "movie_search_temp"
            )
        )
        return response.body()?.key ?: throw IllegalStateException("Failed to create API key")
    }
}