package com.tzt.customize.paint.widget.effect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R
import kotlin.math.min


/**
 * Description:色彩优化
 * @author tangzhentao
 * @since 2020/5/6
 */
class ColorOptimizationView: View{

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // true是dither(抖动)， false是FilterBitmap(双线性过滤绘制Bitmap)
    private var mType = true

    constructor(context: Context, ditherType: Boolean = true): this(context, null) {
        this.mType = ditherType
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val bitmap = BitmapFactory.decodeResource(resources, if (mType) R.mipmap.dither_icon else R.mipmap.batman)
        var srcRect: Rect? = null
        if (!mType) {
            srcRect = Rect(bitmap.width / 16 * 7, bitmap.height / 16 * 7, bitmap.width / 16 * 9, bitmap.height / 16 * 9)
        }


        val size = min(width / 2f, height * 1f) - 100
        if (mType) {
            paint.isDither = false
        } else {
            paint.isFilterBitmap = false
        }
        canvas?.drawBitmap(bitmap, srcRect, RectF(width / 4f - size / 2f, height / 2f - size / 2, width / 4f + size / 2, height / 2f + size / 2), paint)
        if (mType) {
            paint.isDither = true
        } else {
            paint.isFilterBitmap = true
        }
        canvas?.drawBitmap(bitmap, srcRect, RectF(width / 4f * 3 - size / 2f, height / 2f - size / 2, width / 4f * 3 + size / 2, height / 2f + size / 2), paint)
    }
}