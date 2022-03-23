package com.everest.moviedb.network

import com.everest.moviedb.MovieDao
import com.everest.moviedb.models.Movie


class MovieRepository(private val movieDao: MovieDao) {
    fun getAllMovies(): List<Movie> = movieDao.getPopularMovies()

    fun addMovie(movies: List<Movie>) = movieDao.addMovies(movies)
}