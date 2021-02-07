package com.firozmemon.movievilla.model.details

data class MovieDetailsResponse(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val belongs_to_collection: BelongsToCollection? = null,
    val budget: Int = 0,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int = 0,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany>? = null,
    val production_countries: List<ProductionCountry>? = null,
    val release_date: String? = null,
    val revenue: Int = 0,
    val runtime: Int = 0,
    val spoken_languages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)