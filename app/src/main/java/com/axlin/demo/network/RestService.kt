package com.axlin.demo.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestService {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gSonBuilder: Gson = GsonBuilder().setLenient().create()

    val service: APIInterface
        get() {
            return Retrofit.Builder()
                .baseUrl("http://192.168.0.105:8080")
                .addConverterFactory(GsonConverterFactory.create(gSonBuilder))
                .client(okHttpClient)
                .build()
                .create(APIInterface::class.java)
        }
}
