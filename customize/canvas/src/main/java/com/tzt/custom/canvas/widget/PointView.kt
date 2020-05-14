package com.tzt.studykt.customView.canvasdraw.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class PointView: View {
    private lateinit var paint: Paint

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    private fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 20f
            color = Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawPoint(width / 4f, height / 2f, paint)
        paint.strokeCap = Paint.Cap.SQUARE
        canvas?.drawPoint(width / 4f * 3, height / 2f, paint)
    }
}