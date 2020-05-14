package com.tzt.customize.paint.widget.color

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Description:画笔颜色设置
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ColorView: View{

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.LEFT
        textSize = 36f
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText("设置颜色 setColor(int color)", 30f, 60f, textPaint)

        paint.color = Color.parseColor("#009688")
        paint.style = Paint.Style.FILL
        canvas?.drawRect(20f, 80f, 220f, 230f, paint)
        paint.color = Color.parseColor("#ff9800")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas?.drawLine(250f, 80f, 400f, 230f, paint)
        textPaint.color = Color.parseColor("#e91e63")
        canvas?.drawText("设置颜色画文字",500f, 155f, textPaint)

        // setARGB 设置透明
        textPaint.color = Color.BLACK
        canvas?.drawText("设置颜色 setARGB(int a, int r, int g, int b)", 30f, 600f, textPaint)

        paint.setARGB(10, 255,33, 200)
        paint.style = Paint.Style.FILL
        canvas?.drawRect(20f, 650f, 220f, 800f, paint)
        paint.setARGB(99, 33, 54, 212)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas?.drawLine(250f, 650f, 400f, 800f, paint)
        textPaint.setARGB(188, 188, 188, 188)
        canvas?.drawText("设置透明度颜色画文字",500f, 730f, textPaint)
    }
}