package com.example.newsapp.feature.newslist.data.repository

import com.example.newsapp.feature.newslist.data.ArticleData
import com.example.newsapp.feature.newslist.data.remote.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class NewsRepository(
    private val api: NewsApiService
) {
    fun getTopHeadlines(sourceId: String): Flow<List<ArticleData>> = flow {
        val response = api.topHeadlinesBySource(sourceId)
        emit(response.articles)
    }
}