package com.example.feature_search.domain

import javax.inject.Inject
import javax.inject.Singleton

interface ApiKeyProvider {
    suspend fun <T> withTempApiKey(block: suspend (String) -> T): T
}

@Singleton
class ApiKeyProviderImpl @Inject constructor(
    private val createNewApiKeyUseCase: CreateNewApiKeyUseCase
) : ApiKeyProvider {
    override suspend fun <T> withTempApiKey(block: suspend (String) -> T): T {
        val tempApiKey = createNewApiKeyUseCase()
        return block(tempApiKey)

    }
}