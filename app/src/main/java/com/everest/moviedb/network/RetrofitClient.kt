package com.everest.moviedb.network

import com.everest.moviedb.utils.API_KEY
import com.everest.moviedb.utils.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    var retrofitService: RetrofitService? = null

    fun getClient(): RetrofitService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain -> return@addInterceptor addApiKey(chain) }
            .build()

        if (retrofitService == null) {
            retrofitService = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL).build().create(RetrofitService::class.java)
        }
        return retrofitService!!
    }

    private fun addApiKey(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }
}
