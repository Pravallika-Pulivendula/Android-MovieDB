package com.everest.moviedb.network

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.everest.moviedb.models.Movie
import com.everest.moviedb.models.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRepository(context: Context) {
    private var movieDao: MovieDao
    private var movieDatabase: MovieDatabase = MovieDatabase.getDatabase(context)
    private var allMovies: List<Movie>
    private var retrofitClient: RetrofitClient
    private var movies = MutableLiveData<List<Movie>>()

    init {
        movieDao = movieDatabase.movieDao()
        allMovies = movieDao.getPopularMovies()
        retrofitClient = RetrofitClient()
    }

    fun getPopularMovies(): List<Movie> {
        val response = retrofitClient.getClient().getPopularMovies(RetrofitClient.api_key)
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                movieDao.addMovies(movieResponseList.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })
        return movieDao.getPopularMovies()
    }

    fun getLatestMovies(): List<Movie> {
        val response = retrofitClient.getClient().getLatestMovies(RetrofitClient.api_key)
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                movieDao.addMovies(movieResponseList.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })
        return movieDao.getLatestMovies()
    }

    fun getMovieByName(movieName: String): List<Movie>? {
        val response = retrofitClient.getClient().getMovieByName(RetrofitClient.api_key, movieName)
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                movies.value = movieResponseList.results
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })
        return movies.value
    }
}
