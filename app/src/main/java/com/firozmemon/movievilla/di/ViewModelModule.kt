package com.firozmemon.movievilla.di

import com.firozmemon.movievilla.viewmodel.DiscoverMovieListViewModel
import com.firozmemon.movievilla.viewmodel.FavouriteMoviesViewModel
import com.firozmemon.movievilla.viewmodel.MovieDetailsViewModel
import com.firozmemon.movievilla.viewmodel.SearchMoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DiscoverMovieListViewModel() }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { FavouriteMoviesViewModel(get()) }
    viewModel { SearchMoviesViewModel(get()) }
}