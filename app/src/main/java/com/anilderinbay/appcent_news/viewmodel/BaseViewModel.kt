package com.anilderinbay.appcent_news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    private val job = Job() // Bir iş açtık

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // Main Thread işini yap sonra main thread e dön


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}