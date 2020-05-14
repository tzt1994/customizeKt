package com.tzt.customize.path.bezier.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet


/**
 * Description:贝塞尔画圆
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class CircleBezierView:
    CoordinateView {
    private lateinit var pointPaint: Paint
    private lateinit var linePaint: Paint
    private lateinit var circlePaint: Paint

    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f
    private var mRadius = 400f

    // 比例
    private var proportion = 0.55f

    private val pointList = ArrayList<PointF>()

    constructor(context: Context): this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    fun initAll() {
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = Color.RED
        }

        pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.BLUE
        }

        calculateControlPoint()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        halfH = height / 2f
        halfW = width / 2f
        // 画布原点平移
        canvas?.translate(halfW, halfH)

        val path = Path()
        for (i in 0 until 4) {
            if (i == 0) {
                path.moveTo(pointList[i * 3].x, pointList[i * 3].y)
            }

            val endIndex: Int = if (i == 3) {
                0
            } else {
                i * 3 + 3
            }

            path.cubicTo(pointList[i * 3 + 1].x, pointList[i * 3 + 1].y, pointList[i * 3 + 2].x, pointList[i * 3 + 2].y, pointList[endIndex].x, pointList[endIndex].y)
        }
        canvas?.drawPath(path, linePaint)

        canvas?.drawCircle(0f, 0f, mRadius, circlePaint)
    }

    /**
     * 计算圆的控制点
     */
    private fun calculateControlPoint() {
        // 计算 中间控制点到端点的距离
        val controlWidth: Float = proportion * mRadius
        pointList.clear()

        // 右上
        pointList.add(PointF(0f, -mRadius))
        pointList.add(PointF(controlWidth, -mRadius))
        pointList.add(PointF(mRadius, -controlWidth))

        // 右下
        pointList.add(PointF(mRadius, 0f))
        pointList.add(PointF(mRadius, controlWidth))
        pointList.add(PointF(controlWidth, mRadius))

        // 左下
        pointList.add(PointF(0f, mRadius))
        pointList.add(PointF(-controlWidth, mRadius))
        pointList.add(PointF(-mRadius, controlWidth))
        // 左上
        pointList.add(PointF(-mRadius, 0f))
        pointList.add(PointF(-mRadius, -controlWidth))
        pointList.add(PointF(-controlWidth, -mRadius))
    }

    /**
     * 设置进度
     */
    fun setProportion(pp: Float) {
        proportion = when {
            pp < 0f -> 0f
            pp > 1f -> 1f
            else -> pp
        }

        calculateControlPoint()
        postInvalidate()
    }

    fun getProportion() = proportion
}