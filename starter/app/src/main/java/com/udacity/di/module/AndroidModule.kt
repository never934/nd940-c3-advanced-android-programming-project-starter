package com.udacity.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val mContext: Context, private val application: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return mContext
    }

    @Provides
    @Singleton
    internal fun provideResources(): Resources {
        return mContext.resources
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }
}