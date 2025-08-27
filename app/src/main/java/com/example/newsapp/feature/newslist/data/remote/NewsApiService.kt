package com.example.newsapp.feature.newslist.data.remote

import com.example.newsapp.feature.newslist.data.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun topHeadlinesBySource(
        @Query("sources") sourceId: String,
        @Query("pageSize") pageSize: Int = 20
    ): NewsApiResponse
}