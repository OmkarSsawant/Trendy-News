package com.visionDev.trendynews.api.news

import androidx.paging.PagingSource
import com.visionDev.trendynews.api.news.model.TechFashApiResponse
import com.visionDev.trendynews.common.ArticleUIState
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TechFashApi {
    @GET("tech/news/{category}")
   suspend fun getArticles(
        @Path("category") category: String,
        @Query("page") page: Int
    ): TechFashApiResponse
}