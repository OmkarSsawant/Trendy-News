package com.visionDev.trendynews.manager

import com.visionDev.trendynews.api.news.NewsDataApiService
import com.visionDev.trendynews.db.saved_articles.SavedArticlesDAO
import com.visionDev.trendynews.db.today_articles.ArticlesTodayDAO

class NewsApiMediator
    constructor(
        private val apiService: NewsDataApiService,
        private val savedArticlesDAO: SavedArticlesDAO,
        private val articlesTodayDAO: ArticlesTodayDAO
    ){
    val lastPageIndexCache:Map<String,Int> = mapOf()

        init {
            //request 2 pages
        }

/***
* Loads More Pages ,Insert Into Cache(articles_today)
 * and update lastPageIndexCache
* */
    fun loadMore(){
        TODO("Load And Insert Into Cache(articles_today)")
    }
}