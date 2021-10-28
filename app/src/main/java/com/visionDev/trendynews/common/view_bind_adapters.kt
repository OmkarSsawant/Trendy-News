package com.visionDev.trendynews.common

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ScaleDrawable
import android.media.Image
import android.net.Uri
import android.text.format.DateUtils
import android.transition.Transition
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.drawable.DrawableUtils
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.visionDev.trendynews.R
import java.util.*


@BindingAdapter("img_url")
fun ImageView.setUrlImage(imgUrl: String?) {
    if (imgUrl != null) {
        Glide.with(this)
            .load(imgUrl)
            .placeholder(null)
            .apply(RequestOptions.overrideOf(width, height))
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
                (parent as View).let {

                    val title:TextView = it.findViewById(R.id.title)
                    val content:TextView = it.findViewById(R.id.txt_content)

                    resource?.let { loadedImg:Drawable->
                        Palette.Builder(loadedImg.toBitmap())
                            .generate { palette ->
                                Log.i("ViewBindAdapter", "onResourceReady: $palette")
                                palette?.run {
                                    Log.i("ViewBindAdapter", "onResourceReady: ${palette.lightMutedSwatch?.let { "${it.rgb} > ${it.titleTextColor} ${it.bodyTextColor}" } }")
                                    lightMutedSwatch?.titleTextColor?.let { it2->title.setTextColor(it2) }
                                    lightMutedSwatch?.bodyTextColor?.let { it3-> content.setTextColor(it3)  }
                                    lightMutedSwatch?.rgb?.let { it1 -> content.setBackgroundColor(it1) }
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