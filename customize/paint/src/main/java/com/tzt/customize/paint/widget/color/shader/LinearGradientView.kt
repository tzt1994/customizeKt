package com.tzt.studykt.customView.paint.widget.color.shader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description:线性渐变
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class LinearGradientView: View{

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val paint2: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var tileMode = Shader.TileMode.CLAMP

    private lateinit var shader: LinearGradient

    constructor(context: Context, tile: Shader.TileMode): this(context, null) {
        this.tileMode = tile
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 设置线性渐变
        shader = LinearGradient(50f, 50f, width / 2f, height / 2f, Color.parseColor("#e91e63"), Color.parseColor("#2196f3"), tileMode)
        paint.shader = shader
        canvas?.drawRect(50f, 50f, width - 50f, height - 50f, paint)

        paint2.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20f
        }
        canvas?.drawPoint(50f, 50f, paint2)
        canvas?.drawPoint(width / 2f, height / 2f, paint2)

        paint2.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            textSize = 36f
        }
        canvas?.drawText("点1(50, 50)", 100f, 100f, paint2)
        canvas?.drawText("点2(${(width / 2f).toInt()}, ${(height / 2f).toInt()})", width / 2f + 50f, height / 2f + 50f, paint2)
    }
}