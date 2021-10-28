package com.visionDev.trendynews.utils

import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.util.Size

val Context.displaySize: Size
    get() {
        val size = Point()
        val dm = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        dm.displays?.firstOrNull()?.getRealSize(size)
        return Size(size.x, size.y)
    }