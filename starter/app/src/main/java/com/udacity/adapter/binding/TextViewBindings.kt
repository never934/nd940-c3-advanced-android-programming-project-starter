package com.udacity.adapter.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textColorId")
fun bindTextColorId(view: TextView, id: Int?) {
    id?.let {
        view.setTextColor(view.context.getColor(id))
    }
}