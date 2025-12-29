package com.example.skypulse.network

import com.example.skypulse.BuildConfig
import com.example.skypulse.constants.METRIC_UNIT_OF_MEASUREMENT
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object Interceptors {
    val LOGGING_INTERCEPTOR =
        HttpLoggingInterceptor()
            .apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            }

    val QUERY_PARAMETER_INTERCEPTOR = Interceptor {
        val original = it.request()
        val originalUrl = original.url

        val newUrl =
            originalUrl
                .newBuilder()
                .addQueryParameter("appid", BuildConfig.WEATHER_API_KEY)
                .addQueryParameter("units", METRIC_UNIT_OF_MEASUREMENT)
                .build()

        val request =
            original
                .newBuilder()
                .url(newUrl)
                .build()

        it.proceed(request)
    }
}