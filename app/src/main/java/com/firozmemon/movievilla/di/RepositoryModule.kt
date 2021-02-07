package com.firozmemon.movievilla.di

import com.firozmemon.movievilla.datasource.DiscoverMovieDataSourceFactory
import com.firozmemon.movievilla.datasource.MovieDetailsRepository
import com.firozmemon.movievilla.datasource.SearchMovieRepository
import com.firozmemon.movievilla.db.FavMoviesDao
import com.firozmemon.movievilla.db.MyLocalDatabase
import org.koin.dsl.module

val repoModule = module {
    single<FavMoviesDao> { MyLocalDatabase.getDatabase(get()).favMoviesDao() }
    single<MovieDetailsRepository> { MovieDetailsRepository() }
    single<DiscoverMovieDataSourceFactory> { DiscoverMovieDataSourceFactory(get(), get()) }
    single<SearchMovieRepository> { SearchMovieRepository() }
}
