package com.udacity

import android.app.Application
import com.udacity.db.AppDatabase
import com.udacity.di.AndroidComponent
import com.udacity.di.DaggerAndroidComponent
import com.udacity.di.DaggerRoomComponent
import com.udacity.di.RoomComponent
import com.udacity.di.module.AndroidModule
import com.udacity.di.module.RoomModule

class App : Application() {

    companion object {
        lateinit var androidComponent: AndroidComponent
        lateinit var roomComponent: RoomComponent
    }

    override fun onCreate() {
        super.onCreate()
        androidComponent = DaggerAndroidComponent.builder()
            .androidModule(AndroidModule(this, this))
            .build()
        roomComponent = DaggerRoomComponent.builder()
            .androidComponent(androidComponent)
            .roomModule(RoomModule(AppDatabase.getInstance(this)))
            .build()
    }
}