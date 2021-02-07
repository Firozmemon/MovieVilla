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
import com.firozmemon.movievilla.model.search.SearchMovieResponse
import io.reactivex.Scheduler
import io.reactivex.Single
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class SearchMovieRepository {

    private val TAG = "SearchMovieRepository"

    private val application: MovieVillaApplication by KoinJavaComponent.inject(
        MovieVillaApplication::class.java
    )
    private val networkApiService: NetworkApiService by KoinJavaComponent.inject(NetworkApiService::class.java)

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
    fun searchMovieList(query: String): Single<SearchMovieResponse> = Single.create { e ->
        if (isNetworkConnected()) {
            networkApiService.searchMovieQueryList(query)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                    { searchMovieResponse ->
                        searchMovieResponse?.let { response ->
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

}
