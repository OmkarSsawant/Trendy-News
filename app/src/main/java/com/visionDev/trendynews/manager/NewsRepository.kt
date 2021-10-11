package com.visionDev.trendynews.manager

import com.visionDev.trendynews.api.news.ApiResponse
import com.visionDev.trendynews.api.news.NewsDataApiService
import com.visionDev.trendynews.db.saved_articles.SavedArticlesDAO
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository
constructor(
    private val newsApiMediator: NewsApiMediator
) {


}