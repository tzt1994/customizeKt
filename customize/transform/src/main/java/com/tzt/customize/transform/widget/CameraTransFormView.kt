package com.tzt.customize.transform.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.transform.R
import kotlin.math.min


/**
 * Description: camera三维变换
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class CameraTransFormView: View{
    companion object {
        const val TRANSLATE = 1
        const val ROTATE = 2
        const val LOCATION = 4
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
        val camera = Camera()
        val size = min(width / 2f, height * 1f) / 4 * 3
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.maps)
        val srcRectf = RectF(width / 4f - size / 2, height / 2f - size / 2f, width / 4f +  size / 2, height / 2f + size / 2f)
        val dstRectf = RectF(width / 4f * 3 - size / 2, height / 2f - size / 2f, width / 4f * 3 +  size / 2, height / 2f + size / 2f)
        when(type) {
            TRANSLATE -> {
                // 平移
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                camera.translate(100f, 0f, 10f)
                camera.applyToCanvas(canvas)
                camera.restore()
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            ROTATE -> {
                // 旋转
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                canvas?.translate(width / 4f * 3, height / 2f)
                camera.rotate(60f, 0f, 0f)
                camera.applyToCanvas(canvas)
                camera.restore()
                canvas?.translate(-width / 4f * 3, -height / 2f)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
            LOCATION -> {
                // 虚拟相机的位置
                canvas?.drawBitmap(bitmap, null, srcRectf, null)

                canvas?.save()
                canvas?.translate(width / 4f * 3, height / 2f)
                camera.rotate(60f, 0f, 0f)
                camera.setLocation(0f, 0f, -8f)
                camera.applyToCanvas(canvas)
                camera.restore()
                canvas?.translate(-width / 4f * 3, -height / 2f)
                canvas?.drawBitmap(bitmap, null, dstRectf, null)
                canvas?.restore()
            }
        }
    }
}