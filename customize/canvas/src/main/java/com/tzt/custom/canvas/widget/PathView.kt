package com.tzt.studykt.customView.canvasdraw.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class PathView: View {
    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private lateinit var linePaint: Paint
    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    private fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 16f
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
        }

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            color = Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        halfH = height / 2f
        halfW = width / 2f
        canvas?.drawColor(Color.WHITE)
        // 画布原点平移
        canvas?.translate(halfW, halfH)
        canvas?.drawLine(-halfW, 0f, halfW, 0f, linePaint)
        canvas?.drawLine(0f, -halfH, 0f, halfH, linePaint)

        // 画横坐标
        var xRight = 0.0f
        do {
            xRight+= 100f

            val textx = xRight.toInt().toString()
            val distance = (textPaint.fontMetrics.bottom - textPaint.fontMetrics.top) / 2 - textPaint.fontMetrics.bottom
            val rect = Rect()
            textPaint.getTextBounds(textx, 0, textx.length, rect)
            val baseline = distance + rect.centerY() + 20
            canvas?.drawText(textx, xRight, baseline, textPaint)
            canvas?.drawLine(xRight, 0f, xRight, -10f, linePaint)
        } while (xRight < halfW)

        var xLeft = 0.0f
        do {
            xLeft-= 100f

            val textx = xLeft.toInt().toString()
            val distance = (textPaint.fontMetrics.bottom - textPaint.fontMetrics.top) / 2 - textPaint.fontMetrics.bottom
            val rect = Rect()
            textPaint.getTextBounds(textx, 0, textx.length, rect)
            val baseline = distance + rect.centerY() + 20
            canvas?.drawText(textx, xLeft, baseline, textPaint)
            canvas?.drawLine(xLeft, 0f, xLeft, -10f, linePaint)
        } while (xLeft > -halfW)
    }
}