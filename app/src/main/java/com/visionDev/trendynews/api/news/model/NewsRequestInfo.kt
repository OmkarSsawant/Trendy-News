package com.visionDev.trendynews.api.news.model

import com.visionDev.trendynews.api.news.MediaStackApi

class NewsRequestInfo(
     var date: String?,
     val keywords: String?,
     val languages: String,
     val categories: String?,
     val countries: String?,
     val sort: MediaStackApi.Sort = MediaStackApi.Sort.PUB_DESC,
     val newsPublishers: String? = null,
)