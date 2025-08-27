package com.example.newsapp.feature.newslist.data

import com.google.gson.annotations.SerializedName

data class NewsApiResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<ArticleData>
)