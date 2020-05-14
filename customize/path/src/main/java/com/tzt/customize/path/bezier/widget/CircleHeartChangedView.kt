package com.tzt.customize.path.bezier.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.tzt.common.basedepency.dpToPx
import kotlin.math.pow
import kotlin.math.sin


/**
 * Description:圆变心
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class CircleHeartChangedView:
    CoordinateView {
    private lateinit var paint: Paint
    private lateinit var path: Path

    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    private lateinit var valueAnimator: ValueAnimator

    private val heartList = ArrayList<PointF>()
    private val circleList = ArrayList<PointF>()
    private val curList = ArrayList<PointF>()

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    fun initAll() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.FILL
        }

        path = Path()

        heartList.apply {
            add(PointF(0f, dpToPx(-38)))
            add(PointF(dpToPx(50), dpToPx(-103)))
            add(PointF(dpToPx(112), dpToPx(-61)))
            add(PointF(dpToPx(112), dpToPx(-12)))
            add(PointF(dpToPx(112), dpToPx(37)))
            add(PointF(dpToPx(51), dpToPx(90)))
            add(PointF(0f, dpToPx(129)))
            add(PointF(dpToPx(-51), dpToPx(90)))
            add(PointF(dpToPx(-112), dpToPx(37)))
            add(PointF(dpToPx(-112), dpToPx(-12)))
            add(PointF(dpToPx(-112), dpToPx(-61)))
            add(PointF(dpToPx(-50), dpToPx(-103)))
        }

        circleList.apply {
            add(PointF(0f, dpToPx(-89)))
            add(PointF(dpToPx(50), dpToPx(-89)))
            add(PointF(dpToPx(90), dpToPx(-49)))
            add(PointF(dpToPx(90), dpToPx(0)))
            add(PointF(dpToPx(90), dpToPx(50)))
            add(PointF(dpToPx(50), dpToPx(90)))
            add(PointF(0f, dpToPx(90)))
            add(PointF(dpToPx(-49), dpToPx(90)))
            add(PointF(dpToPx(-89), dpToPx(50)))
            add(PointF(dpToPx(-89), 0f))
            add(PointF(dpToPx(-89), dpToPx(-49)))
            add(PointF(dpToPx(-49), dpToPx(-89)))
        }

        curList.apply {
            add(PointF(0f, dpToPx(-89)))
            add(PointF(dpToPx(50), dpToPx(-89)))
            add(PointF(dpToPx(90), dpToPx(-49)))
            add(PointF(dpToPx(90), dpToPx(0)))
            add(PointF(dpToPx(90), dpToPx(50)))
            add(PointF(dpToPx(50), dpToPx(90)))
            add(PointF(0f, dpToPx(90)))
            add(PointF(dpToPx(-49), dpToPx(90)))
            add(PointF(dpToPx(-89), dpToPx(50)))
            add(PointF(dpToPx(-89), 0f))
            add(PointF(dpToPx(-89), dpToPx(-49)))
            add(PointF(dpToPx(-49), dpToPx(-89)))
        }

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000
            addUpdateListener {
                val x = animatedValue as Float
                val factor = 0.15f
                val value = 2.0.pow(-10.0 * x) * sin((x - factor / 4) * (2 * Math.PI) / factor) + 1

                for(i in 0 until curList.size) {
                    val startPointF = circleList[i]
                    val endPointF = heartList[i]

                    curList[i].x = (startPointF.x + (endPointF.x - startPointF.x) * value).toFloat()
                    curList[i].y = (startPointF.y + (endPointF.y - startPointF.y) * value).toFloat()
                }

                postInvalidate()
            }
        }

        startAnimator()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        halfH = height / 2f
        halfW = width / 2f
        // 画布原点平移
        canvas?.translate(halfW, halfH)

        path.reset()
        for(i in 0 until 4) {
            if (i == 0) {
                path.moveTo(curList[i * 3].x, curList[i * 3].y)
            } else {
                path.lineTo(curList[i * 3].x, curList[i * 3].y)
            }

            val endIndex = if (i == 3) {
                0
            }else {
                i * 3 + 3
            }

            path.cubicTo(curList[i * 3 + 1].x, curList[i * 3 + 1].y,
                curList[i * 3 + 2].x, curList[i * 3 + 2].y,
                curList[endIndex].x, curList[endIndex].y)
        }

        canvas?.drawPath(path, paint)
    }

    fun startAnimator() {
        (!valueAnimator.isRunning).let {
            valueAnimator.start()
        }
    }
}