package com.everest.moviedb.network

import com.everest.moviedb.network.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("year") year: Int
    ): MovieResponse

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("query") movieName: String
    ): MovieResponse
}
