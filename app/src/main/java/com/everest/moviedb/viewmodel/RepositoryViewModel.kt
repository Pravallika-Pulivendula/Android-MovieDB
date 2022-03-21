package com.everest.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everest.moviedb.models.Movie
import com.everest.moviedb.models.MovieResponse
import com.everest.moviedb.network.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(private val retrofitRepository: RetrofitRepository) : ViewModel() {
    private var _popularMovieList = MutableLiveData<List<Movie>>()
    var popularMovieList: LiveData<List<Movie>> = _popularMovieList

    private var _latestMovieList = MutableLiveData<List<Movie>>()
    var latestMovieList: LiveData<List<Movie>> = _latestMovieList

    private var _movie = MutableLiveData<List<Movie>>()
    var movie: LiveData<List<Movie>> = _movie

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun getPopularMovies() {
        val response = retrofitRepository.getPopularMovies()
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                _popularMovieList.postValue(movieResponseList.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun getLatestMovie(year: Int) {
        val response = retrofitRepository.getLatestMovies(year)
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                _latestMovieList.postValue(movieResponseList.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

    fun getMovieByName(movieName: String) {
        val response = retrofitRepository.searchMovieByName(movieName)
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                _movie.postValue(movieResponseList.results)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }

}