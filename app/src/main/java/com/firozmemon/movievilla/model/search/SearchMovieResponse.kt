package com.firozmemon.movievilla.model.search

import com.firozmemon.movievilla.model.MovieResult

data class SearchMovieResponse(
    val page: Int = 0,
    val results: List<MovieResult>? = null,
    val total_pages: Int = 0,
    val total_results: Int = 0
)