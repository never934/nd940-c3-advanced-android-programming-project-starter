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
            "https://r4---sn-aigzrn7k.googlevideo.com/videoplayback?expire=1615335293&ei=HbtHYPT3JoWC0wX-oL8Y&ip=37.120.133.133&id=o-ALUTE91UTRYwXHXw6Q8AsDTZpmbP1-oiHDmLq758ktw5&itag=22&source=youtube&requiressl=yes&mh=eN&mm=31%2C26&mn=sn-aigzrn7k%2Csn-4g5e6nzz&ms=au%2Conr&mv=m&mvi=4&pl=24&initcwndbps=781250&vprv=1&mime=video%2Fmp4&ns=PwhELgK7xaHBIlS2NvFGai8F&cnr=14&ratebypass=yes&dur=1740.056&lmt=1614872273143931&mt=1615313325&fvip=4&fexp=24001373%2C24007246&beids=9466585&c=WEB&txp=5535432&n=OGLR8M_MOHPVda1y&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAIK5Jpcm5mFHj_kXxh4NFZ7hMz8SfW6CxDBrAlCTV3GBAiEA2vDLtv1YxjXwGlux9JSlyz_zIFVnaCSQWOEKXapH0oI%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRgIhAOJmCRjFU9X086AV3pZr-NCjU3M8I-UMa6wWCQEjLYzfAiEA7ZQlSwT4MfeFIi1WgZXRuA9brJosPnzIAf27jSj69mY%3D&title=Переделали%20такси%20в%20E63s%20AMG"
        private const val CHANNEL_ID = "channelId"
    }


}
