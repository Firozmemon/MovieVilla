package com.firozmemon.movievilla.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firozmemon.movievilla.datasource.MovieDetailsRepository
import com.firozmemon.movievilla.datasource.SearchMovieRepository
import com.firozmemon.movievilla.model.MovieResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent

class SearchMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val mtldSearchMovies: MutableLiveData<List<MovieResult>> = MutableLiveData()
    val ldSearchMovies: LiveData<List<MovieResult>>
        get() = mtldSearchMovies

    private val compositeDisposable: CompositeDisposable by KoinJavaComponent.inject(
        CompositeDisposable::class.java
    )
    private val searchMovieRepository: SearchMovieRepository by KoinJavaComponent.inject(
        SearchMovieRepository::class.java
    )

    fun searchMovies(searchQuery: String) {
        compositeDisposable.add(
            searchMovieRepository.searchMovieList(searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mtldSearchMovies.value = response.results
                }, {
                    println(it.localizedMessage)
                })
        )
    }

}