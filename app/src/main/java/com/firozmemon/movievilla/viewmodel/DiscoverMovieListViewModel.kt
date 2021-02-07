package com.firozmemon.movievilla.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.firozmemon.movievilla.datasource.DiscoverMovieDataSource
import com.firozmemon.movievilla.datasource.DiscoverMovieDataSourceFactory
import com.firozmemon.movievilla.model.MovieResult
import com.firozmemon.movievilla.util.State
import io.reactivex.disposables.CompositeDisposable
import org.koin.java.KoinJavaComponent

class DiscoverMovieListViewModel : ViewModel() {

    var movieResultList: LiveData<PagedList<MovieResult>>
    private val compositeDisposable: CompositeDisposable by KoinJavaComponent.inject(
        CompositeDisposable::class.java
    )
    private val pageSize = 5
    private val discoverMovieDataSourceFactory: DiscoverMovieDataSourceFactory by KoinJavaComponent.inject(
        DiscoverMovieDataSourceFactory::class.java
    )

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .build()
        movieResultList = LivePagedListBuilder<Int, MovieResult>(discoverMovieDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap<DiscoverMovieDataSource,
            State>(discoverMovieDataSourceFactory.discoverMovieDataSourceLiveData, DiscoverMovieDataSource::state)

    fun retry() {
        discoverMovieDataSourceFactory.discoverMovieDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return movieResultList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}