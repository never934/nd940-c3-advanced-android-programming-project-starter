package com.udacity.screens.detail

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.udacity.Constants
import com.udacity.R
import com.udacity.databinding.ActivityDetailBinding
import com.udacity.screens.main.MainViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelNotifications()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        (intent.extras?.getLong(Constants.KEY_DOWNLOAD_ID))?.let {
            viewModel = ViewModelProvider(this, DetailViewModel.Factory(it)).get(DetailViewModel::class.java)
            binding.viewModel = viewModel
        }
        okButton.setOnClickListener { finish() }
    }

    private fun cancelNotifications() {
        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }


}
