package com.visionDev.trendynews.ui.home.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.visionDev.trendynews.common.ArticleUIState
import kotlinx.coroutines.flow.Flow


/***
 * i  : Observe todays_articles table and feed in recyclerView
 * ii : manage user request when user reach end of page
 * */
class NewsViewModel
    constructor(application: Application,newsRepository: NewsRepository) : AndroidViewModel(application) {

    val newsArticles: (String) -> Flow<PagingData<ArticleUIState>> = { category ->
        Pager(
            config = PagingConfig(10, enablePlaceholders = false),
            pagingSourceFactory = { newsRepository.getNewsApiPagingSource(category) },
            initialKey = 1
        ).flow
    }


    }