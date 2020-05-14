package com.tzt.customize.propertyanimation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min


/**
 * Description: 颜色属性自定义
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ColorPropertyView: View{
    var color: Int = Color.parseColor("#ffffffff")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val radius = (min(width, height) / 2).toFloat()
        paint.color = color
        canvas?.drawCircle(width / 2f, height / 2f, radius, paint)
    }
}