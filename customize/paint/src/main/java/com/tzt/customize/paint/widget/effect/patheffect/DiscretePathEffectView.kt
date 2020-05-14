package com.tzt.studykt.customView.paint.widget.effect.patheffect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description: 线条偏离轮廓效果
 * @author tangzhentao
 * @since 2020/5/6
 */
class DiscretePathEffectView: View{

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private var segmentLength = 20f
    private var deviation = 5f

    constructor(context: Context, segment: Float, deviation: Float): this(context, null) {
        this.segmentLength = segment
        this.deviation = deviation
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.pathEffect = DiscretePathEffect(segmentLength, deviation)
        val path = Path()
        path.reset()
        path.moveTo(width / 12f, height / 4f)
        path.lineTo(width / 6f, height / 4f * 3)
        path.lineTo(width / 3f, height / 6f)
        path.lineTo(width / 12f * 7f, height / 2f)
        path.lineTo(width / 4f * 3f,height / 9f)
        path.lineTo(width / 12 * 11f,height / 3f * 2)
        canvas?.drawPath(path, paint)
    }
}