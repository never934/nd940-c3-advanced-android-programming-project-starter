package com.udacity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.udacity.impl.DownloadImpl
import com.udacity.service.DownloadService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), DownloadImpl {

    private var boundDownloadService = false
    private var downloadService: DownloadService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        custom_button.setOnClickListener {
            download()
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(Intent(this, DownloadService::class.java), downloadServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (boundDownloadService) {
            downloadService?.setCallback(this)
            unbindService(downloadServiceConnection)
            boundDownloadService = false
        }
    }

    override fun downloaded(percents: Int) {
        custom_button.setProgress(percents)
    }

    private fun download() {
        val videosIntent = Intent(this, DownloadService::class.java)
        videosIntent.putExtra(Constants.KEY_URL, URL)
        startService(videosIntent)
    }

    private val downloadServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            val binder = service as DownloadService.DownloadBinder
            downloadService = binder.getService()
            boundDownloadService = true
            downloadService?.setCallback(this@MainActivity)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            boundDownloadService = false
        }
    }

    companion object {
        private const val URL =
            "https://www.eurofound.europa.eu/sites/default/files/ef_publication/field_ef_document/ef1710en.pdf"
        private const val CHANNEL_ID = "channelId"
    }


}
