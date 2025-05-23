package com.example.search_feature

import com.example.search_feature.data.ChatRequest
import com.example.search_feature.data.ChatResponse
import com.example.search_feature.data.Message
import com.example.search_feature.data.RemoteRepositoryImpl
import com.example.search_feature.data.RetrofitHelper
import retrofit2.Response



suspend fun main() {
    val apiKey = BuildConfig.OPENROUTER_API_KEY
    val api = RetrofitHelper.createOpenRouterAPI(apiKey)
    val repository = RemoteRepositoryImpl(api)
    val request = ChatRequest(
        model = "deepseek/deepseek-chat-v3-0324:free",
        messages = listOf(
            Message(role = "user", content = "Which movie should I watch if I'm sad and I love fantasy")
        )
    )
    val response : Response<ChatResponse> = repository.getChatResponse(request)
    val answer = response.body()?.choices?.firstOrNull()?.message?.content
    println(answer)
}