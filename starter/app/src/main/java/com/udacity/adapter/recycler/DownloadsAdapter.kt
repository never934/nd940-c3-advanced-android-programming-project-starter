package com.udacity.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.adapter.diff.DownloadDiffCallback
import com.udacity.databinding.ItemDownloadBinding
import com.udacity.db.entity.DownloadDB
import com.udacity.impl.DownloadListener

class DownloadsAdapter(private val clickListener: DownloadListener) : ListAdapter<DownloadDB, DownloadsAdapter.ViewHolder>(DownloadDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemDownloadBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: DownloadListener, item: DownloadDB) {
            binding.clicklistener = clickListener
            binding.download = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDownloadBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}