package com.udacity.customview


sealed class ButtonState {
    object Loading : ButtonState()
    object Completed : ButtonState()
}