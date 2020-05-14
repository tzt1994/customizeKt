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
class CanvasTransFormView: View{
    companion object {
        const val TRANSLATE = 1
        const val ROTATE = 2
        const val SCALE = 3
        const val SKEW = 4
    }

    private var type = TRANSLATE

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.LEFT
        textSize = 36f
        style = Paint.Style.FILL
        color = Color.BLACK
    }

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
        when(type) {
            TRANSLATE -> {
                // 平移
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                canvas?.translate(100f, 0f)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            ROTATE -> {
                // 旋转
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                canvas?.rotate(70f, width / 4f * 3, height / 2f)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            SCALE -> {
                // 缩放
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                canvas?.scale(0.5f, 0.3f, width / 4f * 3, height / 2f)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            SKEW -> {
                // 错切
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                canvas?.skew(0.3f, 0f)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
        }
    }
}