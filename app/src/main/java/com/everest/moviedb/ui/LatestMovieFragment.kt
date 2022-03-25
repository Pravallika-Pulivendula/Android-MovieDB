package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieData
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory
import java.util.*

class LatestMovieFragment : Fragment(R.layout.fragment_latest_movie) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitClient = RetrofitClient()

        movieViewModel = ViewModelProvider(
            this,
            ViewModelFactory(MovieRepository(retrofitClient))
        ).get(MovieViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)

        getLatestMovies()

    }

    private fun getLatestMovies() {
        movieViewModel.movieData.observe(viewLifecycleOwner) { movieData ->
            when (movieData) {
                is MovieData.LatestMovies -> setRecyclerViewAdapter(movieData.latestMovies)
                is MovieData.Error -> movieData.errorMessage?.let { getToastMessage(it) }
            }
        }
        movieViewModel.getLatestMovies(Calendar.YEAR)
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = context?.let { RecyclerViewAdapter(movies, it) }
        recyclerView.adapter = movieAdapter
        onItemClickListener(movieAdapter, movies)
    }

    private fun onItemClickListener(
        movieAdapter: RecyclerViewAdapter?,
        movies: List<Movie>
    ) {
        movieAdapter?.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                displayMovieDetails(movies[position])
            }
        })
    }

    private fun displayMovieDetails(movie: Movie) {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS, movie)
        startActivity(intent)
    }

    private fun getToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}