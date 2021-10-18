package com.visionDev.trendynews.ui.home.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.db.SharedPrefsManager
import com.visionDev.trendynews.utils.MEDIA_STACK_PER_REQ
import com.visionDev.trendynews.utils.getTodayDate
import kotlinx.coroutines.flow.Flow


/***
 * i  : Observe todays_articles table and feed in recyclerView
 * ii : manage user request when user reach end of page
 * Get Selected Categories in shared prefs
 * Initial Req http://api.mediastack.com/v1/news?date=2021-10-18&languages=en&sort=popularity&access_key=e7abe78a0ca62726103695c1c0e992fe
 * */
class NewsViewModel
constructor(application: Application, newsRepository: NewsRepository) :
    AndroidViewModel(application) {

    val prefsManager: SharedPrefsManager = SharedPrefsManager(application)

    val todayNewsArticles: Flow<PagingData<ArticleUIState>> =
        Pager(
            config = PagingConfig(
                MEDIA_STACK_PER_REQ,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = {
                newsRepository.getNewsApiPagingSource(
                    prefsManager.buildFromPreferredNewsRequestInfo {
                date = getTodayDate()
                    })
            },
            initialKey = 1
        ).flow


}