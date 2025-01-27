package com.visionDev.trendynews.common

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ScaleDrawable
import android.net.Uri
import android.text.format.DateUtils
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.chip.Chip
import com.google.android.material.drawable.DrawableUtils
import com.visionDev.trendynews.R
import java.util.*


@BindingAdapter("img_url")
fun ImageView.setUrlImage(imgUrl: String?) {
    if (imgUrl != null) {
        Glide.with(this)
            .load(imgUrl)
            .placeholder(null)
            .apply(RequestOptions.overrideOf(width, height))
            .into(this)
        



    }


}

@BindingAdapter("should_show")
fun Button.shouldShow(article: ArticleUIState) {

    val require2Digit: (Int) -> String = { if (it < 10) "0$it" else "$it" }
    isVisible = article.pubDate == Calendar.getInstance()
        .let { "${require2Digit(it[Calendar.DAY_OF_MONTH])}-${require2Digit(it[Calendar.MONTH])}-${it[Calendar.YEAR]}" }
}

@BindingAdapter("web_url")
fun Button.openUrl(url:String?){
    if(url!=null)
    setOnClickListener {
        val browser:Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.context.startActivity(Intent.createChooser(browser,"Show Article with"))
    }
    else
       visibility = View.GONE
}

/*
*  .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                       resource?.mutate()?.let {d->
                            background = ScaleDrawable(
                                d,
                                Gravity.CENTER,
                                0.4f,
                                0.2f
                            )
                                .drawable
                        }
                    return false
                }
            })
* */