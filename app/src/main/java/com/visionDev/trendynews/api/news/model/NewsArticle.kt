package com.visionDev.trendynews.api.news.model

import com.visionDev.trendynews.common.ArticleUIState

data class NewsArticle(
    override val id:Long?,
    val author: String,
    val category: String,
    val country: String,
    val description: String,
    val image: String,
    val language: String,
    val published_at: String,
    val source: String,
    override val title: String,
    val url: String
):ArticleUIState{
    override val link: String
        get() = url
    override val videoUrl: String?
        get() = null
    override val imageUrl: String
        get() = image
    override val pubDate: String
        get() = published_at
    override val content: String
        get() = description
    override val authors: String
        get() = author
}