package com.tzt.customize.order.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


/**
 * Description: draw() 下面
 * @author tangzhentao
 * @since 2020/5/6
 */
class DrawAboveView: AppCompatEditText {
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun draw(canvas: Canvas?) {
        canvas?.drawColor(Color.parseColor("#66BB6A"))
        super.draw(canvas)
    }
}