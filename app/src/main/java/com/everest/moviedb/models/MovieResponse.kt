package com.everest.moviedb.models

import com.google.gson.JsonArray


class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)