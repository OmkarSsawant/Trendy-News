package com.visionDev.trendynews.ui.home.main

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle

class NewsRepository
    (
    private val mediaStackApi: MediaStackApi,
    private val articlesTodayDAO: ArticlesTodayDAO
) {


   suspend fun getArticle(id:Long):ArticleUIState? = articlesTodayDAO.getArticleById(id)

    fun getNewsApiNetworkPagingSource(
        newsRequestInfo: NewsRequestInfo
    ):NewsApiPagingSource =  NewsApiPagingSource(newsRequestInfo,mediaStackApi)


    @ExperimentalPagingApi
    fun getNewsApiRemoteMediator(
        newsRequestInfo: NewsRequestInfo
    ):NewsApiRemoteMediator =  NewsApiRemoteMediator(newsRequestInfo,mediaStackApi,articlesTodayDAO)

    fun getNewsApiRMPagingSource():PagingSource<Int,TodayNewsArticle> = articlesTodayDAO.getArticles()
}