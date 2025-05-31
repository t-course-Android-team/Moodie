package com.example.feature_response.domain

 interface FilmRepository {

    suspend fun getFilm(title: String): FilmEntity?

}