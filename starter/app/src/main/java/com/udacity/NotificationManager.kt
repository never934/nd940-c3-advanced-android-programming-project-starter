package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.udacity.db.view.DownloadView
import com.udacity.screens.detail.DetailActivity

private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(downloadView: DownloadView, applicationContext: Context) {

    val channelName = applicationContext.getString(R.string.notification_channel_name)
    val channelId = applicationContext.getString(R.string.notification_channel_id)
    createChannel(channelId, channelName)

    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra(Constants.KEY_DOWNLOAD_ID, downloadView.downloadId)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(android.R.drawable.stat_sys_download_done)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText("id: ${downloadView.downloadId}\nstatus changed")
        .setContentIntent(contentPendingIntent)
        .addAction(R.drawable.ic_arrow_forward_24, App.androidComponent.context.getString(R.string.show), contentPendingIntent)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}

private fun NotificationManager.createChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.setShowBadge(false)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "Time for breakfast"
        createNotificationChannel(notificationChannel)
    }
}