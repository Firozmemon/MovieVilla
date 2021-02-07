package com.firozmemon.movievilla.app

import android.app.Application
import com.firozmemon.movievilla.di.appModule
import com.firozmemon.movievilla.di.repoModule
import com.firozmemon.movievilla.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieVillaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieVillaApplication)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }

}