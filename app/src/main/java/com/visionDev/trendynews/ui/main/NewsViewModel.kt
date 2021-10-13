package com.visionDev.trendynews.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.visionDev.trendynews.manager.NewsRepository


/***
 * i  : Observe todays_articles table and feed in recyclerView
 * ii : manage user request when user reach end of page
 * */
class NewsViewModel
    constructor(application: Application,newsRepository: NewsRepository) : AndroidViewModel(application) {



    }