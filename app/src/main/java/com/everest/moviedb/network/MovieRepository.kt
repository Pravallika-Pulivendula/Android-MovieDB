package com.everest.moviedb.network

import com.everest.moviedb.models.Movie
import com.everest.moviedb.utils.IMAGE_BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val retrofitService: RetrofitService,
    private val movieDao: MovieDao
) {
    suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        var movieList: List<Movie>
        try {
            val movieResponse = retrofitService.getPopularMovies()
            movieList = convertDTOIntoUIModel(movieResponse.results)
            movieDao.addMovies(movieResponse.results)
        } catch (e: Exception) {
            movieList = movieDao.getPopularMoviesFromDb()
        }
        return@withContext movieList
    }

    suspend fun getLatestMovies(year: Int): List<Movie> = withContext(Dispatchers.IO) {
        var movieList: List<Movie>
        try {
            val movieResponse = retrofitService.getLatestMovies(year)
            movieList = convertDTOIntoUIModel(movieResponse.results)
            movieDao.addMovies(movieResponse.results)
        } catch (e: Exception) {
            movieList = movieDao.getLatestMoviesFromDb(year)
        }
        return@withContext movieList
    }

    suspend fun searchMovie(movieName: String): List<Movie> = withContext(Dispatchers.IO) {
        val movieList: List<Movie>
        val movieResponse = retrofitService.getMovieByName(movieName)
        movieList = convertDTOIntoUIModel(movieResponse.results)
        return@withContext movieList
    }

    private fun convertDTOIntoUIModel(movies: List<com.everest.moviedb.network.models.Movie>): List<Movie> {
        return movies.map {
            Movie(
                it.id, it.overview, IMAGE_BASE_URL + it.posterPath, it.title,
                it.releaseDate.subSequence(0, 4) as String
            )
        }
    }
}
