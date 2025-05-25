package com.example.feature_response.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class FilmResponse(
    @SerialName("Title") var Title: String? = null,
    @SerialName("Year") var Year: String? = null,
    @SerialName("Rated") var Rated: String? = null,
    @SerialName("Released") var Released: String? = null,
    @SerialName("Runtime") var Runtime: String? = null,
    @SerialName("Genre") var Genre: String? = null,
    @SerialName("Director") var Director: String? = null,
    @SerialName("Writer") var Writer: String? = null,
    @SerialName("Actors") var Actors: String? = null,
    @SerialName("Plot") var Plot: String? = null,
    @SerialName("Language") var Language: String? = null,
    @SerialName("Country") var Country: String? = null,
    @SerialName("Awards") var Awards: String? = null,
    @SerialName("Poster") var Poster: String? = null,
    @SerialName("Ratings") var Ratings: ArrayList<Ratings> = arrayListOf(),
    @SerialName("Metascore") var Metascore: String? = null,
    @SerialName("imdbRating") var imdbRating: String? = null,
    @SerialName("imdbVotes") var imdbVotes: String? = null,
    @SerialName("imdbID") var imdbID: String? = null,
    @SerialName("Type") var Type: String? = null,
    @SerialName("DVD") var DVD: String? = null,
    @SerialName("BoxOffice") var BoxOffice: String? = null,
    @SerialName("Production") var Production: String? = null,
    @SerialName("Website") var Website: String? = null,
    @SerialName("Response") var Response: String? = null
)

@Serializable
data class Ratings(
    @SerialName("Source") var Source: String? = null,
    @SerialName("Value") var Value: String? = null
)