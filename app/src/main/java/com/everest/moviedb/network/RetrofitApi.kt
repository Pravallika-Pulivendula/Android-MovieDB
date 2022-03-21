package com.everest.moviedb

import com.everest.moviedb.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") api_key: String): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getLatestMovies(@Query("api_key") api_key: String): Call<MovieResponse>

    @GET("search/movie")
    fun getMovieByName(
        @Query("api_key") api_key: String,
        @Query("query") movieName: String
    ): Call<MovieResponse>
}
