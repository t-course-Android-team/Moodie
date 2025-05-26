package com.example.feature_saved.domain

data class FilmEntity (
    val name: String,
    val genres: String,
    val duration: String,
    val yearRelease: Int,
    val rate: Float,
    val posterUrl: String,
    val plot: String
)