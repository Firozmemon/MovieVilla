package com.firozmemon.movievilla.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firozmemon.movievilla.datasource.MovieDetailsRepository
import com.firozmemon.movievilla.model.MovieResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val mtldFavMovies: MutableLiveData<List<MovieResult>> = MutableLiveData()
    val ldFavMovies: LiveData<List<MovieResult>>
        get() = mtldFavMovies

    private val compositeDisposable: CompositeDisposable by KoinJavaComponent.inject(
        CompositeDisposable::class.java
    )
    private val movieDetailsRepository: MovieDetailsRepository by KoinJavaComponent.inject(
        MovieDetailsRepository::class.java
    )

    fun getAllFavouriteMovies() {
        compositeDisposable.add(
            movieDetailsRepository.getAllFavouriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap { favMovieList ->
                    val movieResultList = ArrayList<MovieResult>()
                    favMovieList.forEach {
                        movieResultList.add(
                            MovieResult(
                                id = it.id,
                                title = it.title,
                                poster_path = it.imgUrl
                            )
                        )
                    }
                    Single.just(movieResultList)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mtldFavMovies.value = response
                }, {
                    println(it.localizedMessage)
                })
        )
    }

}