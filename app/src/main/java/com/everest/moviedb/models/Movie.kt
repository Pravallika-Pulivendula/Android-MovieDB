package com.everest.moviedb.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
class Movie(
    val id: Int,
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @PrimaryKey
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,
) : Parcelable