package com.visionDev.trendynews.db.today_articles

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.visionDev.trendynews.common.ArticleUIState


@Entity(tableName = "articles_today")
data class TodayNewsArticle(
    @PrimaryKey(autoGenerate = true)
    override val id:Long?=null,
    override val title:String,
    override val link:String,
    val category: String?=null,
    override val imageUrl:String?=null,
    override val videoUrl:String?=null,
    override val pubDate:String,
    override val content:String?=null,
    override val authors:String?=null,
):ArticleUIState{
    fun isLatest():Boolean = TODO("Compare with todays Date")

    companion object{
        fun from(category: String?,articleUIState: ArticleUIState):TodayNewsArticle =
            with(articleUIState){
                TodayNewsArticle(null,title, link, category, imageUrl, videoUrl, pubDate, content, authors)
            }
    }
}
