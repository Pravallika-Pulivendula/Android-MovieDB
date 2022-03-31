package com.everest.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieData = MutableLiveData<List<Movie>>()
    val movieData: LiveData<List<Movie>> = _movieData

    fun getPopularMovies() {
        viewModelScope.launch {
            _movieData.postValue(movieRepository.getPopularMovies())
        }
    }

    fun getLatestMovies(year: Int) {
        viewModelScope.launch {
            _movieData.postValue(movieRepository.getLatestMovies(year))
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            _movieData.postValue(movieRepository.searchMovie(movieName))
        }
    }
}