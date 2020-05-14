package com.tzt.studykt.customView.paint.widget.color.shader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description:扫描渐变
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class SweepGradientView: View{

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val shader = SweepGradient(width / 2f, height / 2f, Color.parseColor("#e91e63"), Color.parseColor("#2196f3"))
        paint.shader = shader
        canvas?.drawCircle(width / 2f, height / 2f, (height - 100f) / 2, paint)
    }
}