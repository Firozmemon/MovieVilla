package com.firozmemon.movievilla.api

import com.firozmemon.movievilla.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    const val TIME_OUT = 30000L
    const val BASE_URL = "https://api.themoviedb.org/"

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .build()

    fun getService(): NetworkApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()
        .create(NetworkApiService::class.java)

}