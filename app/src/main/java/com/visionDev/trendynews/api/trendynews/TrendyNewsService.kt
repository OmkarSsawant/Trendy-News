package com.visionDev.trendynews.api.trendynews

import retrofit2.http.POST

interface TrendyNewsService {

    @POST("bot/get-page-images")
    fun getImages(@retrofit2.http.Body page_links:List<String>):ArticleImagesResponse
}