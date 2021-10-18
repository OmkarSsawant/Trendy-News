package com.visionDev.trendynews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.databinding.FragmentDetailArticleBinding
import com.visionDev.trendynews.db.TrendyNewsDatabase
import com.visionDev.trendynews.ui.home.main.NewsRepository
import com.visionDev.trendynews.ui.home.main.NewsViewModel
import com.visionDev.trendynews.utils.MEDIA_STACK_API_ENDPOINT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleDetailFragment : Fragment() {

    private lateinit var vb: FragmentDetailArticleBinding
    private val args: ArticleDetailFragmentArgs by navArgs()
//TODO: VIew Model Injection
    private val newsViewModel: NewsViewModel by lazy{

        val newsDataApiService = Retrofit.Builder()
            .baseUrl(MEDIA_STACK_API_ENDPOINT)
            .client(OkHttpClient().newBuilder().addInterceptor {
                Log.i(TAG, "Made Request At : ${it.request().url()}")
                it.proceed(it.request())
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MediaStackApi::class.java)
        val articlesTodayDAO = TrendyNewsDatabase.getInstance(requireContext())
            .articlesTodayDAO()
        NewsViewModel(requireActivity().application, NewsRepository(newsDataApiService,articlesTodayDAO))

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentDetailArticleBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", "onViewCreated: ${args.articleId}")
        newsViewModel.getArticleById(args.articleId) {
            requireActivity().runOnUiThread {
                vb.article = it
                vb.invalidateAll()
            }
        }

    }

    companion object{
        private const val TAG = "ArticleDetailFragment"
    }
}