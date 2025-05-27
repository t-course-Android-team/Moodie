package com.example.feature_search.data.remote
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("model") val model: String,
    @SerialName("messages")  val messages: List<Message>
)
@Serializable
data class Message(
    @SerialName("role")  val role: String,
    @SerialName("content")  val content: String
)
@Serializable
data class ChatResponse(
    @SerialName("choices")  val choices: List<Choice>?
)
@Serializable
data class Choice(
    @SerialName("message")  val message: Message?
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