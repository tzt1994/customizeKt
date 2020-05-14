package com.tzt.studykt.customView.paint.widget.effect.maskfilter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R


/**
 * Description: 模糊效果的MaskFilter
 * @author tangzhentao
 * @since 2020/5/6
 */
class BlurMaskFilterView: View{

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var style = BlurMaskFilter.Blur.NORMAL

    constructor(context: Context, style: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL): this(context, null) {
        this.style = style
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val size = width / 3f
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.what_the_fuck)
        paint.maskFilter = null
        canvas?.drawBitmap(bitmap, null, RectF(width / 4f - size / 2, height / 2f - size / 2,width / 4f +  size / 2, height / 2f + size / 2), paint)
        paint.maskFilter = BlurMaskFilter(50f, style)
        canvas?.drawBitmap(bitmap, null, RectF(width / 4f * 3 - size / 2, height / 2f - size / 2,width / 4f * 3 +  size / 2, height / 2f + size / 2), paint)
    }
}