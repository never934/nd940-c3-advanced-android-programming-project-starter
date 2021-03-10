package com.udacity.impl

import com.udacity.db.entity.DownloadDB

class DownloadListener(val clickListener: (download: DownloadDB) -> Unit) {
    fun onClick(download: DownloadDB) = clickListener(download)
}