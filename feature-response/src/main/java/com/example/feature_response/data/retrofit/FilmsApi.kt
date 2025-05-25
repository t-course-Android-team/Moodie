package com.example.feature_response.data.retrofit

import com.example.feature_response.data.model.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {

    @GET("/")
    suspend fun getFilm(
        @Query("apikey") apiKey: String,
        @Query("t") title: String,
        @Query("plot") plotSize: String
    ): FilmResponse

}