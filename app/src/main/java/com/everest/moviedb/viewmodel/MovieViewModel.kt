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
            _movieData.postValue(MovieData.Data(movieRepository.getPopularMovies()))
        }
    }

    fun getLatestMovies(year: Int) {
        viewModelScope.launch {
            _movieData.postValue(MovieData.Data(movieRepository.getLatestMovies(year)))
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            _movieData.postValue(MovieData.Data(movieRepository.searchMovie(movieName)))
        }
    }
}