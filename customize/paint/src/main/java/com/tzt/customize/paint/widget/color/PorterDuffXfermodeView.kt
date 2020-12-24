package com.tzt.customize.paint.widget.color

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R

/**
 * Description: PorterDuffXfermode
 *
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class PorterDuffXfermodeView: View{
    companion object {
        const val NONE_MODE_SRC = 1
        const val NONE_MODE_DST = 2
        const val MODE = 3
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var model =  PorterDuff.Mode.SRC
    private val bitmapDst = BitmapFactory.decodeResource(resources, R.mipmap.dst)
    private val bitmapSrc = BitmapFactory.decodeResource(resources, R.mipmap.src)

    private var noneMode = MODE

    constructor(context: Context, porterDuffModel: PorterDuff.Mode, noneMode: Int = MODE): this(context, null) {
        this.model = porterDuffModel
        this.noneMode = noneMode
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val matrix = Matrix()
        val scale = (width * 1f) / bitmapDst.width
        matrix.setScale(scale, scale)
        when(noneMode) {
            NONE_MODE_SRC -> canvas?.drawBitmap(bitmapSrc, matrix, paint)
            NONE_MODE_DST -> canvas?.drawBitmap(bitmapDst, matrix, paint)
            MODE -> {val saved = canvas?.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
                canvas?.drawBitmap(bitmapDst, matrix, paint)
                paint.xfermode = PorterDuffXfermode(model)
                canvas?.drawBitmap(bitmapSrc, matrix, paint)
                paint.xfermode = null
                canvas?.restoreToCount(saved?: 0)}
        }
    }
}