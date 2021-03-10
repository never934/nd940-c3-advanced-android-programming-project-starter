package com.udacity.repository

import androidx.lifecycle.LiveData
import com.udacity.base.BaseRepository
import com.udacity.db.entity.DownloadDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DownloadsRepository : BaseRepository(){

    val downloads: LiveData<List<DownloadDB>> = room.downloadsDao.getDownloads()

    suspend fun saveDownload(downloadDB: DownloadDB){
        withContext(Dispatchers.IO){
            room.downloadsDao.insert(downloadDB)
        }
    }

    suspend fun updateDownload(downloadDB: DownloadDB?){
        withContext(Dispatchers.IO){
            downloadDB?.let {
                room.downloadsDao.update(it)
            }
        }
    }
}