package com.tzt.customize.action.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.tzt.customize.action.R


/**
 * Description: 心跳
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class HeartBeatView: AppCompatImageView{
    private val path = Path()

    private var isInit = false;

    private var mAnimation: ValueAnimator? = null

    private var offsetX = 0f

    private val xfermodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
    }

    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.heart)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.WHITE)
        val srcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val mCanvas = Canvas(srcBitmap)
        mCanvas.drawColor(Color.RED, PorterDuff.Mode.CLEAR)
        mCanvas.drawRect(RectF(0f, 0f, width * (1 - offsetX), height.toFloat()), xfermodePaint)

        val layer = canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), xfermodePaint, Canvas.ALL_SAVE_FLAG)
        canvas?.drawBitmap(bitmap, null, RectF(0f, 0f, width.toFloat(), height.toFloat()), xfermodePaint)
        xfermodePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas?.drawBitmap(srcBitmap, 0f, 0f  , xfermodePaint)
        xfermodePaint.xfermode = null
        canvas?.restoreToCount(layer?: 0)
    }

    fun startAnimation() {
        if (mAnimation == null) {
            mAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 4500
                interpolator = LinearInterpolator()
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    offsetX = animatedValue as Float
                    Log.v("TAGAGGA", "筋斗云$offsetX")

                    postInvalidate()
                }
            }
        }

        mAnimation?.start()
    }

    fun stopAnimation() {
        mAnimation?.cancel()
        mAnimation = null
    }
}