package com.visionDev.trendynews.api

class ApiResponse(
    val status:String,
    val totalResults:Int,
    val results:Array<TechArticle>,
    val nextPage:Int
)