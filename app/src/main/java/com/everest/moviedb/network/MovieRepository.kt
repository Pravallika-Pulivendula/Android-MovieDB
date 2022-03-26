package com.everest.moviedb.network

import com.everest.moviedb.models.Movie


class MovieRepository(
    private val retrofitClient: RetrofitClient,
    private val movieDatabase: MovieDatabase
) {

    suspend fun getPopularMovies() =
        retrofitClient.getClient().getPopularMovies(RetrofitClient.api_key)

    suspend fun getLatestMovies() =
        retrofitClient.getClient().getLatestMovies(RetrofitClient.api_key)

    suspend fun getMovieByName(movieName: String) =
        retrofitClient.getClient().getMovieByName(RetrofitClient.api_key, movieName)

    suspend fun addMovies(movies: List<Movie>) =
        movieDatabase.movieDao().addMovies(movies)

    suspend fun getPopularMoviesFromDb() =
        movieDatabase.movieDao().getPopularMoviesFromDb()

    suspend fun getLatestMoviesFromDb() =
        movieDatabase.movieDao().getLatestMoviesFromDb()

}
