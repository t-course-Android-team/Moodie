package com.example.feature_search.di

import com.example.domain.WatchedMoviesRepo
import com.example.feature_search.domain.ApiKeyProvider
import com.example.feature_search.domain.ApiKeyProviderImpl
import com.example.feature_search.domain.CreateNewApiKeyUseCase
import com.example.feature_search.domain.RemoteOpenRouterRepository
import com.example.feature_search.domain.SearchMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideSearchMovieUseCase(
        apiKeyProvider: ApiKeyProvider,
        watchedMoviesRepo: WatchedMoviesRepo
    ): SearchMovieUseCase {
        return SearchMovieUseCase(apiKeyProvider, watchedMoviesRepo)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideApiKeyProvider(useCase: CreateNewApiKeyUseCase): ApiKeyProvider {
        return ApiKeyProviderImpl(useCase)
    }

    @Provides
    @Singleton
    fun provideCreateTempApiKeyUseCase(
        repository: RemoteOpenRouterRepository
    ): CreateNewApiKeyUseCase {
        return CreateNewApiKeyUseCase(repository)
    }
}
