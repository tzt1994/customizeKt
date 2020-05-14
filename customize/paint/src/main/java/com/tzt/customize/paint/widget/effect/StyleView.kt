package com.tzt.customize.paint.widget.effect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Description:样式
 * @author tangzhentao
 * @since 2020/5/6
 */
class StyleView: View{

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var paintStyle = Paint.Style.FILL

    constructor(context: Context, style: Paint.Style): this(context, null) {
        this.paintStyle = style
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 1f
            color = Color.BLACK
        }
        canvas?.drawRect(width / 4f - (height / 2f - 50f), 50f, width / 4f + height / 2f - 50f, height - 50f, paint)

        paint.apply {
            style = paintStyle
            color = Color.BLACK
            strokeWidth = if (paintStyle == Paint.Style.STROKE || paintStyle == Paint.Style.FILL_AND_STROKE) 18f else 0f
        }
        canvas?.drawCircle(width / 4f, height / 2f, height / 2f - 50f, paint)
        paint.apply {
            textSize = 150f
            textAlign = Paint.Align.CENTER
        }
        canvas?.drawText("汤振涛",width / 4f * 3, height / 2f + 50f, paint)
    }
}