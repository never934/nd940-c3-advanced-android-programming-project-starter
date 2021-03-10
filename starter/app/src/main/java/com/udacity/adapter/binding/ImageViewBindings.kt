package com.udacity.adapter.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("srcId")
fun bindSrcId(view: ImageView, id: Int?) {
    id?.let {
        view.setImageResource(it)
    }
}