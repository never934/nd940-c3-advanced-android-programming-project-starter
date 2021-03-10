package com.udacity.screens.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.base.BaseViewModel
import com.udacity.db.entity.DownloadDB
import com.udacity.db.view.DownloadView
import com.udacity.repository.DownloadsRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val downloadId: Long) : BaseViewModel(){

    private val repository = DownloadsRepository()
    private val _downloads: LiveData<List<DownloadDB>> = repository.downloads
    val downloadView: LiveData<DownloadView> = Transformations.switchMap(_downloads){
        repository.downloads.map { DownloadView(it.first { downloadDB -> downloadDB.downloadId == downloadId }) }
    }

    class Factory(val downloadId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(downloadId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}