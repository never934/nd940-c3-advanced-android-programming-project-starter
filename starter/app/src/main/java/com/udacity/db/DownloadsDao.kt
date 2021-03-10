package com.udacity.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.db.entity.DownloadDB

@Dao
interface DownloadsDao {
    @Query("select * from downloaddb order by updatedDate")
    fun getDownloads(): LiveData<List<DownloadDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(download: DownloadDB)

    @Update
    fun update(download: DownloadDB)
}