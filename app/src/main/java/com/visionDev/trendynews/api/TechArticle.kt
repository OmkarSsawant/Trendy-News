package com.visionDev.trendynews.api

import retrofit2.http.Field
import retrofit2.http.Query

data class TechArticle(
    val title:String,
    val link:String,
    @Field("image_url")
    val imageUrl:String?=null,
    @Field("video_url")
    val videoUrl:String?=null,
    val pubDate:String,
    val content:String?=null
)
