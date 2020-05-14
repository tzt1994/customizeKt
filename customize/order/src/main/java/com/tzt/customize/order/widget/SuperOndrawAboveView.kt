package com.tzt.customize.order.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * Description: super.onDraw()上面
 * @author tangzhentao
 * @since 2020/5/6
 */
class SuperOndrawAboveView: AppCompatTextView {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFD700")
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f , 0f, width * 1f,  height / 4f ,paint)
        canvas?.drawRect(0f,  height / 4f * 3, width * 1f,  height * 1f ,paint)
        super.onDraw(canvas)
    }
}