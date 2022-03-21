package com.everest.moviedb

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class PopularMovieFragment : Fragment(R.layout.fragment_popular_movie) {
    private lateinit var retrofitAPI: RetrofitApi
    private var movies: List<Movie> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var detailsFragment: DetailsFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        detailsFragment = DetailsFragment()
        movieViewModel = activity?.let { ViewModelProvider(it)[MovieViewModel::class.java] }
            ?: throw RuntimeException("Not a Activity")
        val retrofitClient = RetrofitClient()
        val retrofit = retrofitClient.getClient()
        retrofitAPI = retrofit.create(RetrofitApi::class.java)
        getMovie()
        recyclerView = view.findViewById(R.id.recyclerView)

    }

    private fun getMovie() {
        val call = retrofitAPI.getMovie(RetrofitClient.api_key)

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
                Toast.makeText(context, "yess", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        recyclerView.layoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val movieAdapter = context?.let { MovieAdapter(movies, it) }
        recyclerView.adapter = movieAdapter
        movieAdapter?.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                movieViewModel.setMovie(movies[position])
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_one, detailsFragment)
                    commit()
                }
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
    }
}

