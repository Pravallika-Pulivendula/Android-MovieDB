package com.everest.moviedb.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,
)