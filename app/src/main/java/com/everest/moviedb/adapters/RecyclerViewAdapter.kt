package com.everest.moviedb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.everest.moviedb.R
import com.everest.moviedb.models.Movie
import com.everest.moviedb.network.RetrofitClient

class RecyclerViewAdapter(
    private var movies: List<Movie>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapter.MovieItemViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class MovieItemViewHolder(view: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {

        val movieTitle: TextView = view.findViewById(R.id.movieTitle)
        val movieDescription: TextView = view.findViewById(R.id.movieDescription)
        val movieThumbnail: ImageView = view.findViewById(R.id.movieThumbnail)

        init {
            view.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_items, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitle.text = movie.title
        holder.movieDescription.text = movie.overview
        Glide.with(context)
            .load(RetrofitClient.image_base_url + movie.poster_path)
            .into(holder.movieThumbnail)
    }

    override fun getItemCount(): Int = movies.size
}