package com.udacity.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.udacity.R
import com.udacity.base.BaseCustomView
import com.udacity.databinding.ItemViewFieldBinding

class ViewField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {
    private lateinit var binding: ItemViewFieldBinding

    init {
        init(attrs, context)
    }

    private fun init(attrs: AttributeSet?, context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.item_view_field, this, true)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ViewField)
        val hint = ta.getString(R.styleable.ViewField_hint)

        try {
            binding.hint.text = hint
        } finally {
            ta.recycle()
        }
    }

    fun setContent(text: String?){
        text?.let {
            binding.content.text = text
        }
    }

    fun setContentColor(color: Int?){
        color?.let {
            binding.content.setTextColor(context.getColor(it))
        }
    }
}