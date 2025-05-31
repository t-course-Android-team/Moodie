package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watched_movies_db")
data class WatchedMoviesEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val genres: String,
    val duration: String,
    val yearRelease: Int,
    val rate: Float,
    val posterUrl: String,
    val plot: String,
    val isSaved: Boolean // если false значит он просмотренный, если true значит он добавлен в сохранённые
)