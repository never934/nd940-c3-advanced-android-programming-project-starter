package com.udacity.screens.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.Constants
import com.udacity.R
import com.udacity.adapter.recycler.DownloadsAdapter
import com.udacity.impl.DownloadImpl
import com.udacity.impl.DownloadListener
import com.udacity.screens.detail.DetailActivity
import com.udacity.service.DownloadService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        custom_button.setOnClickListener {
            viewModel.startDownload()
        }
        viewModel.loadingPercents.observe(this, {
            custom_button.setProgress(it)
        })
        initRecycler()
    }

    private fun initRecycler(){
        val adapter = DownloadsAdapter(DownloadListener {
            it.downloadId?.let { downloadId ->
                startDetailActivity(downloadId)
            }
        })
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (downloadsRecycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(positionStart, 0)
            }
        })
        downloadsRecycler.adapter = adapter
        viewModel.downloads.observe(this, {
            adapter.submitList(it.reversed())
        })
    }

    private fun startDetailActivity(downloadId: Long){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.KEY_DOWNLOAD_ID, downloadId)
        startActivity(intent)
    }

    companion object {
        const val URL =
            "https://www.eurofound.europa.eu/sites/default/files/ef_publication/field_ef_document/ef1710en.pdf"
    }


}
