package com.tzt.customize.paint.widget.color

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R

/**
 * Description:混合着色器原图和目标图
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ComposeView: View{
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 36f
        color = Color.parseColor("#333333")
        textAlign = Paint.Align.CENTER
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val bitmapDst = BitmapFactory.decodeResource(resources, R.mipmap.batman)
        val bitmapSrc = BitmapFactory.decodeResource(resources, R.mipmap.batman_logo)

        canvas?.drawBitmap(bitmapSrc, null, Rect(50, 100, height - 100, height -50), null)
        canvas?.drawText("源图像", (height - 150) / 2f + 50f, 55f, paint)
        canvas?.drawBitmap(bitmapDst, null, Rect(50 + width / 2, 100,  width / 2 + height - 100, height - 50), null)
        canvas?.drawText("目标图像", (height - 150) / 2f + 50 + width / 2, 55f, paint)
    }
}