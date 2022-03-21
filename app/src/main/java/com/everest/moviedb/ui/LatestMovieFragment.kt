package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.network.RetrofitRepository
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.RepositoryViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory
import java.util.*

class LatestMovieFragment : Fragment(R.layout.fragment_latest_movie) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var repositoryViewModel: RepositoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = activity?.let { ViewModelProvider(it)[MovieViewModel::class.java] }
            ?: throw RuntimeException("Not a Activity")

        val retrofitClient = RetrofitClient()

        repositoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitRepository(retrofitClient.getClient()))
        ).get(RepositoryViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)

        getLatestMovies()

    }

    private fun getLatestMovies() {
        repositoryViewModel.latestMovieList.observe(viewLifecycleOwner, Observer {
            setRecyclerViewAdapter(it)
        })
        repositoryViewModel.getLatestMovie(Calendar.YEAR)
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = context?.let { RecyclerViewAdapter(movies, it) }
        recyclerView.adapter = movieAdapter
        movieAdapter?.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                movieViewModel.setMovie(movies[position])
                displayMovieDetails()
            }
        })
    }

    private fun displayMovieDetails() {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS, movieViewModel.movies.value)
        startActivity(intent)
    }
}