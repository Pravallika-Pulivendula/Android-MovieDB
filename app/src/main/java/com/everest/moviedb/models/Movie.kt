package com.everest.moviedb.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String
):Parcelable