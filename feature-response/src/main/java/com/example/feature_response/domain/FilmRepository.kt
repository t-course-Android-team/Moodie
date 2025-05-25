package com.example.feature_response.domain

internal interface FilmRepository {

    suspend fun getFilm(title: String): FilmEntity?

}