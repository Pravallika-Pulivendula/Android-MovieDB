package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory

class PopularMovieFragment : Fragment(R.layout.fragment_popular_movie) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieRepository = context?.let { MovieRepository(it) }!!

        movieViewModel = ViewModelProvider(
            this,
            ViewModelFactory(movieRepository)
        ).get(MovieViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)

        getPopularMovies()
    }

    private fun getPopularMovies() {
        movieViewModel.getPopularMovies()
        movieViewModel.popularMovieList.value?.let { setRecyclerViewAdapter(it) }

    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = context?.let { RecyclerViewAdapter(movies, it) }
        recyclerView.adapter = movieAdapter
        movieAdapter?.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                displayMovieDetails(position)
            }
        })
    }

    private fun displayMovieDetails(position: Int) {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS, movieViewModel.popularMovieList.value?.get(position))
        startActivity(intent)
    }

}

