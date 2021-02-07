package com.firozmemon.movievilla.api

import com.firozmemon.movievilla.BuildConfig
import com.firozmemon.movievilla.model.DiscoverMovieResponse
import com.firozmemon.movievilla.model.details.MovieDetailsResponse
import com.firozmemon.movievilla.model.search.SearchMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApiService {

    @GET("3/discover/movie")
    fun getDiscoverMovies(@Query("page") page: Int,
                          @Query("api_key") api_key: String = BuildConfig.TMDB_API_KEY): Single<DiscoverMovieResponse>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(@Path(value = "movie_id", encoded = true) movie_id: Int,
                        @Query("api_key") api_key: String = BuildConfig.TMDB_API_KEY): Single<MovieDetailsResponse>

    @GET("3/search/movie")
    fun searchMovieQueryList(@Query(value = "query") query: String,
                             @Query("api_key") api_key: String = BuildConfig.TMDB_API_KEY): Single<SearchMovieResponse>

}