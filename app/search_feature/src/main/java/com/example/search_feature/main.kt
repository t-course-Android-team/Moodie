package com.example.search_feature

import com.example.search_feature.data.ChatRequest
import com.example.search_feature.data.ChatResponse
import com.example.search_feature.data.Message
import com.example.search_feature.data.RemoteRepositoryImpl
import com.example.search_feature.data.RetrofitHelper
import retrofit2.Response



suspend fun main() {
    val api = RetrofitHelper.createOpenRouterAPI()
    val repository = RemoteRepositoryImpl(api)

    val request = ChatRequest(
        model = "deepseek/deepseek-chat-v3-0324:free",
        messages = listOf(
            Message(role = "user", content = "What movie I should watch if i love fantasy? Tell me only names of these films")
        )
    )
    val response : Response<ChatResponse> = repository.getChatResponse(request)
    val answer = response.body()?.choices?.firstOrNull()?.message?.content
    println(answer)
}