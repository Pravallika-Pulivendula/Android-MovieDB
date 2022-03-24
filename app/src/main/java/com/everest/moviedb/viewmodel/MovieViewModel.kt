package com.everest.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var _popularMovieList = MutableLiveData<List<Movie>>()
    var popularMovieList: LiveData<List<Movie>> = _popularMovieList

    private var _latestMovieList = MutableLiveData<List<Movie>>()
    var latestMovieList: LiveData<List<Movie>> = _latestMovieList

    private var _movies = MutableLiveData<List<Movie>>()
    var movies: LiveData<List<Movie>> = _movies

    fun getPopularMovies() {
        _popularMovieList.value = movieRepository.getPopularMovies()
    }

    fun getLatestMovies() {
        _latestMovieList.value = movieRepository.getLatestMovies()
    }

    fun getMovies(movieName: String) {
        _movies.value = movieRepository.getMovieByName(movieName)
    }

}