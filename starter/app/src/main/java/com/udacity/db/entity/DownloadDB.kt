package com.udacity.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DownloadDB(
    @PrimaryKey
    val id: String,
    val downloadId: Long,
    val downloadUrl: String,
    var downloaded: Boolean?,
    val createdDate: Long,
    var updatedDate: Long,
) : Parcelable
