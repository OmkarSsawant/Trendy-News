package com.visionDev.trendynews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.visionDev.trendynews.api.model.NewsArticle
import com.visionDev.trendynews.db.saved_articles.SavedArticlesDAO
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO

@Database(
    version = 1,
    entities = [NewsArticle::class]
)
abstract class TrendyNewsDatabase : RoomDatabase() {
   abstract fun articlesTodayDAO():ArticlesTodayDAO
    abstract fun articlesSavedDAO():SavedArticlesDAO


    companion object{
        @Volatile
        var INSTANCE: TrendyNewsDatabase?=null

        @JvmStatic
        fun getInstance(context: Context): TrendyNewsDatabase = synchronized(this){
            if(INSTANCE !=null){
                INSTANCE!!
            }else{
                Room.databaseBuilder(context, TrendyNewsDatabase::class.java,"trendy_news.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

    }
}
