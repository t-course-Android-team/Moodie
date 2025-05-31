package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.WatchedMoviesDao
import com.example.data.WatchedMoviesDataBase
import com.example.data.WatchedMoviesRepoImpl
import com.example.domain.WatchedMoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWatchedMoviesDatabase(
        @ApplicationContext context: Context
    ): WatchedMoviesDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            WatchedMoviesDataBase::class.java,
            "watched_movies.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWatchedMoviesDao(database: WatchedMoviesDataBase): WatchedMoviesDao {
        return database.watchedMoviesDao()
    }

    @Provides
    @Singleton
    fun provideWatchedMoviesRepo(dao: WatchedMoviesDao): WatchedMoviesRepo {
        return WatchedMoviesRepoImpl(dao)
    }
}