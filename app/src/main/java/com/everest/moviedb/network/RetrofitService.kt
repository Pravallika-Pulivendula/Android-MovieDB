package com.everest.moviedb.network

import com.everest.moviedb.network.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String): MovieResponse

    @GET("discover/movie")
    suspend fun getLatestMovies(
        @Query("api_key") api_key: String,
        @Query("year") year: Int
    ): MovieResponse

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("api_key") api_key: String,
        @Query("query") movieName: String
    ): MovieResponse
}
