package com.udacity.base

import android.content.Context
import com.udacity.App
import com.udacity.di.RoomComponent
import com.udacity.di.module.RoomModule

abstract class BaseRepository {
    protected val room: RoomComponent = App.roomComponent
    protected val context: Context = App.androidComponent.context
}