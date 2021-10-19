package com.visionDev.trendynews.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.visionDev.trendynews.R
import com.visionDev.trendynews.api.news.MediaStackApi
import com.visionDev.trendynews.databinding.FragmentArticleDetailBinding
import com.visionDev.trendynews.db.TrendyNewsDatabase
import com.visionDev.trendynews.ui.home.main.NewsRepository
import com.visionDev.trendynews.ui.home.main.NewsViewModel
import com.visionDev.trendynews.utils.MEDIA_STACK_API_ENDPOINT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleDetailFragment : Fragment() {

    private lateinit var vb: FragmentArticleDetailBinding
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
        vb = FragmentArticleDetailBinding.inflate(inflater, container, false)

        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("TAG", "onViewCreated: ${args.articleId}")
        vb.articleDetailToolabar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        vb.articleDetailToolabar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        newsViewModel.getArticleById(args.articleId) {

        vb.collapsingToolbarLayout.apply {
            title = it.title
            expandedTitleGravity = Gravity.START or Gravity.BOTTOM
            setContentScrimColor(Color.BLACK)
            setExpandedTitleColor(Color.TRANSPARENT)

        }

            requireActivity().runOnUiThread {
                vb.article = it
                vb.invalidateAll()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        requireActivity().window.decorView.systemUiVisibility = 0
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    override fun onDestroyView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        super.onDestroyView()
    }
    companion object{
        private const val TAG = "ArticleDetailFragment"
    }
}