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
class ArcView: View {
    private lateinit var paint: Paint

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    private fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        canvas?.drawArc(width / 2f - 200, height / 2f - 200, width / 2f + 200, height / 2f + 200, -30f, 120f, true, paint)

        canvas?.drawArc(width / 2f - 200, height / 2f + 250, width / 2f + 200, height / 2f + 350, 90f, 180f, true, paint)

        paint.style = Paint.Style.STROKE
        canvas?.drawArc(width / 2f - 200, height / 2f - 200, width / 2f + 200, height / 2f + 200,  270f, 70f, false, paint)
    }
}