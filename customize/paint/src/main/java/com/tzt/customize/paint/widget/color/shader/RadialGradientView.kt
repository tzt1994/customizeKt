package com.tzt.studykt.customView.paint.widget.color.shader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description:辐射渐变
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class RadialGradientView: View{

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var tileMode = Shader.TileMode.CLAMP

    constructor(context: Context, tile: Shader.TileMode): this(context, null) {
        this.tileMode = tile
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 36f
        }
        canvas?.drawText("中心点(${(width / 2f).toInt()}, ${(height / 2f).toInt()}), 圆半径${((height - 100f) / 2).toInt()}", 100f, 50f, textPaint)
        canvas?.drawText("辐射半径(${((height - 100f) / 4f).toInt()})", 100f, 100f, textPaint)


        val shader = RadialGradient(width / 2f, height / 2f, (height - 100) / 4f, Color.parseColor("#e91e63"), Color.parseColor("#2196f3"), tileMode)
        paint.shader = shader
        canvas?.drawCircle(width / 2f, height / 2f, (height - 100f) / 2, paint)
    }
}