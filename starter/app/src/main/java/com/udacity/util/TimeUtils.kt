package com.udacity.util

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min

class TimeUtils {
    companion object {
        fun getCurrentUnixTime(): Long {
            return System.currentTimeMillis() / 1000L
        }

        fun getFormattedTime(unixtime: Long): String {
            val date = Date(unixtime * 1000)
            return SimpleDateFormat("dd MMMM hh:mm").format(date)
        }

        fun getTimeDifference(firstUnix: Long, secUnix: Long): Long {
            val time = max(firstUnix, secUnix) - min(firstUnix, secUnix)
            return time
        }

        fun formatUnixTimeSeconds(time: Long): String {
            val seconds = time % 60
            val minutes = seconds / 60
            val hours = minutes / 60
            var secondsRepresentation = seconds.toString()
            var minutesRepresentation = minutes.toString()
            var hoursRepresentation = hours.toString()
            if(secondsRepresentation.length == 1)secondsRepresentation = "0$secondsRepresentation"
            if(minutesRepresentation.length == 1)minutesRepresentation = "0$minutesRepresentation"
            if(hoursRepresentation.length == 1)hoursRepresentation = "0$hoursRepresentation"
            return "$hoursRepresentation:$minutesRepresentation:$secondsRepresentation"
        }
    }
}