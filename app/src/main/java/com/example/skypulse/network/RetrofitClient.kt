package com.example.skypulse.network

import com.example.skypulse.constants.BASE_API_URL
import com.example.skypulse.constants.CONNECT_TIMEOUT
import com.example.skypulse.constants.READ_TIMEOUT
import com.example.skypulse.constants.WRITE_TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val okHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                addInterceptor(Interceptors.LOGGING_INTERCEPTOR)
                addInterceptor(Interceptors.QUERY_PARAMETER_INTERCEPTOR)
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            }
            .build()

    val API: Api =
        Retrofit
            .Builder()
            .apply {
                baseUrl(BASE_API_URL)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
            }
            .build()
            .create(Api::class.java)
}