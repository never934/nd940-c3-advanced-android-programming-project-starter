package com.udacity.screens.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.App
import com.udacity.Constants
import com.udacity.base.BaseViewModel
import com.udacity.db.entity.DownloadDB
import com.udacity.impl.DownloadImpl
import com.udacity.repository.DownloadsRepository
import com.udacity.service.DownloadService

class MainViewModel : BaseViewModel(), DownloadImpl {

    private var _loadingPercents: MutableLiveData<Int> = MutableLiveData()
    val loadingPercents: LiveData<Int>
    get() = _loadingPercents

    private var repository = DownloadsRepository()
    val downloads: LiveData<List<DownloadDB>> = repository.downloads

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
        val videosIntent = Intent(App.androidComponent.context, DownloadService::class.java)
        videosIntent.putExtra(Constants.KEY_URL, MainActivity.URL)
        App.androidComponent.context.startService(videosIntent)
    }

    override fun downloaded(percents: Int) {
        _loadingPercents.value = percents
    }

    override fun onCleared() {
        super.onCleared()
        context.unbindService(downloadServiceConnection)
    }

}