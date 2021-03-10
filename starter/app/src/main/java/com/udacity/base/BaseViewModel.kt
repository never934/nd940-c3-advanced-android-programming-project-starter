package com.udacity.base

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.udacity.App

abstract class BaseViewModel : ViewModel() {
    val context: Context = App.androidComponent.context
}