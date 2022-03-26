//package com.everest.moviedb.ui
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.SearchView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.everest.moviedb.R
//import com.everest.moviedb.adapters.RecyclerViewAdapter
//import com.everest.moviedb.databinding.ActivitySearchBinding
//import com.everest.moviedb.models.Movie
//import com.everest.moviedb.network.MovieRepository
//import com.everest.moviedb.utils.MOVIE_DETAILS
//import com.everest.moviedb.viewmodel.MovieViewModel
//import com.everest.moviedb.viewmodel.ViewModelFactory
//
//class SearchActivity : AppCompatActivity() {
//    private var searchView: SearchView? = null
//    private lateinit var binding: ActivitySearchBinding
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var movieRepository: MovieRepository
//    private lateinit var movieViewModel: MovieViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySearchBinding.inflate(layoutInflater)
//
//        movieRepository = MovieRepository(this)
//
//        movieViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(movieRepository)
//        ).get(MovieViewModel::class.java)
//
//        recyclerView = binding.recyclerView
//
//        setContentView(binding.root)
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.search_menu, menu)
//
//        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
//        searchView = searchItem.actionView as SearchView
//        searchView!!.setOnQueryTextListener(
//            object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    searchMovieByName(query)
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String): Boolean {
//                    searchMovieByName(newText)
//                    return false
//                }
//            })
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    private fun searchMovieByName(movieName: String) {
//        movieViewModel.getMovies(movieName)
//        movieViewModel.movies.value?.let { setRecyclerViewAdapter(it) }
//    }
//
//    private fun setRecyclerViewAdapter(movies: List<Movie>) {
//        recyclerView.layoutManager = LinearLayoutManager(this).apply {
//            orientation = LinearLayoutManager.VERTICAL
//        }
//        val movieAdapter = RecyclerViewAdapter(movies, this)
//        recyclerView.adapter = movieAdapter
//        movieAdapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                displayMovieDetails(position)
//            }
//        })
//    }
//
//    private fun displayMovieDetails(position: Int) {
//        val intent = Intent(this, DetailsActivity::class.java)
//        intent.putExtra(MOVIE_DETAILS, movieViewModel.movies.value?.get(position))
//        startActivity(intent)
//    }
//}