package com.example.feature_search.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("model") val model: String, @SerialName("messages") val messages: List<Message>
)

@Serializable
data class Message(
    @SerialName("role") val role: String, @SerialName("content") val content: String
)

@Serializable
data class ChatResponse(
    @SerialName("choices") val choices: List<Choice>?
)

@Serializable
data class Choice(
    @SerialName("message") val message: Message?
)


@Serializable
data class ModelsResponse(
    val data: List<ModelData>
)

@Serializable
data class ModelData(
    val id: String,
    val name: String,
    val description: String?,
)

@Serializable
data class CreateKeyRequest(
    @SerialName("name") val name: String, @SerialName("label") val label: String
)

@Serializable
data class ApiKeyResponse(
    @SerialName("data") val data: ApiKeyData, @SerialName("key") val key: String
)

@Serializable
data class ApiKeyData(
    @SerialName("hash") val hash: String,
    @SerialName("name") val name: String,
    @SerialName("label") val label: String?,
    @SerialName("limit") val limit: Int?,
    @SerialName("disabled") val disabled: Boolean,
)