package com.udacity.service

import android.app.DownloadManager
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.switchMap
import com.udacity.base.BaseService
import com.udacity.Constants
import com.udacity.R
import com.udacity.db.entity.DownloadDB
import com.udacity.impl.DownloadImpl
import com.udacity.repository.DownloadsRepository
import com.udacity.util.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class DownloadService : BaseService() {

    private val binder: IBinder = DownloadBinder(this)
    private var callback: DownloadImpl? = null
    private var url: String? = null
    private val repository: DownloadsRepository = DownloadsRepository()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let { url = it.getString(Constants.KEY_URL) }
        url?.let {
            serviceScope?.launch {
                downloadContent(it)
            }
        } ?: run {
            callback?.downloaded(100)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private suspend fun downloadContent(url: String){
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)
        var downloading = true
        saveDownload(downloadId)
        while (downloading) {
            val q = DownloadManager.Query()
            q.setFilterById(downloadId)
            val cursor: Cursor = downloadManager.query(q)
            cursor.moveToFirst()
            val bytesDownloaded: Int = cursor.getInt(
                cursor
                    .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            )
            val bytesTotal: Int =
                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) === DownloadManager.STATUS_SUCCESSFUL) {
                downloading = false
                updateDownload(downloadId, true)
            }else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) === DownloadManager.STATUS_FAILED){
                downloading = false
                updateDownload(downloadId, false)
            }
            if (bytesTotal != 0) {
                val progress = ((bytesDownloaded * 100L) / bytesTotal).toInt()
                withContext(Dispatchers.Main){
                    callback?.downloaded(progress)
                }
            }
            cursor.close()
        }
    }

    private suspend fun saveDownload(downloadId: Long){
        withContext(Dispatchers.IO){
            val time = TimeUtils.getCurrentUnixTime()
            repository.saveDownload(DownloadDB(
                id = UUID.randomUUID().toString(),
                downloadId = downloadId,
                downloadUrl = url ?: "",
                downloaded = null,
                createdDate = time,
                updatedDate = time
            ))
        }
    }

    private suspend fun updateDownload(downloadId: Long, downloaded: Boolean){
        withContext(Dispatchers.IO){
            val download = repository.getDownload(downloadId)
            val time = TimeUtils.getCurrentUnixTime()
            download?.downloaded = downloaded
            download?.updatedDate = time
            repository.saveDownload(download)
        }
    }


    class DownloadBinder(private var service: DownloadService) : Binder() {
        fun getService(): DownloadService {
            return service
        }
    }

    fun setCallback(callback: DownloadImpl) {
        this.callback = callback
    }

}