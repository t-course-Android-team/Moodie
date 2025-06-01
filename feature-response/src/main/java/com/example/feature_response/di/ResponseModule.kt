package com.example.feature_response.di

import com.example.feature_response.data.retrofit.FilmsApi
import com.example.feature_response.data.retrofit.RemoteFilmRepository
import com.example.feature_response.data.retrofit.RetrofitHelper
import com.example.feature_response.domain.FilmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResponseModule {

    @Provides
    @Singleton
    fun provideFilmsApi(): FilmsApi {
        return RetrofitHelper.createRetrofit()
    }

    @Provides
    @Singleton
    fun provideFilmRepository(api: FilmsApi): FilmRepository {
        return RemoteFilmRepository(api)
    }
}