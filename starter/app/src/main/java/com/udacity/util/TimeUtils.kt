package com.udacity.util

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object{
        fun getCurrentUnixTime() : Long {
            return System.currentTimeMillis() / 1000L
        }
        fun getFormattedTime(unixtime: Long): String {
            val date = Date(unixtime*1000)
            return SimpleDateFormat("dd MMMM hh:mm").format(date)
        }
    }
}