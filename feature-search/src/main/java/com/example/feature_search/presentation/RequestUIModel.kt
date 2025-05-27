package com.example.feature_search.presentation

data class RequestUIModel (
    val genre: String,
    val released: String?,
    val mood: String?,
    val country: String?,
    val reference: String?

)