package com.anilderinbay.appcent_news.view.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anilderinbay.appcent_news.R
import com.anilderinbay.appcent_news.adapter.NewsAdapter
import com.anilderinbay.appcent_news.databinding.FragmentNewsBinding
import com.anilderinbay.appcent_news.model.response.NewsResponse
import com.anilderinbay.appcent_news.util.Constants
import com.anilderinbay.appcent_news.viewmodel.NewsViewModel
import com.orhanobut.hawk.Hawk


class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private var newsAdapter = NewsAdapter()
    var savedList: ArrayList<NewsResponse.Article> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        viewModel.getBreakingNews("us")

        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner, Observer {
            it.articles?.let { it1 -> newsAdapter.setItems(it1) }
        })

        viewModel.savedNewsLiveData.observe(viewLifecycleOwner, Observer {
            it.articles?.let { it1 -> newsAdapter.setItems(it1)
            }
        })


        binding.apply {
            rvNewsList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvNewsList.adapter = newsAdapter

            searchInput.doAfterTextChanged {
                it?.let {
                    if (it.length >= 2) {
                        newsAdapter.clearItems()
                        viewModel.searchNews(it.toString())

                    } else {
                        newsAdapter.clearItems()
                        viewModel.getBreakingNews("us")

                    }
                }

            }
            newsAdapter.newsItemClickListener = {
                findNavController().navigate(R.id.action_newsFragment_to_detailsFragment,
                    bundleOf("detail" to it))


            }
        }



        viewModel.newsSearchLiveData.observe(viewLifecycleOwner) {
            it.articles?.let { it1 -> newsAdapter.setItems(it1) }
        }

    }


}