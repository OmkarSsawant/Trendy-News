package com.visionDev.trendynews.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.visionDev.trendynews.R
import com.visionDev.trendynews.databinding.LayoutLoadRetryBinding

class NewsLoadStateAdapter(
    private val articlesAdapter: NewsListAdapter
) : LoadStateAdapter<NewsLoadStateAdapter.ArticleLoadingStateVH>() {

   inner class ArticleLoadingStateVH(val vb:LayoutLoadRetryBinding):RecyclerView.ViewHolder(vb.root)
   {
       init {
           vb.retry.setOnClickListener {
               articlesAdapter.retry()
           }
       }
   }
    override fun onBindViewHolder(holder: ArticleLoadingStateVH, loadState: LoadState) {
        holder.vb.loadState = loadState
        holder.vb.invalidateAll()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ArticleLoadingStateVH  = ArticleLoadingStateVH(
        LayoutLoadRetryBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_load_retry,parent,false)
        )
    )
}