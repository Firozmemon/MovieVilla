package com.firozmemon.movievilla.model

data class MovieResult(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int = 0,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)