package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WatchedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchedMovie(movie: WatchedMoviesEntity)

    @Query("""
        SELECT * FROM watched_movies_db
    """)
    suspend fun getAllWatchedMovies(): List<WatchedMoviesEntity>?

    @Query("""
        SELECT COUNT(*) FROM watched_movies_db
    """)
    suspend fun getCount(): Int

    @Query("""
        SELECT COUNT(*) FROM watched_movies_db WHERE (name = :movieName)
    """)
    suspend fun isMovieWatched(movieName: String): Boolean

    @Query("""DELETE FROM watched_movies_db WHERE rowid  IN  
            (SELECT rowid FROM watched_movies_db ORDER BY rowid ASC  LIMIT :count)""")
    suspend fun removeOldest(count: Int)

    @Query("SELECT * FROM watched_movies_db  ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getWatchedMoviesByLimit(
         offset: Int, limit: Int
    ): List<WatchedMoviesEntity>
}
