package com.visionDev.trendynews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.visionDev.trendynews.db.saved_articles.SavedArticlesDAO
import com.visionDev.trendynews.db.saved_articles.SavedNewsArticle
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO
import com.visionDev.trendynews.db.today_articles.TodayNewsArticle

@Database(
    version = 1,
    entities = [SavedNewsArticle::class,TodayNewsArticle::class]
)
@TypeConverters(TrendyNewsTypeConvertors::class)
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
