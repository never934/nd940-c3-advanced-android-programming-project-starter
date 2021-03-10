package com.udacity.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.udacity.db.entity.DownloadDB

class DownloadDiffCallback : DiffUtil.ItemCallback<DownloadDB>() {
    override fun areItemsTheSame(oldItem: DownloadDB, newItem: DownloadDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DownloadDB, newItem: DownloadDB): Boolean {
        return oldItem == newItem
    }
}