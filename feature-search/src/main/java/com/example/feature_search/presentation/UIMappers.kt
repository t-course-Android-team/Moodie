package com.example.feature_search.presentation

import com.example.feature_search.domain.MovieForSearch

object UIMappers {
    internal fun RequestUIModel.toModel(): MovieForSearch {
        return MovieForSearch(
            genre = genre,
            mood = mood,
            released = released,
            reference = reference,
            country = country
        )
    }

    internal fun MovieForSearch.toUI(): RequestUIModel {
        return RequestUIModel(
            genre = genre.toString(),
            mood = mood,
            released = released,
            reference = reference,
            country = country
        )
    }
}