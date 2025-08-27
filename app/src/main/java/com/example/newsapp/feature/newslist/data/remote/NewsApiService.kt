package com.example.newsapp.feature.newslist.data.remote

import com.example.newsapp.feature.newslist.data.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun topHeadlinesBySource(
        @Query("category") category: String? = "Bitcoin",
        @Query("country") country: String = "us",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
    ): NewsApiResponse
}