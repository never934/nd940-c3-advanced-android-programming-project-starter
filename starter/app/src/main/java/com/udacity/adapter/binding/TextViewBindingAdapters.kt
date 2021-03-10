package com.udacity.adapter.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.util.TimeUtils

@BindingAdapter("date")
fun bindDate(view: TextView, date: Long?) {
    date?.let {
        view.text = TimeUtils.getFormattedTime(date)
    }
}