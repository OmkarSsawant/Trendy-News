package com.visionDev.trendynews.ui.home.main

import com.visionDev.trendynews.api.news.MediaStackApi

class NewsRepository
    (
    private val mediaStackApi: MediaStackApi
) {

  fun getNewsApiPagingSource(category:String) = NewsApiPagingSource(category, mediaStackApi)
}