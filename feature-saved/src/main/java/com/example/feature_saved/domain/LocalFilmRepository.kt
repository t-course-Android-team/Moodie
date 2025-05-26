package com.example.feature_saved.domain

class LocalFilmRepository: FilmRepository {
    override suspend fun getFilms(offset: Int, limit: Int): List<FilmEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getCountFilms(): Int {
        TODO("Not yet implemented")
    }

}