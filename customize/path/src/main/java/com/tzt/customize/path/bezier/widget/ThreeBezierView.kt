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
class ThreeBezierView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinateView(context, attrs, defStyleAttr) {
    private lateinit var pointPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var linePaint: Paint
    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    private lateinit var valueAnimator: ValueAnimator
    private lateinit var pointA: PointF
    private lateinit var pointB: PointF
    private lateinit var pointC: PointF
    private lateinit var pointD: PointF

    private var proportion = 0.0f

    init {
        initAll()
    }

    fun initAll() {
        pointA = PointF(-450f, 300f)
        pointB = PointF(-450f, -600f)
        pointC = PointF(450f, -600f)
        pointD = PointF(450f, 300f)

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
            strokeJoin = Paint.Join.MITER
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


        linePaint.color = Color.BLUE
        val pathb = Path()
        pathb.moveTo(pointA.x, pointA.y)
        pathb.lineTo(pointB.x, pointB.y)
        pathb.lineTo(pointC.x, pointC.y)
        pathb.lineTo(pointD.x, pointD.y)
        canvas?.drawPath(pathb, linePaint)
        pointPaint.color = Color.BLUE
        canvas?.drawPoint(pointA.x, pointA.y, pointPaint)
        canvas?.drawPoint(pointB.x, pointB.y, pointPaint)
        canvas?.drawPoint(pointC.x, pointC.y, pointPaint)
        canvas?.drawPoint(pointD.x, pointD.y, pointPaint)
        linePaint.color= Color.RED
        val path = Path()
        path.moveTo(pointA.x, pointA.y)
        path.cubicTo(pointB.x, pointB.y, pointC.x, pointC.y, pointD.x, pointD.y)
        canvas?.drawPath(path, linePaint)

        // 画进度和点文字
        textPaint.color = Color.GRAY
        canvas?.drawText("u = $proportion", -halfW  / 5 * 4, halfH / 5 * 4, textPaint)
        textPaint.color = Color.RED
        canvas?.drawText("A", pointA.x, pointA.y + 50f, textPaint)
        canvas?.drawText("B", pointB.x - 50f, pointB.y, textPaint)
        canvas?.drawText("C", pointC.x + 50f, pointC.y, textPaint)
        canvas?.drawText("D", pointD.x, pointD.y + 50f, textPaint)

        if (proportion < 1f) {
            val pointE = PointF().apply {
                x = pointA.x + (pointB.x - pointA.x) * proportion
                y = pointA.y + (pointB.y - pointA.y) * proportion
            }
            val pointF = PointF().apply {
                x = pointB.x + (pointC.x - pointB.x) * proportion
                y = pointB.y + (pointC.y - pointB.y) * proportion
            }
            val pointG = PointF().apply {
                x = pointC.x + (pointD.x - pointC.x) * proportion
                y = pointC.y + (pointD.y - pointC.y) * proportion
            }
            val pointH = PointF().apply {
                x = pointE.x + (pointF.x - pointE.x) * proportion
                y = pointE.y + (pointF.y - pointE.y) * proportion
            }
            val pointI = PointF().apply {
                x = pointF.x + (pointG.x - pointF.x) * proportion
                y = pointF.y + (pointG.y - pointF.y) * proportion
            }
            val pointJ = PointF().apply {
                x = pointH.x + (pointI.x - pointH.x) * proportion
                y = pointH.y + (pointI.y - pointH.y) * proportion
            }


            linePaint.color = Color.YELLOW
            canvas?.drawLine(pointE.x, pointE.y, pointF.x, pointF.y, linePaint)
            canvas?.drawLine(pointF.x, pointF.y, pointG.x, pointG.y, linePaint)
            linePaint.color = Color.GREEN
            canvas?.drawLine(pointH.x, pointH.y, pointI.x, pointI.y, linePaint)


            pointPaint.color = Color.YELLOW
            canvas?.drawPoint(pointE.x, pointE.y, pointPaint)
            canvas?.drawPoint(pointF.x, pointF.y, pointPaint)
            canvas?.drawPoint(pointG.x, pointG.y, pointPaint)
            pointPaint.color = Color.GREEN
            canvas?.drawPoint(pointH.x, pointH.y, pointPaint)
            canvas?.drawPoint(pointI.x, pointI.y, pointPaint)
            pointPaint.color = Color.RED
            canvas?.drawPoint(pointJ.x, pointJ.y, pointPaint)

            // 画点名称
            canvas?.drawText("E", pointE.x - 50f, pointE.y, textPaint)
            canvas?.drawText("F", pointF.x, pointF.y - 50f, textPaint)
            canvas?.drawText("G", pointG.x + 50f, pointG.y, textPaint)
            canvas?.drawText("H", pointH.x, pointH.y - 50f, textPaint)
            canvas?.drawText("I", pointI.x + 50f, pointI.y, textPaint)
            canvas?.drawText("J", pointJ.x, pointJ.y +  50f, textPaint)
        }
    }

    /**
     * 启动动画
     */
    fun startAnimator() {
        if (!valueAnimator.isStarted) valueAnimator.start()
    }
}