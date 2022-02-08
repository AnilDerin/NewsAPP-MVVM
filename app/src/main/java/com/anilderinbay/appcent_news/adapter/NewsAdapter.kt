package com.anilderinbay.appcent_news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anilderinbay.appcent_news.R
import com.anilderinbay.appcent_news.model.response.NewsResponse
import com.anilderinbay.appcent_news.util.Constants
import com.bumptech.glide.Glide
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.news_article.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    val items = arrayListOf<NewsResponse.Article>()
    var newsItemClickListener: (NewsResponse.Article) -> Unit = {}
    var favoritesItemClickListener: (NewsResponse.Article) -> Unit = {}
    var savedList: ArrayList<NewsResponse.Article> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_article, parent, false)
        )

    override fun getItemCount() = items.count()
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class NewsViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NewsResponse.Article) {


            item.let { response ->
                itemView.newsTitle.text = item.title
                itemView.newsDescription.text = item.description
                itemView.newsSource.text = item.source?.name.toString()
                itemView.newsPublishDate.text = item.publishedAt


                Glide.with(itemView).load(item.urlToImage).into(itemView.newsArticleImage)

                itemView.setOnClickListener {
                    newsItemClickListener(item)

                }



            }

        }


    }



    fun setItems(response: ArrayList<NewsResponse.Article>) {
        items.addAll(response)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }
}