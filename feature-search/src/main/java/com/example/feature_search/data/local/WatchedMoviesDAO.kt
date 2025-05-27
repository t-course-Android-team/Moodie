package com.example.feature_search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface WatchedMoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchedMovie(movie: WatchedMoviesEntity)

    @Query("""
        SELECT * FROM watched_movies
    """)
    suspend fun getWatchedMovies(): List<WatchedMoviesEntity>?

    @Query("""
        SELECT COUNT(*) FROM watched_movies
    """)
    suspend fun getCount(): Int

    @Query("""
        SELECT COUNT(*) FROM watched_movies WHERE (name = :moveName)
    """)
    suspend fun isMovieWatched(moveName: String): Boolean

    @Query("""DELETE FROM watched_movies WHERE rowid  IN  
            (SELECT rowid FROM watched_movies ORDER BY rowid ASC  LIMIT :count)""")
    suspend fun removeOldest(count: Int)
}
