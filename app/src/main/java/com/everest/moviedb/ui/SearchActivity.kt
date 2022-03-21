package com.everest.moviedb.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.R
import com.everest.moviedb.network.RetrofitApi
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.databinding.ActivitySearchBinding
import com.everest.moviedb.models.Movie
import com.everest.moviedb.models.MovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class SearchActivity : AppCompatActivity() {
    private var searchView: SearchView? = null
    private lateinit var binding: ActivitySearchBinding
    private lateinit var retrofitAPI: RetrofitApi
    private var movies: List<Movie> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        movieViewModel = let { ViewModelProvider(it)[MovieViewModel::class.java] }

        val retrofitClient = RetrofitClient()
        val retrofit = retrofitClient.getClient()
        retrofitAPI = retrofit.create(RetrofitApi::class.java)
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
        val call = retrofitAPI.getMovieByName(RetrofitClient.api_key, movieName)
        responseCallback(call, this@SearchActivity)
    }

    private fun responseCallback(call: Call<MovieResponse>, context: Context) {
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                val movieResponse = movieResponseList.results
                for (i in 0 until movieResponse.size()) {
                    val gson = Gson()
                    val listType: Type = object : TypeToken<Collection<Movie?>?>() {}.type
                    movies = gson.fromJson(movieResponse, listType)
                }
                setRecyclerViewAdapter(movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = RecyclerViewAdapter(movies, this)
        recyclerView.adapter = movieAdapter
        movieAdapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                movieViewModel.setMovie(movies[position])
                replaceFragment()
            }
        })
    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_one, DetailsFragment())
            addToBackStack(null)
            commit()
        }
    }
}