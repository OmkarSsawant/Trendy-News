package com.visionDev.trendynews.db.saved_articles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle

@Dao
interface SavedArticlesDAO {
    @Query("SELECT * FROM saved_news_articles")
    fun getArticles(): LiveData<List<SavedNewsArticle>>
}