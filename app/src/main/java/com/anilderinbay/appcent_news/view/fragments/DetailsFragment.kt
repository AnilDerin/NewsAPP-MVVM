package com.anilderinbay.appcent_news.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.anilderinbay.appcent_news.R
import com.anilderinbay.appcent_news.databinding.FragmentDetailsBinding
import com.anilderinbay.appcent_news.model.response.NewsResponse
import com.anilderinbay.appcent_news.util.Constants
import com.bumptech.glide.Glide
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.news_article.*
import kotlinx.android.synthetic.main.news_article.view.*
import java.util.*
import kotlin.collections.ArrayList

class DetailsFragment : Fragment(R.layout.fragment_details) {


    private lateinit var binding: FragmentDetailsBinding
    var savedList: ArrayList<NewsResponse.Article> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            (arguments?.get("detail") as NewsResponse.Article).let { detail ->



                if (Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST) != null) {


                    (Hawk.get(Constants.FAVORITE_LIST) as ArrayList<NewsResponse.Article>).forEach {

                        if (it.url == detail.url) {
                            ivFavorite.setImageResource(R.drawable.ic_liked)
                            ivFavorite.setOnClickListener {
                                (arguments?.get("detail") as NewsResponse.Article).let { detail ->
                                    deleteFromFavorites(detail)
                                    ivFavorite.setImageResource(R.drawable.ic_like)

                                }
                            }
                        } else {
                            ivFavorite.setImageResource(R.drawable.ic_like)
                            ivFavorite.setOnClickListener {
                                (arguments?.get("detail") as NewsResponse.Article).let { detail ->
                                    ivFavorite.setImageResource(R.drawable.ic_liked)
                                    addToFavorites(detail)

                                }
                            }
                        }



                    }
                }

            }


            binding.apply {
                ivShare.setOnClickListener {

                    (arguments?.get("detail") as NewsResponse.Article).let {

                        val url = it.url.toString()
                        val shareIntent = Intent()
                        shareIntent.action = Intent.ACTION_SEND
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
                        startActivity(Intent.createChooser(shareIntent, "Share via"))
                    }
                }

            }


            (arguments?.get("detail") as NewsResponse.Article).let {

                binding.tvDetailsTitle.text = it.title
                binding.tvDetailsDescription.text = it.description

                //binding.content'i API'dan çektiğimizde +2338 gibi karakterler gösteriyor
                // haberin detayı gelmiyor!

                binding.detailsSource.text = it.source?.name
                binding.detailsDate.text = it.publishedAt
                binding.backButton.setOnClickListener {
                    findNavController().navigate(R.id.action_detailsFragment_to_newsFragment)
                }
                Glide.with(this@DetailsFragment).load(it.urlToImage).into(binding.ivNewsImage)
            }


        }

        binding.apply {
            (arguments?.get("detail") as NewsResponse.Article).let { detail ->

                ivFavorite.setOnClickListener {
                    addToFavorites(detail)
                    ivFavorite.setImageResource(R.drawable.ic_liked)

                }
            }
        }





        binding.webViewButton.setOnClickListener {
            val navController: NavController =
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            val bundle = Bundle()
            (arguments?.get("detail") as NewsResponse.Article).let {
                bundle.putSerializable("url", it.url)
            }

            navController.navigate(R.id.webViewFragment, bundle)
        }

    }

    private fun addToFavorites(response: NewsResponse.Article) {
        savedList.add(response)
        if (Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST) != null) {
            if (Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST)
                    .contains(response).not()
            ) {
                Hawk.put(Constants.FAVORITE_LIST, savedList)
                savedList = Hawk.get(Constants.FAVORITE_LIST)

            }
        } else {
            Hawk.put(Constants.FAVORITE_LIST, savedList)
        }
    }

    private fun deleteFromFavorites(response: NewsResponse.Article) {
        savedList.add(response)
        if (Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST) != null) {
            Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST)
                .forEachIndexed { index, article ->
                    if (article == response) {
                        savedList.removeAt(index)
                    }
                }
            savedList = Hawk.get(Constants.FAVORITE_LIST)
            Hawk.put(Constants.FAVORITE_LIST, savedList)
        }
    }


    override fun onResume() {
        super.onResume()


        if (Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST) != null) {

            if (Hawk.get<ArrayList<NewsResponse.Article>>(Constants.FAVORITE_LIST).isNotEmpty()) {

                savedList = Hawk.get(Constants.FAVORITE_LIST)

            }
        }


    }
}