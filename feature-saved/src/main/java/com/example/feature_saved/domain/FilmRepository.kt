package com.example.feature_saved.domain


interface FilmRepository {

    suspend fun getFilms(offset: Int, limit: Int): List<FilmEntity>
    suspend fun getCountFilms(): Int

}