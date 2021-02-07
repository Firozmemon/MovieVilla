package com.firozmemon.movievilla.model

data class DiscoverMovieResponse(
    val page: Int = 0,
    val results: List<MovieResult>? = null,
    val total_pages: Int = 0,
    val total_results: Int = 0
)