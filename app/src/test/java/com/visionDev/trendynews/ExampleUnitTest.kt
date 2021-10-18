package com.visionDev.trendynews

import android.util.JsonReader
import com.google.gson.Gson
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.trendynews.TrendyNewsService
import com.visionDev.trendynews.utils.MEDIA_STACK_API_ENDPOINT
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader
import java.io.Reader
import java.net.URL
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `Date Experiments`(){
        val require2Digit : (Int) -> String  = { if(it < 10) "0$it" else "$it" }
        print(Calendar.getInstance().let {
            "${require2Digit(it[Calendar.DAY_OF_MONTH])}-${require2Digit(it[Calendar.MONTH])}-${it[Calendar.YEAR]}"
        })
    }

    @Test
    fun trendyNewsApiTest(){
        val rf2 = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val tNapi = rf2.create(TrendyNewsService::class.java)
//        val res = tNapi.getImages(listOf("https://timesofindia.indiatimes.com/india/chhattisgarh-1-dead-as-vehicle-ploughs-into-religious-procession/articleshow/87041878.cms"))
//        println(res)
    }


    @Test fun rawApiTest() {

        val rf2 = Retrofit.Builder()
            .baseUrl(MEDIA_STACK_API_ENDPOINT)
            .client(OkHttpClient().newBuilder()
                .addInterceptor {
                println(it.request().url())
                    it.proceed(it.request())
                }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val tNapi = rf2.create(MediaStackApi::class.java)
        runBlocking {
          val res  =  tNapi.fetchArticles(
                null,
                "en",
                "technology",
                null,
                keywords = null,
                0,
                MediaStackApi.Sort.PUB_DESC.str_type,
            )

            println(res.data)
        }
    }
}