package com.anilderinbay.appcent_news.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anilderinbay.appcent_news.R
import com.anilderinbay.appcent_news.databinding.FragmentDetailsBinding
import com.anilderinbay.appcent_news.databinding.FragmentWebViewBinding
import com.anilderinbay.appcent_news.model.response.NewsResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment(R.layout.fragment_web_view) {
    private lateinit var binding: FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("url")?.let {
            binding.webView.loadUrl(it)
            binding.backButtonSource.setOnClickListener {
                findNavController().popBackStack()
            }
        }


    }

}