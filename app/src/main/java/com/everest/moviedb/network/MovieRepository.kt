package com.everest.moviedb.network

class MovieRepository(private val retrofitClient: RetrofitClient) {

    suspend fun getPopularMovies() =
        retrofitClient.getClient().getPopularMovies(RetrofitClient.api_key)

    suspend fun getLatestMovies(year: Int) =
        retrofitClient.getClient().getLatestMovies(RetrofitClient.api_key, year)

    suspend fun getMovieByName(movieName: String) =
        retrofitClient.getClient().getMovieByName(RetrofitClient.api_key, movieName)

}