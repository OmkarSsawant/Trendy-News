package com.visionDev.trendynews.api.news.model

data class NewsArticle(
    val author: String,
    val category: String,
    val country: String,
    val description: String,
    val image: String,
    val language: String,
    val published_at: String,
    val source: String,
    val title: String,
    val url: String
)