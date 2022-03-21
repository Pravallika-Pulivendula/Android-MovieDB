package com.everest.moviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.everest.moviedb.viewmodel.MovieViewModel
import com.everest.moviedb.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel = activity?.let { ViewModelProvider(it)[MovieViewModel::class.java] }
            ?: throw RuntimeException("Not a Activity")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieTitleTV.text = viewModel.movies.value?.title
        binding.movieDescriptionTV.text = viewModel.movies.value?.overview
        Glide.with(requireActivity())
            .load("https://image.tmdb.org/t/p/w500" + viewModel.movies.value?.poster_path)
            .into(binding.movieThumbnailTV)
    }

}