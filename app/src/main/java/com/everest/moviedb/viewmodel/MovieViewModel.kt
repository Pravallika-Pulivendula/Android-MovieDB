package com.everest.moviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import kotlinx.coroutines.launch

class RepositoryViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val popularMovieList = MutableLiveData<List<Movie>>()
    val latestMovieList = MutableLiveData<List<Movie>>()
    val movieList = MutableLiveData<List<Movie>>()


    fun getPopularMovies() {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovies()
            if (response.isSuccessful) {
                val movieResponseList = response.body()
                popularMovieList.value = movieResponseList!!.results
            }
        }
    }

    fun getLatestMovies(year: Int) {
        viewModelScope.launch {
            val response = movieRepository.getLatestMovies(year)
            if (response.isSuccessful) {
                val movieResponseList = response.body()
                latestMovieList.value = movieResponseList!!.results
            }
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            val response = movieRepository.getMovieByName(movieName)
            if (response.isSuccessful) {
                val movieResponseList = response.body()
                movieList.value = movieResponseList!!.results
            }
        }
    }

}