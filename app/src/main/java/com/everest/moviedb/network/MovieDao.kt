package com.everest.moviedb.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.everest.moviedb.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * from movies ")
    suspend fun getPopularMoviesFromDb(): List<Movie>

    @Query("SELECT * from movies where releaseDate =:year")
    suspend fun getLatestMoviesFromDb(year: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)
}