package com.everest.moviedb.network

import com.everest.moviedb.utils.API_KEY
import okhttp3.Interceptor

class Interceptor {
     fun addApiKey(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }
}