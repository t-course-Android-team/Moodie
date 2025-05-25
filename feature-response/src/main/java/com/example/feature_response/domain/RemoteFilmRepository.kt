package com.example.feature_response.domain

import com.example.feature_response.data.retrofit.FilmResponseMapper
import com.example.feature_response.data.retrofit.FilmsApi

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