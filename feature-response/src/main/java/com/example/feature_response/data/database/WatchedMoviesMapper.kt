package com.example.feature_response.data.database

import com.example.data.WatchedMoviesEntity
import com.example.feature_response.domain.FilmEntity

internal object WatchedMoviesMapper {
    fun map(film: FilmEntity, isSaved: Boolean): WatchedMoviesEntity {
        return WatchedMoviesEntity(
            id = 0,
            name = film.name,
            genres = film.genres,
            duration = film.duration,
            yearRelease = film.yearRelease,
            rate = film.rate,
            posterUrl = film.posterUrl,
            plot = film.plot,
            isSaved = isSaved
        )
    }
}