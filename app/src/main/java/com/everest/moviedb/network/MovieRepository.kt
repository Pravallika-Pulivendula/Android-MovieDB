package com.everest.moviedb.network

import com.everest.moviedb.models.Movie
import com.everest.moviedb.utils.IMAGE_BASE_URL

class MovieRepository(private val retrofitClient: RetrofitClient) {

    suspend fun getPopularMovies() : List<Movie>{
        val movieList = retrofitClient.getClient().getPopularMovies()
        return convertDTOIntoUIModel(movieList.results)
    }

    suspend fun getLatestMovies(year: Int) : List<Movie>{
        val movieResponse = retrofitClient.getClient().getLatestMovies(year)
        return convertDTOIntoUIModel(movieResponse.results)
    }

    suspend fun getMovieByName(movieName: String) : List<Movie>{
        val movieResponse = retrofitClient.getClient().getMovieByName(movieName)
        return convertDTOIntoUIModel(movieResponse.results)
    }

    private fun convertDTOIntoUIModel(movies: List<com.everest.moviedb.network.models.Movie>): List<Movie> {
        return movies.map {
            Movie(
                it.id, it.overview, IMAGE_BASE_URL + it.poster_path, it.title
            )
        }
    }

}