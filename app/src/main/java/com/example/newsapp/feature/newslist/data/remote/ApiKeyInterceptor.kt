package com.example.newsapp.feature.newslist.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder()
            .addHeader("X-Api-Key", apiKey)  // NewsAPI supports this header
            .build()
        return chain.proceed(req)
    }
}