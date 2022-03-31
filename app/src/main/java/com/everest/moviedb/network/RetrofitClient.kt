package com.everest.moviedb.network

import com.everest.moviedb.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private fun getClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain -> return@addInterceptor Interceptor().addApiKey(chain) }
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL).build()
    }

    val retrofitService: RetrofitService by lazy {
        getClient().create(RetrofitService::class.java)
    }
}
