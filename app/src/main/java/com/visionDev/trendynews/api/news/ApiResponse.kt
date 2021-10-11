package com.visionDev.trendynews.api.news

import com.visionDev.trendynews.api.model.NewsArticle

class ApiResponse(
    val status:String,
    val totalResults:Int,
    val results:Array<NewsArticle>,
    val nextPage:Int
)