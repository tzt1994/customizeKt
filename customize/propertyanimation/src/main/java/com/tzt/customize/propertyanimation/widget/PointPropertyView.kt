package com.tzt.customize.propertyanimation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Description: 位置属性自定义
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class PointPropertyView: View{
    var position = PointF(0f, 0f)
        set(value) {
            if (field.x != value.x || field.y != value.x) {
                field = value
                postInvalidate()
            }
        }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.v("TTTTTT", "进度值${position.x} + ${position.y}")
        canvas?.drawCircle(position.x * width, position.y * height + 50, 50f, paint)
    }
}