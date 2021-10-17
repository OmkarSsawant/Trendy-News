package com.visionDev.trendynews.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.databinding.TileNewsBinding

class NewsListAdapter :
    PagingDataAdapter<ArticleUIState, NewsListAdapter.ArticleViewHolder>(ARTICLE_DIFF_TOOL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            TileNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position).let {
            holder.vb.article = it
            holder.vb.invalidateAll()
        }
    }


    inner class ArticleViewHolder( val vb: TileNewsBinding) : RecyclerView.ViewHolder(vb.tileRoot)
    companion object {
        val ARTICLE_DIFF_TOOL = object : DiffUtil.ItemCallback<ArticleUIState>() {
            override fun areItemsTheSame(
                oldItem: ArticleUIState,
                newItem: ArticleUIState
            ): Boolean = oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: ArticleUIState,
                newItem: ArticleUIState
            ): Boolean =
                (oldItem.link == newItem.link &&
                        oldItem.title == newItem.title &&
                        oldItem.pubDate == newItem.pubDate &&
                        oldItem.authors == newItem.authors
                        )
        }
    }
}