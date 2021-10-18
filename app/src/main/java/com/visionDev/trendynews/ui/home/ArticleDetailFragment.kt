package com.visionDev.trendynews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.visionDev.trendynews.databinding.FragmentDetailArticleBinding

class ArticleDetailFragment : Fragment() {

    private lateinit var vb:FragmentDetailArticleBinding
    private val args:ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentDetailArticleBinding.inflate(inflater,container,false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = TODO("Get Article with :${args.articleId}")
        vb.article = article
    }
}