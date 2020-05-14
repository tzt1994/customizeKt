package com.tzt.customize.order.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 * Description: super.onDraw() 下面
 * @author tangzhentao
 * @since 2020/5/6
 */
class SuperOndrawBelowView: AppCompatImageView {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFD700")
        textSize = 36f
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText("自定义View: 继承ImageView", width / 8f, height / 8f, paint)
        canvas?.drawText("尺寸：${drawable.intrinsicWidth} x ${drawable.intrinsicHeight}", width / 8f, height / 4f, paint)
    }
}