package com.everest.moviedb.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everest.moviedb.network.RetrofitRepository
import com.everest.moviedb.network.RoomViewModel

class ViewModelFactory constructor(private val context: Context?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            context?.let { RoomViewModel(it) } as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}