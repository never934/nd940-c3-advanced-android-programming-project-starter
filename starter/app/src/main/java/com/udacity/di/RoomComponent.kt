package com.udacity.di

import com.udacity.db.DownloadsDao
import com.udacity.di.module.RoomModule
import dagger.Component

@EnvScope
@Component(dependencies = [AndroidComponent::class], modules = [RoomModule::class])
interface RoomComponent {
    val asteroidsDao: DownloadsDao
}