package com.visionDev.trendynews.ui.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.visionDev.trendynews.R
import com.visionDev.trendynews.common.ArticleUIState
import com.visionDev.trendynews.databinding.TileNewsBinding
import com.visionDev.trendynews.ui.home.ArticleDetailFragmentArgs
import com.visionDev.trendynews.ui.home.HomeFragmentDirections
import org.w3c.dom.Text

class NewsListAdapter :
    PagingDataAdapter<ArticleUIState, NewsListAdapter.ArticleViewHolder>(ARTICLE_DIFF_TOOL),
    MotionLayout.TransitionListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            TileNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let {
            holder.vb.article = it
            holder.vb.invalidateAll()
            holder.vb.constraintLayout.addTransitionListener(this)
            holder.vb.constraintLayout.tag = holder.vb.constraintLayout.tag ?:
                    ScrollView(holder.itemView.context)
                        .also {sv->
                            sv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                        }

        }
    }





    inner class ArticleViewHolder(val vb: TileNewsBinding) : RecyclerView.ViewHolder(vb.root)
    companion object {
        val ARTICLE_DIFF_TOOL = object : DiffUtil.ItemCallback<ArticleUIState>() {
            override fun areItemsTheSame(
                oldItem: ArticleUIState,
                newItem: ArticleUIState
            ): Boolean = oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: ArticleUIState,
                newItem: ArticleUIState
            ): Boolean =
                (oldItem.link == newItem.link &&
                        oldItem.title == newItem.title &&
                        oldItem.pubDate == newItem.pubDate &&
                        oldItem.authors == newItem.authors
                        )
        }
    }

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

        val scrollView:ScrollView? = motionLayout?.tag as ScrollView?
        when(currentId){
            R.id.end->{
                motionLayout?.let {ml->
                    //Add A ScrollView to text to be fully visible
                    val articleContent: CardView = ml.findViewById(R.id.article_content)
                    val textContent: TextView = articleContent.findViewById(R.id.txt_content)
                    val img:ImageView = ml.findViewById(R.id.image)
                    val title:TextView = ml.findViewById(R.id.article_title)
                    val palette  = img.tag as? Palette
                    title.setTextColor(palette?.getDarkVibrantColor(Color.BLACK) ?: Color.BLACK)
                    title.setBackgroundColor(palette?.getLightVibrantColor(Color.WHITE) ?: Color.WHITE)
                    img.scaleType = ImageView.ScaleType.FIT_XY
                    articleContent.removeView(textContent)

                    scrollView?.removeAllViews()
                    scrollView?.addView(textContent)

                    articleContent.removeAllViews()
                    articleContent.addView(scrollView)
                }
            }
            R.id.start->{
                motionLayout?.let {ml->
                    //ScrollView should be removed to gain the
//                    Motion behaviour
                    val articleContent:CardView = ml.findViewById(R.id.article_content)
                    val textContent:TextView = articleContent.findViewById(R.id.txt_content)
                    val img:ImageView = ml.findViewById(R.id.image)
                    img.scaleType = ImageView.ScaleType.FIT_START
                    val title:TextView = ml.findViewById(R.id.article_title)
                    val palette  = img.tag as? Palette
                    title.setBackgroundColor(Color.TRANSPARENT)
                    title.setTextColor(palette?.darkVibrantSwatch?.titleTextColor ?: Color.WHITE)
                    scrollView?.removeView(textContent)
                    articleContent.removeView(scrollView)
                    articleContent.removeAllViews()
                    articleContent.addView(textContent)
                }
            }
        }
    }

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {}
}