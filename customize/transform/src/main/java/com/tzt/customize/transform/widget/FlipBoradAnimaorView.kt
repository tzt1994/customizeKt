package com.tzt.customize.transform.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.transform.R


/**
 * Description: camera三维变换
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class FlipBoradAnimaorView: View{

    // Y右半边的翻转角度
    var degreeRightY = 0f
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 画布旋转角度
    var degreeCanvas = 0f
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 画布旋转结束后的Y左半边翻转角度
    var degreeLeftY = 0f
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.maps)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val camera = Camera()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val bpWidth = bitmap.width / 2f
        val bpHeight = bitmap.height / 2f
        val centerX = width / 2f
        val centerY = height / 2f
        canvas?.translate( centerX, centerY)

        // 右边半部分
        canvas?.save()
        canvas?.rotate(-degreeCanvas)
        camera.save()
        camera.setLocation(0f, 0f, -resources.displayMetrics.density * 6)
        camera.rotateY(degreeRightY)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(RectF(0f, -centerY, centerX, centerY))
        canvas?.rotate(degreeCanvas)
        canvas?.drawBitmap(bitmap, -bpWidth, -bpHeight, paint)
        camera.restore()
        canvas?.restore()


        // 左边半部分
        canvas?.save()
        canvas?.rotate(-degreeCanvas)
        camera.save()
        camera.setLocation(0f, 0f, -resources.displayMetrics.density * 6)
        camera.rotateY(degreeLeftY)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(RectF(-centerX, -centerY, 0f, centerY))
        canvas?.rotate(degreeCanvas)
        canvas?.drawBitmap(bitmap, -bpWidth, -bpHeight, paint)
        camera.restore()
        canvas?.restore()
    }

    fun reset() {
        degreeRightY = 0f
        degreeLeftY = 0f
        degreeCanvas = 0f

        postInvalidate()
    }
}