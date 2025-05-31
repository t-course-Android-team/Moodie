package com.example.feature_response.data.retrofit

import com.example.feature_response.domain.FilmEntity
import com.example.feature_response.domain.FilmRepository

internal class RemoteFilmRepository(
    private val api: FilmsApi
) : FilmRepository {

    override suspend fun getFilm(title: String): FilmEntity? {
        return try {
            val response = api.getFilm(
                title = title,
                apiKey = "8ff44741",
                plotSize = "full"
            )
            FilmResponseMapper.map(response)
        } catch (t: Throwable) {
            null
        }
    }

}