package com.everest.moviedb.network

import com.everest.moviedb.models.Movie
import com.everest.moviedb.utils.IMAGE_BASE_URL


class MovieRepository(
    private val retrofitClient: RetrofitClient,
    private val movieDatabase: MovieDatabase
) {
    suspend fun getPopularMovies(): List<Movie> {
        val movieList = retrofitClient.getClient().getPopularMovies()
        return convertDTOIntoUIModel(movieList.results)
    }

    suspend fun getLatestMovies(year: Int): List<Movie> {
        val movieResponse = retrofitClient.getClient().getLatestMovies(year)
        return convertDTOIntoUIModel(movieResponse.results)
    }

    suspend fun searchMovie(movieName: String): List<Movie> {
        val movieResponse = retrofitClient.getClient().getMovieByName(movieName)
        return convertDTOIntoUIModel(movieResponse.results)
    }

    suspend fun addMovies(movies: List<Movie>) =
        movieDatabase.movieDao().addMovies(movies)

    suspend fun getPopularMoviesFromDb() =
        movieDatabase.movieDao().getPopularMoviesFromDb()

    suspend fun getLatestMoviesFromDb(year: Int) =
        movieDatabase.movieDao().getLatestMoviesFromDb(year)

    private fun convertDTOIntoUIModel(movies: List<com.everest.moviedb.network.models.Movie>): List<Movie> {
        return movies.map {
            Movie(
                it.id, it.overview, IMAGE_BASE_URL + it.posterPath, it.title, it.releaseDate
            )
        }
    }

}
