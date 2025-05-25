package com.example.feature_response.data.retrofit

import com.example.feature_response.data.model.FilmResponse
import com.example.feature_response.domain.FilmEntity

internal object FilmResponseMapper {
    fun map(response: FilmResponse): FilmEntity {
        return  FilmEntity(
            name = response.Title!!,
            genres = response.Genre!!,
            duration = if (response.Runtime!! == "N/A") { "N/A" } else response.Runtime?.dropLast(4)!!,
            yearRelease = response.Year?.toInt()!!,
            rate = response.imdbRating?.toFloat()!!,
            posterUrl = response.Poster!!,
            plot = response.Plot!!
        )
    }
}