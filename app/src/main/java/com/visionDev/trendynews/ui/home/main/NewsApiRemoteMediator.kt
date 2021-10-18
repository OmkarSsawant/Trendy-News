package com.visionDev.trendynews.ui.home.main


import android.util.Log
import androidx.paging.*
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle
import com.visionDev.trendynews.utils.MEDIA_STACK_PER_REQ

@ExperimentalPagingApi
class NewsApiRemoteMediator(
    private val newsRequestInfo: NewsRequestInfo,
    private val mediaStackApi: MediaStackApi,
    private val todayDAO: ArticlesTodayDAO
) : RemoteMediator<Int, TodayNewsArticle>() {

    var offset: Int = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TodayNewsArticle>
    ): MediatorResult {

        /*
        * For Testing purpose and to prevent requests
        * the articles from cached db will be used
        * */
        return MediatorResult.Success(endOfPaginationReached = true)

        when (loadType) {
            /*No Fetch Previous page required*/
            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.lastItemOrNull() ?: return MediatorResult.Success(
                endOfPaginationReached = true
            )
            LoadType.REFRESH -> { }
        }
        try {

            val res = with(newsRequestInfo) {
                mediaStackApi.fetchArticles(
                    date,
                    languages,
                    categories,
                    countries,
                    keywords,
                    offset,
                    sort.str_type,
                    newsPublishers,
                    limit = state.config.pageSize
                )
            }
            offset += MEDIA_STACK_PER_REQ
            val todaysArticles =
                res.data.map { TodayNewsArticle.from(newsRequestInfo.categories, it) }
            Log.i(TAG, "load: $todaysArticles")
            todayDAO.saveArticles(todaysArticles)

            if (res.pagination.count < MEDIA_STACK_PER_REQ) {
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                return MediatorResult.Success(endOfPaginationReached = false)
            }

        } catch (e: Exception) {
            Log.e(TAG, "load: $e")
            return MediatorResult.Error(e)
        }
    }


    companion object {
        private const val TAG = "NewsApiRemoteMediator"
    }
}