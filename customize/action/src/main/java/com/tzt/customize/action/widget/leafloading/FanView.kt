package com.tzt.customize.action.widget.leafloading

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.annotation.ColorInt
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.action.R


/**
 * Description: 风扇
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class FanView: View {
    // 进度
    var mProgres = 0
        set(value) {
            if (field != value) {
                field = value
                if (field == 0) {
                    startAnimator()
                }
            }
        }
    // 风扇背景色
    var mFanBgColor = Color.parseColor("#fed255")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 风扇边框颜色
    var mFanBorderColor = Color.parseColor("#ffffff")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 风扇边框宽度
    var mBorderWidth = dpToPx(3)
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 风扇百分百进度时的进度值文字颜色
    var mFanTextColor = Color.parseColor("#ffffff")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 风扇百分百进度时的进度值文字大小
    var mFanTextSize =  dpToPx(12)
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    // 风扇
    private var mFsBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.fengshan)

    // 风扇相关画笔
    private val mFanPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 百分百进度值文字画笔
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    // 当前旋转度数
    private var angle = 0
    // 旋转动画
    private lateinit var animator: ValueAnimator

    constructor(
        context: Context,
        progress: Int,
        @ColorInt bgColor: Int,
        @ColorInt borderColor: Int,
        @ColorInt textColor: Int,
        textSize: Float,
        borderWidth: Float
    ): this(context, null) {
        this.mProgres = progress
        this.mFanBgColor = bgColor
        this.mFanBorderColor = borderColor
        this.mFanTextColor = textColor
        this.mFanTextSize = textSize
        this.mBorderWidth = borderWidth
    }

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        if (mProgres == 0) {
            startAnimator()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(size, size)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 画边框
        mFanPaint.apply {
            color = mFanBorderColor
            strokeWidth = mBorderWidth
            style = Paint.Style.STROKE
        }
        canvas?.drawCircle(width / 2f, height/ 2f, (height - mBorderWidth) / 2, mFanPaint)
        // 画背景色
        mFanPaint.apply {
            color = mFanBgColor
            style = Paint.Style.FILL
        }
        canvas?.drawCircle(width / 2f, height/ 2f, height / 2 - mBorderWidth, mFanPaint)
        if (mProgres < 100) {
            // 旋转角度
            val space = height / 7f
            val fanRectF = RectF(space, space, width - space, height - space)
            canvas?.save()
            canvas?.rotate(-angle.toFloat(), width / 2f, height / 2f)
            canvas?.drawBitmap(mFsBitmap, null, fanRectF, null)
            canvas?.restore()
        } else {
            // 百分百进度值
            mTextPaint.apply {
                color = mFanTextColor
                textSize = mFanTextSize
            }
            val fontMetrics = mTextPaint.fontMetrics
            val baseLine = height / 2f + ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent)
            canvas?.drawText("100%", width / 2f, baseLine, mTextPaint)
            animator.cancel()
        }
    }

    fun startAnimator() {
        animator = ValueAnimator.ofInt(0, 360).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            pivotX = 0.5f
            pivotY = 0.5f
            duration = 1500
            interpolator = LinearInterpolator()

            addUpdateListener {
                angle = animatedValue as Int
                postInvalidate()
            }
        }

        animator.start()
    }
}