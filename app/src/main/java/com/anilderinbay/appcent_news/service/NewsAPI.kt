package com.anilderinbay.appcent_news.service

import com.anilderinbay.appcent_news.model.response.NewsResponse
import com.anilderinbay.appcent_news.util.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsAPI {

    companion object {
        const val SEARCH_EVERYTHING = "v2/everything"
        const val TOP_HEADLINES = "v2/top-headlines"
    }


    @GET(SEARCH_EVERYTHING)
    fun searchNews(
        @Query("q") searchTerm: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Single<NewsResponse>


    @GET(TOP_HEADLINES)
    fun breakingNews(
        @Query("country") country: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Single<NewsResponse>

    @GET(TOP_HEADLINES)
    fun saveNews(
        @Query("url") url: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Single<NewsResponse>
}
