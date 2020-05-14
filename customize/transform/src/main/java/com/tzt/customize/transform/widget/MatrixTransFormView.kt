package com.tzt.customize.transform.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.transform.R
import kotlin.math.min


/**
 * Description: canvas几何变换
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class MatrixTransFormView: View{
    companion object {
        const val TRANSLATE = 1
        const val ROTATE = 2
        const val SCALE = 3
        const val SKEW = 4
        const val SINGLE_POINT = 5
        const val MULTI_POINTS = 6
    }

    private var type = TRANSLATE

    constructor(context: Context, type: Int = TRANSLATE): this(context, null) {
        this.type = type
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val size = min(width / 2f, height * 1f) / 4 * 3
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.maps)
        val srcRectf = RectF(width / 4f - size / 2, height / 2f - size / 2f, width / 4f +  size / 2, height / 2f + size / 2f)
        val dstRectf = RectF(width / 4f * 3 - size / 2, height / 2f - size / 2f, width / 4f * 3 +  size / 2, height / 2f + size / 2f)
        val matrix = Matrix()
        when(type) {
            TRANSLATE -> {
                // 平移
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                matrix.postTranslate(100f, 0f)
                canvas?.concat(matrix)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            ROTATE -> {
                // 旋转
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                matrix.postRotate(70f, width / 4f * 3, height / 2f)
                canvas?.concat(matrix)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            SCALE -> {
                // 缩放
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                matrix.postScale(0.5f, 0.3f, width / 4f * 3, height / 2f)
                canvas?.concat(matrix)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            SKEW -> {
                // 错切
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                matrix.postSkew(0.3f, 0f)
                canvas?.concat(matrix)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            SINGLE_POINT -> {
                // 单点平移
                val pointsSrc = floatArrayOf(dstRectf.left, dstRectf.top, dstRectf.right, dstRectf.top, dstRectf.left, dstRectf.bottom, dstRectf.right, dstRectf.bottom)
                val pointsDst = floatArrayOf(dstRectf.left + 100, dstRectf.top, dstRectf.right + 120, dstRectf.top - 90, dstRectf.left + 20, dstRectf.bottom + 30, dstRectf.right + 20, dstRectf.bottom + 60)
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 1)
                canvas?.concat(matrix)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            MULTI_POINTS -> {
                // 多点扭曲效果
                val pointsSrc = floatArrayOf(dstRectf.left, dstRectf.top, dstRectf.right, dstRectf.top, dstRectf.left, dstRectf.bottom, dstRectf.right, dstRectf.bottom)
                val pointsDst = floatArrayOf(dstRectf.left - 10, dstRectf.top + 50, dstRectf.right + 120, dstRectf.top - 90, dstRectf.left + 20, dstRectf.bottom + 30, dstRectf.right + 20, dstRectf.bottom + 60)
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4)
                canvas?.concat(matrix)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
        }
    }
}