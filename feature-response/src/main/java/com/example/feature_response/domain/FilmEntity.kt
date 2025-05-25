package com.example.feature_response.domain

data class FilmEntity (
    val name: String,
    val genres: String,
    val duration: String,
    val yearRelease: Int,
    val rate: Float,
    val posterUrl: String,
    val plot: String
)