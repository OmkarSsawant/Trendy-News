package com.visionDev.trendynews.db

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.visionDev.trendynews.R
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.utils.NEWS_CATEGORIES
import com.visionDev.trendynews.utils.NEWS_COUNTRIES
import com.visionDev.trendynews.utils.NEWS_LANGUAGES
import com.visionDev.trendynews.utils.NEWS_PUBS
/**
* This SharedPrefsManager `newsCustomizedStore` will store the customization of news the
 * user wants to read daily
* */
class SharedPrefsManager(context: Context) {

    private val newsCustomizedStore: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

     fun setPreferredNewsCategories(categories: String) = newsCustomizedStore.edit(true) {
        putString(NEWS_CATEGORIES,categories)
    }

     fun setPreferredNewsLanguages(languages: String) = newsCustomizedStore.edit(true){
        putString(NEWS_LANGUAGES,languages)

    }


     fun setPreferredCountries(countries: String) =  newsCustomizedStore.edit(true){
        putString(NEWS_COUNTRIES,countries)

    }

     fun setPreferredPublishers(publishers: String) =  newsCustomizedStore.edit(true){
        putString(NEWS_PUBS,publishers)

    }


    private fun getPreferredNewsCategories(): String = newsCustomizedStore.getString(
        NEWS_CATEGORIES,
        "technology,entertainment,politics"
    )!!

    private fun getPreferredNewsLanguages(): String = newsCustomizedStore.getString(
        NEWS_LANGUAGES,
        "en"
    )!!

    private fun getPreferredCountries(): String? = newsCustomizedStore.getString(
        NEWS_COUNTRIES,
        null
    )

    private fun getPreferredPublishers(): String? = newsCustomizedStore.getString(
        NEWS_PUBS,
        null
    )

    fun buildFromPreferredNewsRequestInfo(builder: NewsRequestInfo.() -> Unit): NewsRequestInfo {
        val reqInfo = NewsRequestInfo(
            null,
            null,
            getPreferredNewsLanguages(),
            getPreferredNewsCategories(),
            getPreferredCountries(),
            newsPublishers = getPreferredPublishers()
        )
        builder(reqInfo)
        return reqInfo
    }
}