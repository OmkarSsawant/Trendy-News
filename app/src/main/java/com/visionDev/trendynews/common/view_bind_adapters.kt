package com.visionDev.trendynews.common

import android.text.format.DateUtils
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import java.util.*


@BindingAdapter("img_url")
fun ImageView.setUrlImage(imgUrl: String?)
{
    if(imgUrl!=null)
    Glide.with(context)
        .load(imgUrl)
        .into(this)

}

@BindingAdapter("should_show")
fun Button.shouldShow(article: ArticleUIState) {

    val require2Digit: (Int) -> String = { if (it < 10) "0$it" else "$it" }
    isVisible = article.pubDate == Calendar.getInstance()
        .let { "${require2Digit(it[Calendar.DAY_OF_MONTH])}-${require2Digit(it[Calendar.MONTH])}-${it[Calendar.YEAR]}" }
}