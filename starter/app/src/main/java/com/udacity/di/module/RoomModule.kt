package com.udacity.di.module

import com.udacity.db.AppDatabase
import com.udacity.db.DownloadsDao
import com.udacity.di.EnvScope
import dagger.Module
import dagger.Provides

@Module
class RoomModule(private val database: AppDatabase){

    @Provides
    @EnvScope
    fun providesRoomDatabase(): AppDatabase {
        return database
    }

    @Provides
    @EnvScope
    fun providesDownloadsDao(database: AppDatabase): DownloadsDao {
        return database.downloadsDao()
    }

}