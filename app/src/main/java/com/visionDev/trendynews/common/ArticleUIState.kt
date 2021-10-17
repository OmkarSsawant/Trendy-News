package com.visionDev.trendynews.common

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field


interface ArticleUIState {
    val title:String
    val link:String
    val videoUrl:String?
    val imageUrl:String?
    val pubDate:String
    val content:String?
    val authors:String?
}