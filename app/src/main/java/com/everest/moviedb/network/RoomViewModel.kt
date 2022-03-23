package com.everest.moviedb.network

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.moviedb.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(context: Context) : ViewModel() {

    private val repository: MovieRepository
    private val allMovies: List<Movie>

    init {
        val movieDB = MovieDatabase.getDatabase(context).movieDao()
        repository = MovieRepository(movieDB)
        allMovies = repository.getAllMovies()
    }

    fun addMovies(movies: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovie(movies)
        }
    }

    fun getAllMovies(): List<Movie> {
        return allMovies
    }
}