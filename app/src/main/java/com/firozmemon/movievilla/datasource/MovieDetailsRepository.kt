package com.firozmemon.movievilla.datasource

import android.content.Context
import android.net.ConnectivityManager
import com.firozmemon.movievilla.api.NetworkApiService
import com.firozmemon.movievilla.app.MovieVillaApplication
import com.firozmemon.movievilla.db.FavMovies
import com.firozmemon.movievilla.db.FavMoviesDao
import com.firozmemon.movievilla.di.IO_SCHEDULER
import com.firozmemon.movievilla.di.MAIN_SCHEDULER
import com.firozmemon.movievilla.model.details.MovieDetailsResponse
import io.reactivex.Scheduler
import io.reactivex.Single
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class MovieDetailsRepository {

    private val TAG = "MovieDetailsRepository"

    private val application: MovieVillaApplication by KoinJavaComponent.inject(
        MovieVillaApplication::class.java
    )
    private val networkApiService: NetworkApiService by KoinJavaComponent.inject(NetworkApiService::class.java)
    private val favMoviesDao: FavMoviesDao by KoinJavaComponent.inject(FavMoviesDao::class.java)

    private val ioScheduler: Scheduler by KoinJavaComponent.inject(
        Scheduler::class.java,
        qualifier = named(IO_SCHEDULER)
    )

    private val mainScheduler: Scheduler by KoinJavaComponent.inject(
        Scheduler::class.java,
        qualifier = named(MAIN_SCHEDULER)
    )

    ///////////////////////////////////////////////////////////////////////////
    // API calls
    ///////////////////////////////////////////////////////////////////////////
    fun getMovieDetails(movieId: Int): Single<MovieDetailsResponse> = Single.create { e ->
        if (isNetworkConnected()) {
            networkApiService.getMovieDetails(movieId)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                    { movieDetailsResponse ->
                        movieDetailsResponse?.let { response ->
                            if (e.isDisposed.not()) e.onSuccess(response)
                        } ?: run {
                            if (e.isDisposed.not()) e.onError(Throwable("No Data Found"))
                        }

                    },
                    { if (e.isDisposed.not()) e.onError(Throwable("Something went Wrong ${it.localizedMessage}")) })
        } else {
            if (e.isDisposed.not()) e.onError(Throwable("Internet not available"))
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    ///////////////////////////////////////////////////////////////////////////
    // Database
    ///////////////////////////////////////////////////////////////////////////
    fun getAllFavouriteMovies(): Single<List<FavMovies>> = Single.create {
        val moviesList = favMoviesDao.getAllFavMovies()
        it.onSuccess(moviesList!!)
    }

    fun isFavouriteMovie(mid: Int): Boolean {
        val favMovie = favMoviesDao.getFavMoviesById(mid)
        return favMovie != null
    }

    fun insertNewMoviesList(favMoviesList: List<FavMovies>): Single<Boolean> =
        Single.create {
            favMoviesList.forEach {
                insertNewMovie(it)
            }
            it.onSuccess(true)
        }

    private fun insertNewMovie(favMovies: FavMovies) {
        favMoviesDao.insert(favMovies)
    }

    fun deleteMoviesData(favMovies: FavMovies): Single<Boolean> = Single.create {
        favMoviesDao.deleteFavMovies(favMovies.id)
        it.onSuccess(true)
    }

}
