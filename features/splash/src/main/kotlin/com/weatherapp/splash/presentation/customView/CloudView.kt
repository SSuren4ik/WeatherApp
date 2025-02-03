package com.weatherapp.splash.presentation.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import kotlin.math.cos
import kotlin.math.sin

class CloudView(
    context: Context, attrs: AttributeSet? = null,
) : AnimatedView(context, attrs) {

    private var centerX = 0f
    private var bottomY = 0f
    private var baseRadius = 0f
    private var largeRadius = 0f
    private var mediumRadius = 0f
    private var rainDropCount = 10

    private val cloudPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val rainPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width * 0.35f
        bottomY = height * 0.55f
        baseRadius = width * 0.1f
        largeRadius = baseRadius * 1.5f
        mediumRadius = baseRadius * 1.2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCloud(canvas)
        drawRains(canvas)
    }

    private fun drawCloud(canvas: Canvas) {
        val centerFirstCircleX = centerX
        val centerSecondCircleX = centerX + baseRadius
        val centerThirdCircleX = centerSecondCircleX + largeRadius
        val centerFortyCircleX = centerThirdCircleX + mediumRadius

        canvas.drawCircle(centerFirstCircleX, bottomY - baseRadius, baseRadius, cloudPaint)
        canvas.drawCircle(centerSecondCircleX, bottomY - largeRadius, largeRadius, cloudPaint)
        canvas.drawCircle(centerThirdCircleX, bottomY - mediumRadius, mediumRadius, cloudPaint)
        canvas.drawCircle(centerFortyCircleX, bottomY - baseRadius, baseRadius, cloudPaint)
    }

    private fun drawRains(canvas: Canvas) {
        val rainDropLength = baseRadius / 5f
        val rainDropInterval = (baseRadius * 2 + largeRadius + mediumRadius) / rainDropCount
        val angle = Math.toRadians(60.0).toFloat()
        val cosAngle = cos(angle)
        val sinAngle = sin(angle)

        for (i in 0 until rainDropCount) {
            var startX = centerX + i * rainDropInterval
            var startY = bottomY + rainDropLength
            val stopX = startX - baseRadius * animatedFactor * cosAngle
            val stopY = startY + baseRadius * animatedFactor * sinAngle
            startX = stopX + rainDropLength * cosAngle
            startY = stopY - rainDropLength * sinAngle
            canvas.drawLine(startX, startY, stopX, stopY, rainPaint)
        }
    }

    override fun onAnimationUpdate() {
        rainPaint.alpha = animatedAlphaValue
    }
}