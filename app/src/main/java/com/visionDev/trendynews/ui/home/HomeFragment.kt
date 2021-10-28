package com.visionDev.trendynews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.flatMap
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.TechFashApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.databinding.FragmentHomeBinding
import com.visionDev.trendynews.db.TrendyNewsDatabase
import com.visionDev.trendynews.ui.home.adapters.NewsListAdapter
import com.visionDev.trendynews.ui.home.adapters.NewsLoadStateAdapter
import com.visionDev.trendynews.ui.home.main.NewsRepository
import com.visionDev.trendynews.ui.home.main.NewsViewModel
import com.visionDev.trendynews.utils.MEDIA_STACK_API_ENDPOINT
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.max
import kotlin.math.min

class HomeFragment : Fragment() {

    lateinit var vb: FragmentHomeBinding
    //TODO: VIew Model Injection
    private val newsViewModel: NewsViewModel by lazy {
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
        val techFashApiService = Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TechFashApi::class.java)
        NewsViewModel(requireActivity().application, NewsRepository(newsDataApiService,articlesTodayDAO,techFashApiService))
        /* ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(NewsViewModel::class.java)*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentHomeBinding.inflate(inflater, container, false)
        return vb.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsListAdapter = NewsListAdapter()
        vb.newsList.adapter = newsListAdapter

        vb.newsList.isUserInputEnabled = false
        vb.newsList.setOnDragListener { v, event ->
            if(event.action == DragEvent.ACTI   ON_DRAG_ENDED )
            {
                if(event.x >= requireActivity().window.decorView.width * .6)
                    vb.newsList.setCurrentItem(min(vb.newsList.currentItem+1,newsListAdapter.itemCount),true)
                else if(event.x <= requireActivity().window.decorView.width * .4){
                    vb.newsList.setCurrentItem(max(vb.newsList.currentItem-1,1),true)
                }
            }
            false
        }
        newsListAdapter.withLoadStateFooter(NewsLoadStateAdapter(newsListAdapter))

        lifecycleScope.launch {
//
//            newsViewModel.todayNewsArticles
//                .collectLatest { pagingData ->
//                    Log.i(TAG, "onViewCreated: $pagingData")
//                    newsListAdapter.submitData(pagingData.map { it })
//                }

            newsViewModel.newsArticles.collectLatest { pagingData ->
                Log.i(TAG, "onViewCreated: $pagingData")
                newsListAdapter.submitData(pagingData.map { it })
            }

        }




    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}