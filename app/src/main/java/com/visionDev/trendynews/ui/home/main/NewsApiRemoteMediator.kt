package com.visionDev.trendynews.ui.home.main


import androidx.paging.*
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle
import com.visionDev.trendynews.utils.MEDIA_STACK_PER_REQ
//TODO: Optimize
@ExperimentalPagingApi
class NewsApiRemoteMediator(
    private val newsRequestInfo: NewsRequestInfo,
    private val mediaStackApi: MediaStackApi,
    private val todayDAO: ArticlesTodayDAO
) : RemoteMediator<Int, ArticleUIState>() {


    init {

    }

    var offset:Int=0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleUIState>
    ): MediatorResult  = try {
        val res  = with(newsRequestInfo){
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
        val todaysArticles = res.data.map{ TodayNewsArticle.from(newsRequestInfo.categories,it)}
        todayDAO.saveArticles(todaysArticles)

        if(res.pagination.total > offset + MEDIA_STACK_PER_REQ){
            offset += MEDIA_STACK_PER_REQ
             MediatorResult.Success(endOfPaginationReached = false)
        }else{
            //TODO: Consider Remmainngs
             MediatorResult.Success(endOfPaginationReached = true)
        }

    }catch (e :Exception){
        MediatorResult.Error(e)
    }

}