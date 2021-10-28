package com.visionDev.trendynews.common

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.hardware.display.DisplayManager
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.visionDev.trendynews.R
import java.util.*


@BindingAdapter("img_url")
fun ImageView.setUrlImage(imgUrl: String?) {

    val size = Point()
    val dm = context.getSystemService(   Context.DISPLAY_SERVICE) as DisplayManager
    dm.displays?.firstOrNull()?.getRealSize(size)


    if (imgUrl != null) {
        Glide.with(this)
            .load(imgUrl)
            .placeholder(null)
            .apply(RequestOptions.overrideOf(size.x,size.y))
            .listener(object : RequestListener<Drawable> {
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

                (parent.parent as View).let {

                    val title:TextView = it.findViewById(R.id.article_title)
                    val content:TextView = it.findViewById(R.id.txt_content)

                    resource?.let { loadedImg:Drawable->
                        Palette.Builder(loadedImg.toBitmap())
                            .generate { palette ->
                                tag = palette
                                Log.i("ViewBindAdapter", "onResourceReady: $palette")
                                palette?.run {
                                    Log.i("ViewBindAdapter", "onResourceReady: ${palette.lightMutedSwatch?.let { "${it.rgb} > ${it.titleTextColor} ${it.bodyTextColor}" } }")
                                    darkVibrantSwatch?.let {dvs->
                                        title.setTextColor(dvs.titleTextColor)
                                    }
                                    lightMutedSwatch?.bodyTextColor?.let { it3-> content.setTextColor(it3)  }
                                    lightMutedSwatch?.rgb?.let { it1 -> (content.parent as View).setBackgroundColor(it1) }
                                }
                            }
                    }

                }

                return false
            }
        })
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

@BindingAdapter("bottom_right_corner")
fun MaterialCardView.setBottomRightCorner(value:Float){
    this.shapeAppearanceModel = shapeAppearanceModel.toBuilder()
        .setBottomRightCorner(CornerFamily.ROUNDED,value)
        .build()
}

