package com.visionDev.trendynews.utils

import java.util.*

fun getTodayDate():String {
    val require2Digit : (Int) -> String  = { if(it < 10) "0$it" else "$it" }
    return Calendar.getInstance().let {
        "${it[Calendar.YEAR]}-${require2Digit(it[Calendar.MONTH])}-${require2Digit(it[Calendar.DAY_OF_MONTH])}"
    }
}