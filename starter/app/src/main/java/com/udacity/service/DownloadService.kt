package com.udacity.service

import android.app.DownloadManager
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.udacity.base.BaseService
import com.udacity.Constants
import com.udacity.R
import com.udacity.impl.DownloadImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DownloadService : BaseService() {

    private val binder: IBinder = DownloadBinder(this)
    private var callback: DownloadImpl? = null
    private var url: String? = null

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


    class DownloadBinder(private var service: DownloadService) : Binder() {
        fun getService(): DownloadService {
            return service
        }
    }

    fun setCallback(callback: DownloadImpl) {
        this.callback = callback
    }

}