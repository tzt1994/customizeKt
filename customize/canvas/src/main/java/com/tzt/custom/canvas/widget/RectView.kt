package com.tzt.studykt.customView.canvasdraw.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class RectView: View {
    private lateinit var paint: Paint

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    private fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectF = RectF(width / 2f -100, height / 2f - 100, width / 2f + 100, height / 2f + 100)
        canvas?.drawRect(rectF, paint)
    }
}