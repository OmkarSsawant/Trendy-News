package com.visionDev.trendynews.db

import android.util.TypedValue
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import java.lang.reflect.Type

object TrendyNewsTypeConvertors {

    @TypeConverter
    fun stringListToString(strings:List<String>):String = Gson().toJson(strings)

    @TypeConverter
    fun stringToStringList(strings:String):List<String> = Gson().fromJson(strings,Array<String>::class.java).toList()

}