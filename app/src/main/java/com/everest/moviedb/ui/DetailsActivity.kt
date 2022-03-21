package com.everest.moviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.everest.moviedb.databinding.ActivityDetailsBinding
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.RetrofitClient

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        displayMovieDetails()

    }

    private fun displayMovieDetails() {
        val movie: Movie? = intent.getParcelableExtra<Movie>("MOVIE_DETAILS")
        binding.movieTitleTV.text = movie?.title
        binding.movieDescriptionTV.text = movie?.overview
        Glide.with(this)
            .load(RetrofitClient.image_base_url + movie?.poster_path)
            .into(binding.movieThumbnailTV)
    }
}