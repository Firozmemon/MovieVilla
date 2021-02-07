package com.firozmemon.movievilla.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.firozmemon.movievilla.api.NetworkApiService
import com.firozmemon.movievilla.di.IO_SCHEDULER
import com.firozmemon.movievilla.di.MAIN_SCHEDULER
import com.firozmemon.movievilla.util.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import com.firozmemon.movievilla.model.MovieResult
import io.reactivex.Scheduler
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class DiscoverMovieDataSource(
        private val networkService: NetworkApiService,
        private val compositeDisposable: CompositeDisposable
)
    : PageKeyedDataSource<Int, MovieResult>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    private val ioScheduler: Scheduler by KoinJavaComponent.inject(
        Scheduler::class.java,
        qualifier = named(IO_SCHEDULER)
    )

    private val mainScheduler: Scheduler by KoinJavaComponent.inject(
        Scheduler::class.java,
        qualifier = named(MAIN_SCHEDULER)
    )

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieResult>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                networkService.getDiscoverMovies(1)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    callback.onResult(response.results!!,
                                            null,
                                            2
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadInitial(params, callback) })
                                }
                        )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                networkService.getDiscoverMovies(params.key)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    callback.onResult(response.results!!,
                                            params.key + 1
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadAfter(params, callback) })
                                }
                        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
                    .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}