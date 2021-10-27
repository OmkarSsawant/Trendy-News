package com.visionDev.trendynews.ui.home.main

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.visionDev.trendynews.api.news.TechFashApi
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle
import com.visionDev.trendynews.utils.MEDIA_STACK_PER_REQ

@OptIn(ExperimentalPagingApi::class)
class TechFashApiMediator
    (
    private val techFashApi: TechFashApi,
    private val dao: ArticlesTodayDAO,
    private val category: String
) : RemoteMediator<Int, TodayNewsArticle>() {

    var page = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TodayNewsArticle>
    ): MediatorResult {
        when (loadType) {
            /*No Fetch Previous page required*/
            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.lastItemOrNull() ?: return MediatorResult.Success(
                endOfPaginationReached = true
            )
            LoadType.REFRESH -> {
            }
        }
        return try {
            val res = techFashApi.getArticles(category,page )
            val todaysArticles =
                res.news.map { TodayNewsArticle.from(category, it) }
            Log.i(NewsApiRemoteMediator.TAG, "load: $todaysArticles")
            dao.saveArticles(todaysArticles)
            page++
            MediatorResult.Success(endOfPaginationReached = res.pagination.endOfResult)
        } catch (e: Exception) {
            Log.e(NewsApiRemoteMediator.TAG, "load: $e")
            MediatorResult.Error(e)
        }
    }
}