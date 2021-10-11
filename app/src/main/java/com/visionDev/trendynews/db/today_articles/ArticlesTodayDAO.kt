package com.visionDev.trendynews.db.today_articles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface ArticlesTodayDAO {

    @Query("SELECT * FROM articles_today WHERE category=:type")
    fun getArticlesOf(type:String):LiveData<List<TodayNewsArticle>>

}