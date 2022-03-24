package com.everest.moviedb.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.everest.moviedb.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * from movies")
    fun getPopularMovies(): List<Movie>

    @Query("SELECT * from movies where release_date = '2022-03-11'")
    fun getLatestMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<Movie>)
}