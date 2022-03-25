package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.databinding.ActivitySearchBinding
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.MovieRepository
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieData
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory

class SearchActivity : AppCompatActivity() {
    private var searchView: SearchView? = null
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        val retrofitClient = RetrofitClient()

        movieViewModel = ViewModelProvider(
            this,
            ViewModelFactory(MovieRepository(retrofitClient))
        ).get(MovieViewModel::class.java)

        recyclerView = binding.recyclerView

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView!!.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchMovieByName(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchMovieByName(newText)
                    return false
                }
            })
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchMovieByName(movieName: String) {
        movieViewModel.movieData.observe(this) { movieData ->
            when (movieData) {
                is MovieData.SearchMovies -> setRecyclerViewAdapter(movieData.movieList)
                is MovieData.Error -> movieData.errorMessage?.let { getToastMessage(it) }
            }
        }

        movieViewModel.searchMovie(movieName)
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = RecyclerViewAdapter(movies, this)
        recyclerView.adapter = movieAdapter
        setOnItemClickListener(movieAdapter, movies)
    }

    private fun setOnItemClickListener(
        movieAdapter: RecyclerViewAdapter,
        movies: List<Movie>
    ) {
        movieAdapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                displayMovieDetails(movies[position])
            }
        })
    }

    private fun displayMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS, movie)
        startActivity(intent)
    }

    private fun getToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}