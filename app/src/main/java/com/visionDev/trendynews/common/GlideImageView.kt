package com.visionDev.trendynews.common

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class GlideImageView
@JvmOverloads
constructor(
    context: Context,
    attributeSet: AttributeSet?=null,
    defStyle:Int=0
)
    : AppCompatImageView(
    context,
    attributeSet,
    defStyle
    ) {

    override fun onDraw(canvas: Canvas?) {
        try {
            super.onDraw(canvas)
        }catch (e:Exception){
            Log.i("Glide Temp Error", "onDraw: tempFix For Bitmap Recycle Glide Error")
        }
    }
}