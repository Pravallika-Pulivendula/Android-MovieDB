package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.databinding.ActivitySearchBinding
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieDatabase
import com.everest.moviedb.network.MovieRepository
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        movieRepository = MovieRepository(
            RetrofitClient().retrofitService,
            MovieDatabase.getDatabase(this).movieDao()
        )

        movieViewModel = ViewModelProvider(
            this,
            ViewModelFactory(movieRepository)
        ).get(MovieViewModel::class.java)

        recyclerView = binding.recyclerView

        setContentView(binding.root)

        searchMovieByName()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    movieViewModel.searchMovie(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    movieViewModel.searchMovie(newText)
                    return false
                }
            })
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchMovieByName() {
        movieViewModel.movieData.observe(this) {
            setRecyclerViewAdapter(it)
        }
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = RecyclerViewAdapter(movies, this)
        recyclerView.adapter = movieAdapter
        movieAdapter.setOnItemClickListener(itemClickListener)
    }

    private val itemClickListener = object : RecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            displayMovieDetails(movieViewModel.movieData.value!![position])
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS, movie)
        startActivity(intent)
    }
}