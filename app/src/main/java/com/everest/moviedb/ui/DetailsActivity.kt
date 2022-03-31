package com.everest.moviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.everest.moviedb.databinding.ActivityDetailsBinding
import com.everest.moviedb.models.Movie
import com.everest.moviedb.utils.MOVIE_DETAILS

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        displayMovieDetails()

    }

    private fun displayMovieDetails() {
        val movie: Movie? = intent.getParcelableExtra(MOVIE_DETAILS)
        binding.movieTitleTV.text = movie?.title
        binding.movieDescriptionTV.text = movie?.overview
        Glide.with(this)
            .load(movie?.posterPath)
            .into(binding.movieThumbnailTV)
    }
}