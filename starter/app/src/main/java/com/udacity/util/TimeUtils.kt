package com.udacity.util

class TimeUtils {
    companion object{
        fun getCurrentUnixTime() : Long {
            return System.currentTimeMillis() / 1000L
        }
    }
}