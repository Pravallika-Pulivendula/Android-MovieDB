package com.everest.moviedb.network

import com.everest.moviedb.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") api_key: String): Call<MovieResponse>

    @GET("discover/movie")
    fun getLatestMovies(
        @Query("api_key") api_key: String,
        @Query("year") year: Int
    ): Call<MovieResponse>

    @GET("search/movie")
    fun getMovieByName(
        @Query("api_key") api_key: String,
        @Query("query") movieName: String
    ): Call<MovieResponse>
}
