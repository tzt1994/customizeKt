package com.tzt.customize.propertyanimation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description: 属性拆分
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class HoldersPropertyView: View{
    var progress: Int = 50
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF1493")
        style = Paint.Style.STROKE
        strokeWidth = 60f
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100f
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.parseColor("#696969"))
        val percent = "$progress%"
        val fontMetrics = textPaint.fontMetrics
        val baseline = height / 2f + (fontMetrics.descent - fontMetrics.ascent) / 2f - fontMetrics.descent

        canvas?.drawText(percent,width / 2f, baseline, textPaint)
        canvas?.drawArc(RectF(30f, 30f, width - 30f, height - 30f),
            120f, 300f * (progress * 1f / 100f), false, paint)
    }
}