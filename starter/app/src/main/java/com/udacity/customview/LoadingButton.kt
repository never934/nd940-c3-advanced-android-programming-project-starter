package com.udacity.customview

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.udacity.R
import kotlin.math.min
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        color = context.getColor(android.R.color.black)
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        isClickable = new != ButtonState.Loading
    }
    private var valueAnimator: ValueAnimator? = null
    private var textColor: Int = android.R.color.white
    private var backgroundColorResource: Int = R.color.colorPrimary

    init{
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton)
        val backgroundColor = ta.getColor(R.styleable.LoadingButton_loading_button_background_color, 0)
        val textColor = ta.getColor(R.styleable.LoadingButton_loading_button_text_color,0)
        try {
            this.textColor = textColor
            this.backgroundColorResource = backgroundColor
            isClickable = true
        } finally {
            ta.recycle()
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawDefault(canvas)
        when(buttonState){
            ButtonState.Loading -> {
                drawProgress(canvas)
                drawProgressCircle(canvas)
                drawText(canvas, context.getString(R.string.button_loading))
            }
            ButtonState.Completed -> {
                drawText(canvas, context.getString(R.string.button_name))
            }
            else -> {}
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun performClick(): Boolean {
        if (super.performClick())return true
        return true
    }

    private fun drawText(canvas: Canvas?, text: String?){
        text?.let {
            paint.color = textColor
            paint.textSize = height/2.toFloat()
            val yPos = (height / 2 - (paint.descent() + paint.ascent()) / 2)
            canvas?.drawText(text, (width / 2).toFloat(), yPos, paint)
        }
    }

    private fun drawDefault(canvas: Canvas?){
        paint.color = backgroundColorResource
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    private fun drawProgress(canvas: Canvas?){
        paint.color = context.getColor(R.color.colorPrimaryDark)
        val width = width.toFloat() * valueAnimator?.animatedValue as Float
        canvas?.drawRect(0f, 0f, width, height.toFloat(), paint)
    }

    private fun drawProgressCircle(canvas: Canvas?){
        val circleSize = (min(width, height)/2).toFloat()
        val marginHorizontal = (width/4).toFloat()
        val marginVertical = (height/4).toFloat()
        val rectangle = RectF((width.toFloat()-marginHorizontal)-circleSize, marginVertical, (width.toFloat()-marginHorizontal), circleSize+marginVertical)
        val proportion = valueAnimator?.animatedValue as Float
        paint.color = context.getColor(R.color.colorAccent)
        canvas?.drawArc(rectangle, -90f, proportion * 360, true, paint)
        paint.color = context.getColor(R.color.colorPrimaryDark)
        canvas?.drawArc(rectangle, -90f + proportion * 360, (1 - proportion) * 360, true, paint)
    }

    fun setProgress(percents: Int){
        var lastProgress = 0f
        valueAnimator?.let {
            lastProgress = valueAnimator?.animatedValue as Float
            valueAnimator?.cancel()
        }
        if (percents in 0..100) {
            buttonState = ButtonState.Loading
            valueAnimator = ValueAnimator.ofFloat(lastProgress, (percents.toFloat()/100))
            valueAnimator?.addUpdateListener {
                if (it.animatedValue == 1f){
                    valueAnimator?.cancel()
                    valueAnimator = null
                    buttonState = ButtonState.Completed
                }
                invalidate()
            }
            valueAnimator?.start()
        }else{
            buttonState = ButtonState.Completed
            invalidate()
        }
    }

}