package com.visionDev.trendynews.db.today_articles

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesTodayDAO {

    @Query("SELECT * FROM articles_today WHERE :and_or_Query")
     fun getArticlesOf(and_or_Query:String):PagingSource<Long,TodayNewsArticle>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(articles:List<TodayNewsArticle>)
}