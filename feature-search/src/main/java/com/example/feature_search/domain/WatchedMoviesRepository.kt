package com.example.feature_search.domain

import com.example.feature_search.data.local.WatchedMoviesEntity

interface WatchedMoviesRepository {
    suspend fun getWatchedMovies(): List<WatchedMoviesEntity>?
}