package com.splash.customView

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

abstract class AnimatedView(
    context: Context, attrs: AttributeSet? = null,
) : View(context, attrs) {

    protected var animatedFactor = 0f
    protected var animatedAlphaValue = 0
    private var animationDuration = 1000L

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = animationDuration
        repeatCount = ValueAnimator.INFINITE
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { valueAnimator ->
            animatedFactor = valueAnimator.animatedValue as Float
            animatedAlphaValue = (255 * (1 - animatedFactor)).toInt()
            onAnimationUpdate()
            invalidate()
        }
    }

    protected abstract fun onAnimationUpdate()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }
}