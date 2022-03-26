package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieDatabase
import com.everest.moviedb.network.MovieRepository
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieData
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory

class PopularMovieFragment : Fragment(R.layout.fragment_popular_movie) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieRepository = MovieRepository(RetrofitClient(), MovieDatabase.getDatabase(requireContext()))

        Log.i("tyewfdgc",movieRepository.toString())

        movieViewModel = ViewModelProvider(
            this,
            ViewModelFactory(movieRepository)
        ).get(MovieViewModel::class.java)

        Log.i("tyertaf",movieViewModel.toString())

        recyclerView = view.findViewById(R.id.recyclerView)

        getPopularMovies()
    }

    private fun getPopularMovies() {
        Log.i("poioyu","poiouyutrtew")
        movieViewModel.movieData.observe(viewLifecycleOwner) { movieData ->
            when (movieData) {
                is MovieData.PopularMovies -> setRecyclerViewAdapter(movieData.popularMovies)
                is MovieData.Error -> movieData.errorMessage?.let { getToastMessage(it) }
            }
        }
        movieViewModel.getPopularMovies()

    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = context?.let { RecyclerViewAdapter(movies, it) }
        recyclerView.adapter = movieAdapter
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

