package com.udacity.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.udacity.db.view.DownloadView
import com.udacity.repository.DownloadsRepository
import com.udacity.sendNotification
import com.udacity.util.TimeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadReceiver : BroadcastReceiver() {

    private val repository = DownloadsRepository()
    private val scope = CoroutineScope(Dispatchers.Unconfined)

    override fun onReceive(context: Context?, intent: Intent?) {
        val downloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        scope.launch {
            downloadId?.let {
                repository.getDownload(downloadId)?.let { downloadDB ->
                    val download = DownloadView(downloadDB)
                    context?.let {
                        sendNotification(it, download)
                    }
                }
            }
        }
    }

    private fun sendNotification(context: Context, download: DownloadView){
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(download, context)
    }
}