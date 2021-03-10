package com.udacity.screens.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.Constants
import com.udacity.R
import com.udacity.adapter.recycler.DownloadsAdapter
import com.udacity.databinding.ActivityMainBinding
import com.udacity.impl.DownloadListener
import com.udacity.screens.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        binding.content.customButton.setOnClickListener {
            viewModel.startDownload()
        }
        viewModel.loadingPercents.observe(this, {
            binding.content.customButton.setProgress(it)
        })
        viewModel.eventShowChoseToast.observe(this, {
            if (it) {
                Toast.makeText(this, getString(R.string.choose_file_desc), Toast.LENGTH_SHORT).show()
            }
        })
        initRecycler()
        initRadioChoosing()
    }

    private fun initRadioChoosing(){
        binding.content.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.downloadChosen(radioGroup.checkedRadioButtonId)
        }
    }

    private fun initRecycler(){
        val adapter = DownloadsAdapter(DownloadListener {
            it.downloadId?.let { downloadId ->
                startDetailActivity(downloadId)
            }
        })
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (binding.content.downloadsRecycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(positionStart, 0)
            }
        })
        binding.content.downloadsRecycler.adapter = adapter
        viewModel.downloads.observe(this, {
            adapter.submitList(it.reversed())
        })
    }

    private fun startDetailActivity(downloadId: Long){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.KEY_DOWNLOAD_ID, downloadId)
        startActivity(intent)
    }

}
