package com.everest.moviedb.network

import com.everest.moviedb.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("api_key") api_key: String
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("api_key") api_key: String,
        @Query("query") movieName: String
    ): Response<MovieResponse>
}
