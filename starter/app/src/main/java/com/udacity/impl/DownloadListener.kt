package com.udacity.impl

import com.udacity.db.view.DownloadView

class DownloadListener(val clickListener: (download: DownloadView) -> Unit) {
    fun onClick(download: DownloadView) = clickListener(download)
}