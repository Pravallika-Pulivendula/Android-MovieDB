package com.everest.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import kotlinx.coroutines.launch

sealed class MovieData {
    class PopularMovies(val popularMovies: List<Movie>) : MovieData()
    class LatestMovies(val latestMovies: List<Movie>) : MovieData()
    class SearchMovies(val movieList: List<Movie>) : MovieData()
    class Error(val errorMessage: String?) : MovieData()
}

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieData = MutableLiveData<MovieData>()
    val movieData: LiveData<MovieData> = _movieData

    fun getPopularMovies() {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovies()
            try {
                val movieResponseList = response.body()
                _movieData.postValue(MovieData.PopularMovies(movieResponseList!!.results))
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }

    fun getLatestMovies(year: Int) {
        viewModelScope.launch {
            val response = movieRepository.getLatestMovies(year)
            try {
                val movieResponseList = response.body()
                _movieData.postValue(MovieData.LatestMovies(movieResponseList!!.results))
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            val response = movieRepository.getMovieByName(movieName)
            try {
                val movieResponseList = response.body()
                _movieData.postValue(MovieData.SearchMovies(movieResponseList!!.results))
            } catch (e: Exception) {
                _movieData.postValue(MovieData.Error(e.localizedMessage))
            }
        }
    }

}