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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    var isGetArticlesAlreadyInProgress = false
    var selectedArticleData: ArticleData? = null

    fun loadNews(subject: String) {
        if (isGetArticlesAlreadyInProgress) return
        isGetArticlesAlreadyInProgress = true
        _isLoading.value = true
        viewModelScope.launch {
            repo.getTopHeadlines(subject).collect { list ->
                _articles.value = list
            }
            isGetArticlesAlreadyInProgress = false
            _isLoading.value = false

        }

    }

    companion object {
        const val ARTICLE_SUBJECT_ONE_KEY = "business"
        const val ARTICLE_SUBJECT_TWO_KEY = "technology"
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