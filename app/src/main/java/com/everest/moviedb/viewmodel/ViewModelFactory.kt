package com.everest.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everest.moviedb.network.RetrofitRepository

class ViewModelFactory constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {
            RepositoryViewModel(this.retrofitRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}