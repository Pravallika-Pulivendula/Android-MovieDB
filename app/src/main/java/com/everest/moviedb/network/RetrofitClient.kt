package com.everest.moviedb.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val BASE_URL: String = "https://api.themoviedb.org/3/"

    companion object {
        const val api_key: String = "e857bbab0881a32fb4ca66325107a830"
    }

    var retrofitService: RetrofitService? = null

    fun getClient(): RetrofitService {
        val okHttpClient = OkHttpClient.Builder().build()
        if (retrofitService == null) {
            retrofitService = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL).build().create(RetrofitService::class.java)
        }
        return retrofitService!!
    }
}
