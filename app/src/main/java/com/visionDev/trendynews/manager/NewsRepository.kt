package com.visionDev.trendynews.manager

import com.visionDev.trendynews.api.ApiResponse
import com.visionDev.trendynews.api.NewsDataApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {
    private val apiService: NewsDataApiService
    init{
        val rf2 = Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
         apiService = rf2.create(NewsDataApiService::class.java)
    }

  suspend  fun getNewsOf(type:String,category: String): ApiResponse {
        return apiService.getArticles(type,category)
    }
}