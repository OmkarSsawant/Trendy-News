package com.visionDev.trendynews.ui.home.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.db.SharedPrefsManager
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle
import com.visionDev.trendynews.utils.MEDIA_STACK_PER_REQ
import com.visionDev.trendynews.utils.getTodayDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


/***
 * i  : Observe todays_articles table and feed in recyclerView
 * ii : manage user request when user reach end of page
 * Get Selected Categories in shared prefs
 * Initial Req Request At : http://api.mediastack.com/v1/news?date=2021-09-18&languages=en&categories=technology%2Centertainment%2Cpolitics&offset=0&sort=popularity&limit=100&access_key=e7abe78a0ca62726103695c1c0e992fe
 * */
class NewsViewModel
constructor(application: Application, private val  newsRepository: NewsRepository) :
    AndroidViewModel(application) {

    private val prefsManager: SharedPrefsManager = SharedPrefsManager(application)

    @ExperimentalPagingApi
    val todayNewsArticles: Flow<PagingData<TodayNewsArticle>> =
        Pager(
            config = PagingConfig(
                MEDIA_STACK_PER_REQ,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = newsRepository.getNewsApiRemoteMediator(prefsManager.buildFromPreferredNewsRequestInfo {
                date = getTodayDate()
                sort  = MediaStackApi.Sort.POPULARITY
            }),
            pagingSourceFactory = { newsRepository.getNewsApiRMPagingSource() }
        ).flow

    fun getArticleById(id:Long,action:(ArticleUIState)->Unit) {
        viewModelScope.launch {
            newsRepository.getArticle(id)?.let { action(it) }
        }
    }

}