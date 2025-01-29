package com.splash.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import kotlin.math.cos
import kotlin.math.sin

class SunView(
    context: Context, attrs: AttributeSet? = null,
) : AnimatedView(context, attrs) {

    private val sunPaint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
    }

    private val rayPaint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    private var sunCenterX = 0f
    private var sunCenterY = 0f
    private var sunRadius = 0f
    private var rayCount = 18

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sunCenterX = width * 0.55f
        sunCenterY = height * 0.4f
        sunRadius = width * 0.15f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSun(canvas)
        drawSunRays(canvas)
    }

    private fun drawSun(canvas: Canvas) {
        canvas.drawCircle(sunCenterX, sunCenterY, sunRadius, sunPaint)
    }

    private fun drawSunRays(canvas: Canvas) {
        for (i in 0 until rayCount) {
            val angle = Math.toRadians((360.0 / rayCount) * i)
            val cosAngle = cos(angle).toFloat()
            val sinAngle = sin(angle).toFloat()
            val rayLength = sunRadius + (sunRadius * animatedFactor)

            val startX = sunCenterX + sunRadius * cosAngle
            val startY = sunCenterY + sunRadius * sinAngle
            val stopX = sunCenterX + rayLength * cosAngle
            val stopY = sunCenterY + rayLength * sinAngle
            canvas.drawLine(startX, startY, stopX, stopY, rayPaint)
        }
    }

    override fun onAnimationUpdate() {
        rayPaint.alpha = animatedAlphaValue
    }
}