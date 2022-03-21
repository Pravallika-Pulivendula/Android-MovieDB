package com.everest.moviedb.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.R
import com.everest.moviedb.network.RetrofitApi
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.models.Movie
import com.everest.moviedb.models.MovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class LatestMovieFragment : Fragment(R.layout.fragment_latest_movie) {
    private lateinit var retrofitAPI: RetrofitApi
    private var movies: List<Movie> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = activity?.let { ViewModelProvider(it)[MovieViewModel::class.java] }
            ?: throw RuntimeException("Not a Activity")

        val retrofitClient = RetrofitClient()
        val retrofit = retrofitClient.getClient()
        retrofitAPI = retrofit.create(RetrofitApi::class.java)
        recyclerView = view.findViewById(R.id.recyclerView)

        getLatestMovies()

    }

    private fun getLatestMovies() {
        val call = retrofitAPI.getLatestMovies(RetrofitClient.api_key)

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
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = context?.let { RecyclerViewAdapter(movies, it) }
        recyclerView.adapter = movieAdapter
        movieAdapter?.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                movieViewModel.setMovie(movies[position])
                replaceFragment()
            }
        })
    }

    private fun replaceFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_one, DetailsFragment())
            addToBackStack(null)
            commit()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}