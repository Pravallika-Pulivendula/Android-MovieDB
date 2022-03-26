package com.everest.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import kotlinx.coroutines.launch

sealed class MovieData {
    class Data(val movieList: List<Movie>) : MovieData()
    class Error(val errorMessage: String?) : MovieData()
}

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieData = MutableLiveData<MovieData>()
    val movieData: LiveData<MovieData> = _movieData

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies()
                _movieData.postValue(MovieData.Data(response))
                movieRepository.addMovies(response)
            } catch (e: java.lang.Exception) {
                _movieData.postValue(MovieData.Data(movieRepository.getPopularMoviesFromDb()))
            }
        }
    }

    fun getLatestMovies(year: Int) {
        viewModelScope.launch {
            try {
                val response = movieRepository.getLatestMovies(year)
                _movieData.postValue(MovieData.Data(response))
                movieRepository.addMovies(response)
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Data(movieRepository.getLatestMoviesFromDb(year)))
            }
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            try {
                val response = movieRepository.searchMovie(movieName)
                _movieData.postValue(MovieData.Data(response))
                movieRepository.addMovies(response)
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }
}