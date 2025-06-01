package com.example.data

import com.example.domain.WatchedMoviesRepo
import javax.inject.Inject

class WatchedMoviesRepoImpl @Inject constructor(
    private val moviesDao: WatchedMoviesDao
) : WatchedMoviesRepo {
    override suspend fun insertWatchedMovie(movie: WatchedMoviesEntity) {
        moviesDao.insertWatchedMovie(movie)
    }

    override suspend fun getAllWatchedMovies(): List<WatchedMoviesEntity>? {
        return moviesDao.getAllWatchedMovies()
    }

    override suspend fun getWatchedMoviesCount(): Int {
        return moviesDao.getCount()
    }

    override suspend fun getSavedMoviesCount(): Int {
        return moviesDao.getSavedCount()
    }

    override suspend fun isMovieWatched(movieName: String): Boolean {
        return moviesDao.isMovieWatched(movieName)
    }

    override suspend fun removeOldestMovies(count: Int) {
        moviesDao.removeOldest(count)
    }

    override suspend fun getPagedWatchedMovies(
        offset: Int,
        limit: Int
    ): List<WatchedMoviesEntity> {
        return moviesDao.getWatchedMoviesByLimit(
            offset = offset,
            limit = limit
        )
    }

    override suspend fun getPagedSavedMovies(
        offset: Int,
        limit: Int
    ): List<WatchedMoviesEntity> {
        return moviesDao.getSavedMoviesByLimit(
            offset = offset,
            limit = limit
        )
    }

    override suspend fun isMovieSaved(movieName: String): Boolean {
        return moviesDao.isMovieSaved(movieName)
    }

    override suspend fun updateMovieIsSaved(movieName: String, newIsSaved: Boolean) {
        moviesDao.updateMovieIsSaved(movieName, newIsSaved)
    }

    override suspend fun deleteMovie(movieName: String) {
        moviesDao.deleteMovie(movieName)
    }

}
