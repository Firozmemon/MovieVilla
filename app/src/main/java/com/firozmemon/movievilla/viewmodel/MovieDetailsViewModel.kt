package com.firozmemon.movievilla.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firozmemon.movievilla.datasource.MovieDetailsRepository
import com.firozmemon.movievilla.db.FavMovies
import com.firozmemon.movievilla.model.details.MovieDetailsResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val mtldMovieDetails: MutableLiveData<MovieDetailsResponse> = MutableLiveData()
    val ldMovieDetails: LiveData<MovieDetailsResponse>
        get() = mtldMovieDetails
    private val mtldIsFavourite: MutableLiveData<Boolean> = MutableLiveData(false)
    val ldIsFavourite: LiveData<Boolean>
        get() = mtldIsFavourite

    private val compositeDisposable: CompositeDisposable by KoinJavaComponent.inject(
        CompositeDisposable::class.java
    )
    private val movieDetailsRepository: MovieDetailsRepository by KoinJavaComponent.inject(
        MovieDetailsRepository::class.java
    )

    fun getMovieDetails(movieId: Int) {
        compositeDisposable.add(
            movieDetailsRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap { movieResponse ->
                    val isInFav = movieDetailsRepository.isFavouriteMovie(movieId)
                    Single.just(Pair(movieResponse, isInFav))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ response ->
                    mtldIsFavourite.value = response.second
                    mtldMovieDetails.value = response.first
                },{
                    println(it.localizedMessage)
                })
        )
    }

    fun addToFavourite(movieDetailsResponse: MovieDetailsResponse) {
        compositeDisposable.add(
            movieDetailsRepository.insertNewMoviesList(ArrayList<FavMovies>().apply {
                add(
                    FavMovies(
                        movieDetailsResponse.id,
                        movieDetailsResponse.title!!,
                        movieDetailsResponse.poster_path!!
                    )
                )
            })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    mtldIsFavourite.value = response
                }
        )
    }

    fun removeFromFavourite(movieDetailsResponse: MovieDetailsResponse) {
        compositeDisposable.add(
            movieDetailsRepository.deleteMoviesData(
                FavMovies(
                    movieDetailsResponse.id,
                    movieDetailsResponse.title!!,
                    movieDetailsResponse.poster_path!!
                )
            )
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    mtldIsFavourite.value = !response
                }
        )
    }
}