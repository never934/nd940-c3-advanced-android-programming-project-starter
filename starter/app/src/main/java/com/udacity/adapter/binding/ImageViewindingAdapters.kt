package com.udacity.adapter.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.R
import com.udacity.util.TimeUtils

@BindingAdapter("downloadStatus")
fun bindDownloadStatus(view: ImageView, boolean: Boolean?) {
    boolean?.let {
        if (it){
            view.setImageResource(R.drawable.ic_check_24)
        }else{
            view.setImageResource(R.drawable.ic_error_24)
        }
    } ?: run {
        view.setImageResource(R.drawable.ic_time_24)
    }
}