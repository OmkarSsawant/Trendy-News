package com.visionDev.trendynews.api.news

import com.visionDev.trendynews.BuildConfig.NEWS_API_KEY_MAIN
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataApiService {
    @GET("news")
    suspend fun getArticles(@Query("q") query:String, @Query("category") category:String,@Query("language")language:String="en", @Query("apikey") apiKey:String= NEWS_API_KEY_MAIN): ApiResponse
}