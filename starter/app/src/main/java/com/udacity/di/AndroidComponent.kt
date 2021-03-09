package com.udacity.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.udacity.di.module.AndroidModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class])
interface AndroidComponent {
    val context: Context
    val resources: Resources
    val application: Application
}