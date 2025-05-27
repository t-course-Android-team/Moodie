package com.example.feature_search.domain


import com.example.feature_search.BuildConfig
import com.example.feature_search.data.remote.RemoteOpenRouterRepositoryImpl
import com.example.feature_search.data.remote.RetrofitHelper

object RepositoryFactory {
    fun createOpenRouterRepository(): RemoteOpenRouterRepository {
        return RemoteOpenRouterRepositoryImpl(
            RetrofitHelper.createOpenRouterAPI(BuildConfig.OPENROUTER_API_KEY)
        )
    }

}