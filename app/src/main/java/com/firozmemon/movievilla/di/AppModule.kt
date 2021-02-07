package com.firozmemon.movievilla.di

import com.firozmemon.movievilla.api.ApiClient
import com.firozmemon.movievilla.app.MovieVillaApplication
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCHEDULER = "main_scheduler"
const val IO_SCHEDULER = "io_scheduler"
const val COMPUTATION_SCHEDULER = "computation_scheduler"

private fun provideApiService() = ApiClient.getService()

private fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

private fun provideIOScheduler(): Scheduler = Schedulers.io()

private fun provideComputationScheduler(): Scheduler = Schedulers.computation()

private fun provideCompositeDisposable() = CompositeDisposable()

val appModule = module {
    single { androidApplication() as MovieVillaApplication }
    single { provideApiService() }
    single(qualifier = named(MAIN_SCHEDULER)) { provideMainScheduler() }
    single(qualifier = named(IO_SCHEDULER)) { provideIOScheduler() }
    single(qualifier = named(COMPUTATION_SCHEDULER)) { provideComputationScheduler() }
    factory { provideCompositeDisposable() }
}