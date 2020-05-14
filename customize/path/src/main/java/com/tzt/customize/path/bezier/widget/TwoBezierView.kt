package com.tzt.customize.path.bezier.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class TwoBezierView: CoordinateView {
    private lateinit var pointPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var linePaint: Paint
    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    private lateinit var valueAnimator: ValueAnimator
    private lateinit var pointA: PointF
    private lateinit var pointB: PointF
    private lateinit var pointC: PointF

    private var proportion = 0.0f

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    fun initAll() {
        pointA = PointF(-450f, 80f)
        pointB = PointF(0f, -400f)
        pointC = PointF(450f, 80f)

        pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20f
            style = Paint.Style.STROKE
            color = Color.BLUE
        }

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 36f
            color = Color.GRAY
            textAlign = Paint.Align.LEFT
        }

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 8f
            color = Color.BLUE
        }

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            repeatCount = -1
            duration = 10000
            addUpdateListener {
                proportion = animatedValue as Float
                postInvalidate()
            }
        }

        startAnimator()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        halfH = height / 2f
        halfW = width / 2f
        // 画布原点平移
        canvas?.translate(halfW, halfH)


        pointPaint.color = Color.BLUE
        canvas?.drawPoint(pointA.x, pointA.y, pointPaint)
        canvas?.drawPoint(pointB.x, pointB.y, pointPaint)
        canvas?.drawPoint(pointC.x, pointC.y, pointPaint)
        linePaint.color = Color.BLUE
        canvas?.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, linePaint)
        canvas?.drawLine(pointB.x, pointB.y, pointC.x, pointC.y, linePaint)
        linePaint.color= Color.RED
        val path = Path()
        path.moveTo(pointA.x, pointA.y)
        path.quadTo(pointB.x, pointB.y, pointC.x, pointC.y)
        canvas?.drawPath(path, linePaint)

        // 画进度和点文字
        textPaint.color = Color.GRAY
        canvas?.drawText("u = $proportion", -halfW  / 5 * 4, halfH / 5 * 4, textPaint)
        textPaint.color = Color.RED
        canvas?.drawText("A", pointA.x, pointA.y + 50f, textPaint)
        canvas?.drawText("B", pointB.x - 50f, pointB.y, textPaint)
        canvas?.drawText("C", pointC.x, pointC.y + 50f, textPaint)

        if (proportion < 1f) {
            val pointD = PointF().apply {
                x = pointA.x + (pointB.x - pointA.x) * proportion
                y = pointA.y + (pointB.y - pointA.y) * proportion
            }
            val pointE = PointF().apply {
                x = pointB.x + (pointC.x - pointB.x) * proportion
                y = pointB.y + (pointC.y - pointB.y) * proportion
            }
            val pointF = PointF().apply {
                x = pointD.x + (pointE.x - pointD.x) * proportion
                y = pointD.y + (pointE.y - pointD.y) * proportion
            }

            // 画点名称
            canvas?.drawText("D", pointD.x, pointD.y - 50f, textPaint)
            canvas?.drawText("E", pointE.x, pointE.y - 50f, textPaint)
            canvas?.drawText("F", pointF.x, pointF.y +  50f, textPaint)

            linePaint.color = Color.YELLOW
            canvas?.drawLine(pointD.x, pointD.y, pointE.x, pointE.y, linePaint)
            pointPaint.color = Color.YELLOW
            canvas?.drawPoint(pointD.x, pointD.y, pointPaint)
            canvas?.drawPoint(pointE.x, pointE.y, pointPaint)
            pointPaint.color = Color.RED
            canvas?.drawPoint(pointF.x, pointF.y, pointPaint)
        }
    }

    /**
     * 启动动画
     */
    fun startAnimator() {
        valueAnimator.start()
    }
}