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
class CircleView: View {
    private lateinit var paint: Paint

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    private fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        canvas?.drawCircle(width / 4f, height / 4f, 200f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        canvas?.drawCircle(width / 4f * 3, height / 4f, 200f, paint)


        paint.style = Paint.Style.FILL
        paint.color = Color.BLUE
        canvas?.drawCircle(width / 4f, height / 4f * 3, 200f, paint)

        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 50f
        canvas?.drawCircle(width / 4f * 3, height / 4f * 3, 200f, paint)
    }
}