package com.everest.moviedb.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val id: Int,
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,
) : Parcelable