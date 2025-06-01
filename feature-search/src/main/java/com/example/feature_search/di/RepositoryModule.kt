package com.example.feature_search.di

import android.content.Context
import com.example.data.WatchedMoviesDao
import com.example.data.WatchedMoviesDataBase
import com.example.data.WatchedMoviesRepoImpl
import com.example.domain.WatchedMoviesRepo
import com.example.feature_search.BuildConfig
import com.example.feature_search.domain.RemoteOpenRouterRepository
import com.example.feature_search.domain.RepositoryFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteOpenRouterRepository(
        @ApplicationContext context: Context
    ): RemoteOpenRouterRepository {
        val apiKey = context.getSharedPreferences("API_KEYS", Context.MODE_PRIVATE)
            .getString("OPENROUTER_KEY", BuildConfig.OPENROUTER_API_KEY)
            ?: BuildConfig.OPENROUTER_API_KEY

        return RepositoryFactory.createOpenRouterRepository(apiKey)
    }

}