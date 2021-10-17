package com.visionDev.trendynews.ui.home.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.utils.MEDIA_STACK_API_START_OFFSET

class NewsApiPagingSource(
    val date: String,
    val keywords: String,
    val languages: String,
    val categories: String,
    val countries: String,
    val sort: String = MediaStackApi.Sort.PUB_DESC.str_type,
    val newsPublishers: String? = null,
    private val mediaStackApi: MediaStackApi
) : PagingSource<Int, ArticleUIState>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleUIState> {
        val mOffset = params.key ?: MEDIA_STACK_API_START_OFFSET

       return try {

           val res = mediaStackApi.fetchArticles(
               date,
               keywords,
               languages,
               categories,
               countries,
               offset,
               sort,
               newsPublishers
           )


           LoadResult.Page()
       }catch (e:Exception){
           LoadResult.Error(e)
       }

    }

    override fun getRefreshKey(state: PagingState<Int, ArticleUIState>): Int? {
        TODO("Learn And Write")
    }

    companion object {
        private const val TAG = "NewsApiPagingSource"
    }
}