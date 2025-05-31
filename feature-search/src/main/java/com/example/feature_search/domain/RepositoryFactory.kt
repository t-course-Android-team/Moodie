package com.example.feature_search.domain


import com.example.feature_search.BuildConfig
import com.example.feature_search.data.remote.RemoteOpenRouterRepositoryImpl
import com.example.feature_search.data.remote.RetrofitHelper

object RepositoryFactory {
    fun createOpenRouterRepository(key: String): RemoteOpenRouterRepository {
        return RemoteOpenRouterRepositoryImpl(
            RetrofitHelper.createUserAPI(key), RetrofitHelper.createAdminAPI()
        )
    }

}