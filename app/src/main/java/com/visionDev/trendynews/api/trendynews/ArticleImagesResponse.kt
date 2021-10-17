package com.visionDev.trendynews.api.trendynews

import retrofit2.http.Field

class ArticleImagesResponse(
    @Field("article_images")
    val articleAndImages:List<ArticleImage>
)