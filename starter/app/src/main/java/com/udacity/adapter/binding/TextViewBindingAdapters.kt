package com.udacity.adapter.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("date")
fun bindDate(view: TextView, date: Long?) {
    date?.let {
        view.text = date.toString()
    }
}