package com.example.feature_search.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watched_movies")
data class WatchedMoviesEntity(
    @PrimaryKey val id: String,
    val name: String
)