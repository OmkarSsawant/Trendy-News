package com.visionDev.trendynews.ui.home.main


import androidx.paging.*
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.utils.MEDIA_STACK_API_START_OFFSET

@ExperimentalPagingApi
class NewsApiRemoteMediator(
    private val newsRequestInfo: NewsRequestInfo,
    private val mediaStackApi: MediaStackApi
) : RemoteMediator<Int, ArticleUIState>() {


    companion object {
        private const val TAG = "NewsApiPagingSource"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleUIState>
    ): MediatorResult {
     TODO("")

     /*
        val mOffset = params.key ?: MEDIA_STACK_API_START_OFFSET

        return try {

            val res = with(newsRequestInfo){
                mediaStackApi.fetchArticles(
                    date,
                    languages,
                    categories,
                    countries,
                    keywords,
                    mOffset,
                    sort.str_type,
                    newsPublishers
                )
            }
            MediatorResult.Success(
            endOfPaginationReached = true
            )
        }catch (e:Exception){
            MediatorResult.Error(e)
        }*/
    }
}