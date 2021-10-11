package com.visionDev.trendynews.db.saved_articles

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.visionDev.trendynews.common.ArticleUIState
import retrofit2.http.Field

@Entity(tableName = "saved_news_articles")
data class SavedNewsArticle(
    override val title:String,
    override val link:String,
    override val imageUrl:String?=null,
    override val videoUrl:String?=null,
    override val pubDate:String,
    override val content:String?=null,
    override val author:String?=null,
):ArticleUIState{

    fun isLatest():Boolean = TODO("Compare with todays Date")
}