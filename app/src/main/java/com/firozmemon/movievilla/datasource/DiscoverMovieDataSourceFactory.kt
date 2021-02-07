package com.firozmemon.movievilla.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.firozmemon.movievilla.api.NetworkApiService
import com.firozmemon.movievilla.model.MovieResult
import io.reactivex.disposables.CompositeDisposable

class DiscoverMovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkApiService)
    : DataSource.Factory<Int, MovieResult>() {

    val discoverMovieDataSourceLiveData = MutableLiveData<DiscoverMovieDataSource>()

    override fun create(): DataSource<Int, MovieResult> {
        val discoverMovieDataSource = DiscoverMovieDataSource(networkService, compositeDisposable)
        discoverMovieDataSourceLiveData.postValue(discoverMovieDataSource)
        return discoverMovieDataSource
    }
}