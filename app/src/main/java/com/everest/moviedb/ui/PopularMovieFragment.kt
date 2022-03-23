package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everest.moviedb.R
import com.everest.moviedb.adapters.RecyclerViewAdapter
import com.everest.moviedb.models.Movie
import com.everest.moviedb.models.MovieResponse
import com.everest.moviedb.network.RetrofitClient
import com.everest.moviedb.network.RoomViewModel
import com.everest.moviedb.utils.MOVIE_DETAILS
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.viewmodel.RepositoryViewModel
import com.everest.moviedb.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMovieFragment : Fragment(R.layout.fragment_popular_movie) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var repositoryViewModel: RepositoryViewModel
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var retrofitClient: RetrofitClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = activity?.let { ViewModelProvider(it)[MovieViewModel::class.java] }
            ?: throw RuntimeException("Not a Activity")

        retrofitClient = RetrofitClient()

//        repositoryViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(RetrofitRepository(retrofitClient.getClient()))
//        ).get(RepositoryViewModel::class.java)

        roomViewModel = ViewModelProvider(
            this,
            ViewModelFactory(context)
        ).get(RoomViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)

        getPopularMovies()
    }

    private fun getPopularMovies() {
//        repositoryViewModel.popularMovieList.observe(viewLifecycleOwner, Observer {
//            setRecyclerViewAdapter(it)
//        })
//        repositoryViewModel.getPopularMovies()

        val response = retrofitClient.getClient().getPopularMovies(RetrofitClient.api_key)
        Log.i("ghsdv", response.toString())
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponseList = response.body()!!
                Log.i("gfdsg", movieResponseList.results[0].title)
                roomViewModel.addMovies(movieResponseList.results)
                setRecyclerViewAdapter(roomViewModel.getAllMovies())
                Log.i("hgsdvc",roomViewModel.getAllMovies().toString())
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.i("hgfdsg", "gsfd")
            }
        })
    }

    private fun setRecyclerViewAdapter(movies: List<Movie>) {
        roomViewModel.addMovies(movies)
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

