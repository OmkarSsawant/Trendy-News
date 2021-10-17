package com.visionDev.trendynews.api.news

import com.visionDev.trendynews.BuildConfig.MEDIA_STACK_API_KEY_MAIN
import com.visionDev.trendynews.api.news.model.MediaStackApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaStackApi {
        @GET("news")
        suspend fun fetchArticles(
            @Query("date") date:String,
            @Query("keywords") keywords:String,
            @Query("languages") languages:String,
            @Query("categories") categories:String,
            @Query("countries") countries:String,
            /*
            The Pagination offset value is `returned in response`
            * */
            @Query("offset") offset:Int,
            @Query("sort")sort: String = Sort.PUB_DESC.str_type,
            @Query("sources") newsPublishers:String?=null,
            @Query("limit") limit:Int=25,
            @Query("access_key") apiKey: String = MEDIA_STACK_API_KEY_MAIN
        ):MediaStackApiResponse

        enum class Sort(val str_type:String){
            PUB_DESC("published_desc"),PUB_ASC("published_asc"),POPULARITY("popularity")
        }
}