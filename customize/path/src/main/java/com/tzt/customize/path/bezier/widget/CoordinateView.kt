package com.tzt.customize.path.bezier.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description: 坐标系基础view
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
open class CoordinateView: View {
    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private lateinit var linePaint: Paint
    private lateinit var tableLinePaint: Paint
    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initCoordinate()
    }

    private fun initCoordinate() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 30f
            color = Color.GRAY
        }

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = Color.GRAY
        }

        tableLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = Color.parseColor("#f1f1f1")
        }
    }

    private fun drawCoorDinate(canvas: Canvas?) {
        halfH = height / 2f
        halfW = width / 2f
        canvas?.save()
        // 画布原点平移
        canvas?.translate(halfW, halfH)
        canvas?.drawLine(-halfW, 0f, halfW, 0f, linePaint)
        canvas?.drawLine(0f, -halfH, 0f, halfH, linePaint)

        // 画横坐标
        textPaint.textAlign = Paint.Align.CENTER
        val distance = (textPaint.fontMetrics.bottom - textPaint.fontMetrics.top) / 2 - textPaint.fontMetrics.bottom
        val rect = Rect()
        var xRight = 0.0f
        do {
            xRight+= 50f

            canvas?.drawLine(xRight, -halfH, xRight, halfH, tableLinePaint)
            canvas?.drawLine(xRight, 0f, xRight, -15f, linePaint)

            if (xRight.toInt() % 100 == 0) {
                val textx = xRight.toInt().toString()
                textPaint.getTextBounds(textx, 0, textx.length, rect)
                val baseline = (distance + rect.centerY()) + 40f
                canvas?.drawText(textx, xRight, baseline, textPaint)
            }
        } while (xRight < halfW)

        var xLeft = 0.0f
        do {
            xLeft-= 50f

            canvas?.drawLine(xLeft, -halfH, xLeft, halfH, tableLinePaint)
            canvas?.drawLine(xLeft, 0f, xLeft, -15f, linePaint)
            if (xLeft.toInt() % 100 == 0) {
                val textx = xLeft.toInt().toString()
                textPaint.getTextBounds(textx, 0, textx.length, rect)
                val baseline = (distance + rect.centerY()) + 40f
                canvas?.drawText(textx, xLeft, baseline, textPaint)
            }
        } while (xLeft > -halfW)

        // 画纵坐标
        textPaint.textAlign = Paint.Align.RIGHT
        val distanceY = (textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent) / 2 - textPaint.fontMetrics.descent
        var yTop = 0.0f
        do {
            yTop+= 50f

            canvas?.drawLine(-halfW, yTop, halfW, yTop, tableLinePaint)
            canvas?.drawLine(0f, yTop, 15f, yTop, linePaint)
            if (yTop.toInt() % 100 == 0) {
                val texty = yTop.toInt().toString()
                val baseline = yTop + distanceY
                canvas?.drawText(texty, -40f, baseline, textPaint)
            }
        } while (yTop < halfH)

        var yBottom = 0.0f
        do {
            yBottom-= 50f

            canvas?.drawLine(-halfW, yBottom, halfW, yBottom, tableLinePaint)
            canvas?.drawLine(0f, yBottom, 15f, yBottom, linePaint)
            if (yBottom.toInt() % 100 == 0) {
                val texty = yBottom.toInt().toString()
                val baseline = yBottom + distanceY
                canvas?.drawText(texty, -40f, baseline, textPaint)
            }
        } while (yBottom > -halfH)

        canvas?.restore()
    }

    override fun onDraw(canvas: Canvas?) {
        drawCoorDinate(canvas)
        super.onDraw(canvas)
    }
}