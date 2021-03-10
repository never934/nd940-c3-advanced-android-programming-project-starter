package com.udacity.db.view

import com.udacity.App
import com.udacity.R
import com.udacity.db.entity.DownloadDB
import com.udacity.util.TimeUtils

data class DownloadView(
    var id: String? = null,
    var downloadId: Long? = null,
    var downloadUrl: String? = null,
    var status: String? = null,
    var statusColorId: Int? = null,
    var statusIconId: Int? = null,
    var downloadTime: String? = null,
    var createdDate: String? = null,
    var updatedDate: String? = null,
){
    constructor(downloadDB: DownloadDB) : this(){
        this.id = downloadDB.id
        this.downloadId = downloadDB.downloadId
        this.downloadUrl = downloadDB.downloadUrl
        this.downloadTime = TimeUtils.formatUnixTimeSeconds(TimeUtils.getTimeDifference(downloadDB.createdDate, downloadDB.updatedDate))
        this.createdDate = TimeUtils.getFormattedTime(downloadDB.createdDate)
        this.updatedDate = TimeUtils.getFormattedTime(downloadDB.updatedDate)
        when(downloadDB.downloaded){
            true -> {
                this.status = App.androidComponent.context.getString(R.string.success)
                this.statusColorId = R.color.accept
                this.statusIconId = R.drawable.ic_check_24
            }
            false -> {
                this.status = App.androidComponent.context.getString(R.string.fail)
                this.statusColorId = R.color.alert
                this.statusIconId = R.drawable.ic_error_24
            }
            null -> {
                this.status = App.androidComponent.context.getString(R.string.loading)
                this.statusColorId = R.color.colorAccent
                this.statusIconId = R.drawable.ic_time_24
            }
        }
    }
}