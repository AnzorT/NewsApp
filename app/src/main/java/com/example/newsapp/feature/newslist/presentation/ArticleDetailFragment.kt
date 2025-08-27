package com.example.newsapp.feature.newslist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleDetailBinding
import com.example.newsapp.feature.newslist.domain.NewsViewModel

class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        implementOnclickListeners()
    }

    private fun implementOnclickListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.loadNews()
            findNavController().navigate(R.id.action_articleDetailFragment_to_newsListFragment)
        }
    }

    private fun initViews() {
        with(binding) {
            tvTitle.text = viewModel.selectedArticleData?.title ?: ""
            tvDescription.text = viewModel.selectedArticleData?.description ?: ""
            imageViewImage.load(viewModel.selectedArticleData?.urlToImage ?: "") { crossfade(true) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}