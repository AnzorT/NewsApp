package com.example.newsapp.feature.newslist.data.remote

import com.example.newsapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    fun createNewsApi(): NewsApiService {
        require(BuildConfig.NEWS_API_KEY.isNotBlank()) { "NEWS_API_KEY is missing. Set it in local.properties" }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.NEWS_API_KEY))
            .addInterceptor(logging)
            .build()

        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NewsApiService::class.java)
    }
}