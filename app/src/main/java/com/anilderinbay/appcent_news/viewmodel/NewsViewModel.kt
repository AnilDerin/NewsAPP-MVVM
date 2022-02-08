package com.anilderinbay.appcent_news.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anilderinbay.appcent_news.model.response.NewsResponse
import com.anilderinbay.appcent_news.service.NewsAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NewsViewModel(application: Application) : BaseViewModel(application) {

    private val newsAPIService = NewsAPIService()
    private val disposable = CompositeDisposable()

    private val _newsSearchLiveData = MutableLiveData<NewsResponse>()
    val newsSearchLiveData : LiveData<NewsResponse> = _newsSearchLiveData

    private val _breakingNewsLiveData = MutableLiveData<NewsResponse>()
    val breakingNewsLiveData : LiveData<NewsResponse> = _breakingNewsLiveData

    private val _savedNewsLiveData = MutableLiveData<NewsResponse>()
    val savedNewsLiveData : LiveData<NewsResponse> = _savedNewsLiveData


    fun searchNews(searchTerm: String) {
        disposable.add(
            newsAPIService.searchNews(searchTerm)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(response: NewsResponse) {
                        _newsSearchLiveData.value = response
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getBreakingNews(country : String) {
        disposable.add(
            newsAPIService.getBreakingNews(country)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(response: NewsResponse) {
                        _breakingNewsLiveData.value = response
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )

    }


}