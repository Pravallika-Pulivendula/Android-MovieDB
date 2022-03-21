package com.everest.moviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everest.moviedb.models.Movie

class MovieViewModel : ViewModel() {
    private var _movies = MutableLiveData<Movie>()
    var movies: LiveData<Movie> = _movies

    private var _movieName = MutableLiveData<String>()
    var movieName: LiveData<String> = _movieName

    fun setMovie(movie: Movie) {
        this._movies.value =
            Movie(
                movie.adult,
                movie.backdrop_path,
                movie.genre_ids,
                movie.id,
                movie.original_language,
                movie.original_title,
                movie.overview,
                movie.popularity,
                movie.poster_path,
                movie.release_date,
                movie.title,
                movie.video,
                movie.vote_average,
                movie.vote_count
            )
    }
}