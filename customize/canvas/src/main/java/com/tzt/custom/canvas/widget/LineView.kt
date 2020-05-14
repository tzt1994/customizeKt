package com.tzt.studykt.customView.canvasdraw.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class LineView: View {
    private lateinit var paint: Paint

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    private fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 10f
            color = Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val path = Path()
        path.moveTo(300f, 300f)
        path.lineTo(500f, 500f)
        path.rLineTo(800f, 300f)
        path.close()
        canvas?.drawPath(path, paint)
    }
}