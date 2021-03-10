package com.udacity.adapter.binding

import android.widget.TextView
import android.widget.ViewFlipper
import androidx.databinding.BindingAdapter
import com.udacity.customview.ViewField

@BindingAdapter("content")
fun bindContent(view: ViewField, content: String?) {
    view.setContent(content)
}

@BindingAdapter("contentColor")
fun bindContent(view: ViewField, color: Int?) {
    view.setContentColor(color)
}
