package com.example.domain

import com.example.data.WatchedMoviesEntity

interface WatchedMoviesRepo {
    suspend fun insertWatchedMovie(movie: WatchedMoviesEntity)

    suspend fun getAllWatchedMovies(): List<WatchedMoviesEntity>?

    suspend fun getWatchedMoviesCount(): Int

    suspend fun isMovieWatched(movieName: String): Boolean

    suspend fun removeOldestMovies(count: Int)

    suspend fun getPagedWatchedMovies(
        offset: Int,
        limit: Int
    ): List<WatchedMoviesEntity>

    suspend fun isMovieSaved(movieName: String): Boolean

    suspend fun updateMovieIsSaved(movieName: String, newIsSaved: Boolean): Unit
}