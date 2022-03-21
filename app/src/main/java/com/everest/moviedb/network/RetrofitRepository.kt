package com.everest.moviedb.network

class RetrofitRepository(private val retrofitService: RetrofitService) {
    fun getPopularMovies() = retrofitService.getPopularMovies(RetrofitClient.api_key)

    fun getLatestMovies() = retrofitService.getLatestMovies(RetrofitClient.api_key)

    fun searchMovieByName(movieName: String) =
        retrofitService.getMovieByName(RetrofitClient.api_key, movieName)
}