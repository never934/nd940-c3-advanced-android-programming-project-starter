package com.udacity.base

import android.app.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

abstract class BaseService : Service() {
    protected var serviceScope: CoroutineScope? = null

    override fun onCreate() {
        super.onCreate()
        serviceScope = CoroutineScope(Dispatchers.IO)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope?.coroutineContext?.cancelChildren()
        serviceScope?.coroutineContext?.cancel()
        serviceScope = null
    }

}