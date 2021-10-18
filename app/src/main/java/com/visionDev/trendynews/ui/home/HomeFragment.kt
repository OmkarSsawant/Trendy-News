package com.visionDev.trendynews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.api.news.model.NewsRequestInfo
import com.visionDev.trendynews.databinding.FragmentHomeBinding
import com.visionDev.trendynews.ui.home.adapters.NewsListAdapter
import com.visionDev.trendynews.ui.home.main.NewsRepository
import com.visionDev.trendynews.ui.home.main.NewsViewModel
import com.visionDev.trendynews.utils.MEDIA_STACK_API_ENDPOINT
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    lateinit var vb: FragmentHomeBinding
    private val newsViewModel: NewsViewModel by lazy {
        val newsDataApiService = Retrofit.Builder()
            .baseUrl(MEDIA_STACK_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MediaStackApi::class.java)
        NewsViewModel(requireActivity().application, NewsRepository(newsDataApiService))
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsListAdapter = NewsListAdapter()
        vb.newsList.layoutManager = LinearLayoutManager(context)
        vb.newsList.adapter = newsListAdapter

        lifecycleScope.launch {

            newsViewModel.todayNewsArticles
                .collectLatest {
                    Log.i(TAG, "onViewCreated: $it")
                    newsListAdapter.submitData(it)
                }
        }

    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}