package com.tzt.customize.paint.widget.effect.patheffect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description: 路径虚线轮廓效果
 * @author tangzhentao
 * @since 2020/5/6
 */
class PathDashPathEffectView: View{

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val textpaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 32f
        textAlign = Paint.Align.CENTER
    }

    private var shape = Path().apply {
        lineTo(0f, 0f)
        lineTo(-20f, -20f)
        lineTo(-40f, 0f)
        close()
    }
    private var advance = 40f
    private var phase = 0f

    constructor(
        context: Context,
        shape: Path = Path().apply {
            lineTo(0f, 0f)
            lineTo(20f, -20f)
            lineTo(40f, 0f)
            close()
        },
        advance: Float = 40f,
        phase: Float = 0f): this(context, null) {
        this.shape = shape
        this.phase = phase
        this.advance = advance
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val sWidth = width / 2f
        val sHeight = height / 2f

        paint.pathEffect = null
        canvas?.drawRoundRect(RectF(50f, 50f, sWidth - 50f, sHeight - 50f), 20f, 20f, paint)
        canvas?.drawText("原图形", sWidth / 2, sHeight, textpaint)

        paint.pathEffect = PathDashPathEffect(shape, advance, phase, PathDashPathEffect.Style.TRANSLATE)
        canvas?.drawRoundRect(RectF(sWidth + 50f, 50f, width - 50f, sHeight - 50f), 20f, 20f, paint)
        canvas?.drawText("TRANSLATE", sWidth / 2 * 3, sHeight, textpaint)

        paint.pathEffect = PathDashPathEffect(shape, advance, phase, PathDashPathEffect.Style.ROTATE)
        canvas?.drawRoundRect(RectF(80f, sHeight + 50f, sWidth - 50f, height - 50f), 20f, 20f, paint)
        canvas?.drawText("ROTATE", sWidth / 2, height - 10f, textpaint)

        paint.pathEffect = PathDashPathEffect(shape, advance, phase, PathDashPathEffect.Style.MORPH)
        canvas?.drawRoundRect(RectF(sWidth + 50f, sHeight + 50f, width - 50f, height - 50f), 20f, 20f, paint)
        canvas?.drawText("MORPH", sWidth / 2 * 3, height - 10f, textpaint)
    }
}