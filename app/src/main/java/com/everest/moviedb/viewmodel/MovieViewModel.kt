package com.everest.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import kotlinx.coroutines.launch

sealed class MovieData {
    class MovieList(val movieList: List<Movie>) : MovieData()
    class Error(val errorMessage: String?) : MovieData()
}

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieData = MutableLiveData<MovieData>()
    val movieData: LiveData<MovieData> = _movieData

    fun getPopularMovies() {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovies()
            try {
                _movieData.postValue(MovieData.MovieList(response))
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }

    fun getLatestMovies(year: Int) {
        viewModelScope.launch {
            val response = movieRepository.getLatestMovies(year)
            try {
                _movieData.postValue(MovieData.MovieList(response))
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            val response = movieRepository.getMovieByName(movieName)
            try {
                _movieData.postValue(MovieData.MovieList(response))
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }

}