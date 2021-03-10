package com.udacity.screens.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.App
import com.udacity.Constants
import com.udacity.R
import com.udacity.base.BaseViewModel
import com.udacity.db.entity.DownloadDB
import com.udacity.db.view.DownloadView
import com.udacity.impl.DownloadImpl
import com.udacity.repository.DownloadsRepository
import com.udacity.service.DownloadService
import kotlinx.android.synthetic.main.content_main.view.*

class MainViewModel : BaseViewModel(), DownloadImpl {

    private var _loadingPercents: MutableLiveData<Int> = MutableLiveData()
    val loadingPercents: LiveData<Int>
    get() = _loadingPercents

    private var repository = DownloadsRepository()
    private val _downloads: LiveData<List<DownloadDB>> = repository.downloads
    val downloads: LiveData<List<DownloadView>>
    get() = Transformations.map(_downloads){
        it.map { downloadDB ->  DownloadView(downloadDB) }
    }
    private var downloadUrl: String? = null
    private var _eventShowChoseToast: MutableLiveData<Boolean> = MutableLiveData(false)
    val eventShowChoseToast: LiveData<Boolean>
    get() = _eventShowChoseToast

    // service
    private var boundDownloadService = false
    private var downloadService: DownloadService? = null
    private val downloadServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            val binder = service as DownloadService.DownloadBinder
            downloadService = binder.getService()
            boundDownloadService = true
            downloadService?.setCallback(this@MainViewModel)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            boundDownloadService = false
        }
    }

    init {
        context.bindService(Intent(context, DownloadService::class.java), downloadServiceConnection, Context.BIND_AUTO_CREATE)
    }

    fun startDownload(){
        downloadUrl?.let {
            val videosIntent = Intent(App.androidComponent.context, DownloadService::class.java)
            videosIntent.putExtra(Constants.KEY_URL, downloadUrl)
            App.androidComponent.context.startService(videosIntent)
        } ?: run {
            _eventShowChoseToast.value = true
        }
    }

    fun downloadChosen(id: Int){
        downloadUrl = when(id){
            R.id.glideRadio -> context.getString(R.string.glide_url)
            R.id.retrofitRadio -> context.getString(R.string.retrofit_url)
            R.id.courseRadio -> context.getString(R.string.course_url)
            else -> null
        }
    }

    fun choseToastShown(){
        _eventShowChoseToast.value = false
    }

    override fun downloaded(percents: Int) {
        _loadingPercents.value = percents
    }

    override fun onCleared() {
        super.onCleared()
        context.unbindService(downloadServiceConnection)
    }

}