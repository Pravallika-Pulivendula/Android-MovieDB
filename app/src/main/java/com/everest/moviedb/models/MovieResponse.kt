package com.everest.moviedb

import com.google.gson.JsonArray


class MovieResponse(
    val page: Int,
    val results: JsonArray,
    val total_results: Int,
    val total_pages: Int
)