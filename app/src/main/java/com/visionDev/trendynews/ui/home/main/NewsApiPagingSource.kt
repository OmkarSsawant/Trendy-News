package com.visionDev.trendynews.ui.home.main

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.utils.MEDIA_STACK_API_START_OFFSET
import com.visionDev.trendynews.utils.MEDIA_STACK_PER_REQ

class NewsApiPagingSource(
    private val newsRequestInfo: NewsRequestInfo,
    private val mediaStackApi: MediaStackApi
) : PagingSource<Int, ArticleUIState>() {

        var offset:Int=0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleUIState> {
        val mOffset = params.key ?: MEDIA_STACK_API_START_OFFSET

       return try {

           val res = with(newsRequestInfo){
               mediaStackApi.fetchArticles(
                   date,
                   languages,
                   categories,
                   countries,
                   keywords,
                   offset,
                   sort.str_type,
                   newsPublishers
               )
           }
           offset += MEDIA_STACK_PER_REQ
           Log.i(TAG, "load: ${res.data}")
           LoadResult.Page(
               data = res.data,
              prevKey = (offset- MEDIA_STACK_PER_REQ).coerceAtLeast(0),
              nextKey = offset + MEDIA_STACK_PER_REQ
           )
       }catch (e:Exception){
           Log.e(TAG, "load: $e", )
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