package com.example.newsapp.feature.newslist.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.BuildConfig
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.feature.newslist.data.remote.RetrofitProvider
import com.example.newsapp.feature.newslist.data.repository.NewsRepository
import com.example.newsapp.feature.newslist.domain.NewsViewModel
import com.example.newsapp.feature.newslist.domain.NewsViewModel.Companion.SOURCE_TECHCRUNCH
import com.example.newsapp.feature.newslist.domain.NewsViewModelFactory
import com.example.newsapp.feature.newslist.presentation.adapter.ArticleAdapter
import kotlinx.coroutines.launch


class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by activityViewModels {
        val api = RetrofitProvider.createNewsApi(BuildConfig.NEWS_API_KEY)
        val repo = NewsRepository(api)
        NewsViewModelFactory(repo)
    }

    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setupAdapter()
        subscribeObservers()
        viewModel.loadNews(SOURCE_TECHCRUNCH)
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = ArticleAdapter { article ->
            viewModel.selectedArticleData = article
            findNavController().navigate(R.id.action_newsListFragment_to_articleDetailFragment)
        }

        binding.recyclerArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerArticles.setHasFixedSize(true)
        binding.recyclerArticles.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}