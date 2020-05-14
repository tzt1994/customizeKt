package com.tzt.customize.transform.widget

import android.animation.ValueAnimator
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
class RotateAnimaorView: View{
    private val valueAnimator = ValueAnimator.ofFloat(0f, 1f)

    private var degreeX = 0f

    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.maps)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        valueAnimator.apply {
            duration = 4000
            repeatCount = 0
            addUpdateListener {
                degreeX = (animatedValue as Float) * 360

                postInvalidate()
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val camera = Camera()
        val size = min(width / 2f, height * 1f) / 8 * 7
        val rectF = RectF((width - size) / 2f, (height - size) / 2f, (width + size) / 2f, (height + size) / 2f)

        // 旋转
        canvas?.save()
        canvas?.translate(width / 2f, height / 2f)
        camera.save()
        camera.setLocation(0f, 0f, -resources.displayMetrics.density * 6)
        camera.rotateX(degreeX)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas?.translate(-width / 2f, -height / 2f)
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//            textSize = 100f
//            textAlign = Paint.Align.CENTER
//            color = Color.BLACK
//        }
        canvas?.drawBitmap(bitmap, null, rectF, null)

//        val fontMetrics = paint.fontMetrics
//        val baseline = (fontMetrics.ascent - fontMetrics.descent) / 2f + fontMetrics.descent + height / 2f
//        canvas?.drawText(if (degreeX < 90f) "Hello TangZhenTao" else "第二行文字", width / 2f, baseline, paint)
        canvas?.restore()
    }

    fun startAnimatior() {
        valueAnimator.start()
    }
}