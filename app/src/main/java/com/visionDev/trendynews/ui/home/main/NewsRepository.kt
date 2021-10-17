package com.visionDev.trendynews.ui.home.main

import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo

class NewsRepository
    (
    private val mediaStackApi: MediaStackApi
) {

    fun getNewsApiPagingSource(
        newsRequestInfo: NewsRequestInfo
    ):NewsApiPagingSource =  NewsApiPagingSource(newsRequestInfo,mediaStackApi)



}