package com.example.feature_search.data.local

import com.example.feature_search.domain.WatchedMoviesRepository

class WatchedMoviesRepositoryImpl(private val watchedMoviesDAO: WatchedMoviesDAO) : WatchedMoviesRepository{
//    suspend fun addWatchedMovie(movie: Movie){
//
//    }
   override suspend fun getWatchedMovies(): List<WatchedMoviesEntity>? {

       return watchedMoviesDAO.getWatchedMovies()
    }
}