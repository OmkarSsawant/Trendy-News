package com.visionDev.trendynews.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class NewsArticle(
    val title:String,
    val link:String,
    @Field("image_url")
    val imageUrl:String?=null,
    @Field("video_url")
    val videoUrl:String?=null,
    val pubDate:String,
    val content:String?=null,
    @field:SerializedName("creator")
    val author:String?=null,
){

    fun isLatest():Boolean = TODO("Compare with todays Date")
}
