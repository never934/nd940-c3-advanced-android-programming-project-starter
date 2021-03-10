package com.udacity.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.udacity.db.entity.DownloadDB
import com.udacity.db.view.DownloadView

class DownloadDiffCallback : DiffUtil.ItemCallback<DownloadView>() {
    override fun areItemsTheSame(oldItem: DownloadView, newItem: DownloadView): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DownloadView, newItem: DownloadView): Boolean {
        return oldItem == newItem
    }
}