package com.example.newsapp.feature.newslist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.databinding.ArticleItemBinding
import com.example.newsapp.feature.newslist.data.ArticleData

class ArticleAdapter(
    private val onClick: (ArticleData) -> Unit
) : ListAdapter<ArticleData, ArticleAdapter.ArticleVH>(Diff) {

    object Diff : DiffUtil.ItemCallback<ArticleData>() {
        override fun areItemsTheSame(old: ArticleData, new: ArticleData) = old.url == new.url
        override fun areContentsTheSame(old: ArticleData, new: ArticleData) = old == new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val binding = ArticleItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleVH(binding, onClick)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.bind(getItem(position))
    }

    class ArticleVH(
        private val binding: ArticleItemBinding,
        private val onClick: (ArticleData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleData) = with(binding) {
            tvArticleTitle.text = item.title
            tvArticleDescription.text  = item.description
            imageViewArticleImage.load(item.urlToImage) {
                crossfade(true)
            }
            root.setOnClickListener { onClick(item) }
        }
    }
}