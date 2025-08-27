package com.example.newsapp.feature.newslist.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsapp.feature.newslist.data.ArticleData
import com.example.newsapp.feature.newslist.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repo: NewsRepository
) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticleData>>(emptyList())
    val articles: StateFlow<List<ArticleData>> = _articles

    var selectedArticleData: ArticleData? = null
    fun loadNews(sourceId: String) {
        viewModelScope.launch {
            repo.getTopHeadlines(sourceId).collect { list ->
                _articles.value = list
            }
        }
    }

    companion object {
        const val SOURCE_TECHCRUNCH = "techcrunch"
    }
}

class NewsViewModelFactory(
    private val repo: NewsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repo) as T
    }
}