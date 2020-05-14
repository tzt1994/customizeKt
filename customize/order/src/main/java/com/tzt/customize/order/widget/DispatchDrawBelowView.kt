package com.tzt.customize.order.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.tzt.customize.order.R


/**
 * Description: dispatchDraw() 下面
 * @author tangzhentao
 * @since 2020/5/6
 */
class DispatchDrawBelowView: LinearLayout {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#80FF1493")
        style = Paint.Style.FILL
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        orientation = VERTICAL
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.batman)
        val params1 = LayoutParams(LayoutParams.MATCH_PARENT, 0)
        params1.weight = 1f
        addView(imageView, params1)
        val buffer = Button(context)
        buffer.text = "Batman"
        val params2 = LayoutParams(LayoutParams.MATCH_PARENT, -2)
        addView(buffer, params2)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawCircle(width / 4f, height / 12f * 5, 30f, paint)
        canvas?.drawCircle(width / 4f * 3, height / 4f * 1, 50f, paint)
        canvas?.drawCircle(width / 8f * 3, height / 12f * 7, 60f, paint)
        canvas?.drawCircle(width / 8f * 5, height / 4f * 3, 80f, paint)
    }
}