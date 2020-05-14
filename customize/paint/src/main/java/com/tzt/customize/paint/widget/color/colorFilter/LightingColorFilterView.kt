package com.tzt.customize.paint.widget.color.colorFilter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R

/**
 * Description:LightingColorFilter颜色过滤器
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class LightingColorFilterView: View{
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        colorFilter = LightingColorFilter(0xffffff, 0x113000)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#333333")
        textSize = 36f
        textAlign = Paint.Align.CENTER
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.batman)
        val size = width / 3f
        canvas?.drawBitmap(bitmap, null, RectF(50f, 50f, size + 50f, size + 50f), null)
        canvas?.drawText("原图", size / 2 + 50f, size + 150f, textPaint)
        canvas?.drawBitmap(bitmap, null, RectF(size + 150f, 50f, size * 2 + 150f, size + 50f), paint)
        canvas?.drawText("转化后", size / 2 * 3 + 150f, size + 150f, textPaint)
    }
}