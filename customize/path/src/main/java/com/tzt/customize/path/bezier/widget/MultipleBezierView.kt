package com.tzt.customize.path.bezier.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.tzt.customize.path.bezier.util.BezierUtils


/**
 * Description: 多阶阶贝塞尔曲线
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class MultipleBezierView:
    CoordinateView {

    private lateinit var pointPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var linePaint: Paint
    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    private lateinit var valueAnimator: ValueAnimator
    private val controllPointList = ArrayList<PointF>()

    private var proportion = 0.0f

    private var bezierPath = Path()
    private lateinit var controllLinePath: Path

    private val colors = listOf(Color.YELLOW, Color.GREEN, Color.parseColor("#4B0082"), Color.parseColor("#800000"), Color.parseColor("#FFC0CB"), Color.parseColor("#FFA500"))

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    fun initAll() {
        controllPointList.apply {
            add(PointF(-300f, 580f))
            add(PointF(-480f, 100f))
            add(PointF(-430f, -400f))
            add(PointF(-150f, -200f))
            add(PointF(0f, -400f))
            add(PointF(400f, -400f))
//            add(PointF(300f, 300f))
            add(PointF(80f, 590f))
        }

        prepareBezierPath()
        controllLinePath = Path().apply {
            for (point in controllPointList) {
                if (controllPointList.indexOf(point) == 0) {
                    moveTo(point.x, point.y)
                } else {
                    lineTo(point.x, point.y)
                }
            }
        }

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
            strokeWidth = 6f
            color = Color.BLUE
            strokeJoin = Paint.Join.ROUND
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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        halfH = height / 2f
        halfW = width / 2f
        // 画布原点平移
        canvas?.translate(halfW, halfH)


        // 绘制静态控制线
        linePaint.color = Color.BLUE
        canvas?.drawPath(controllLinePath, linePaint)

        // 绘制静态点
        pointPaint.color = Color.BLUE
        for (point in controllPointList) {
            canvas?.drawPoint(point.x, point.y, pointPaint)
        }

        // 绘制贝塞尔曲线
        linePaint.color= Color.RED
        canvas?.drawPath(bezierPath, linePaint)

        // 画进度
        textPaint.color = Color.GRAY
        canvas?.drawText("u = $proportion", -halfW  / 5 * 4, halfH / 5 * 4, textPaint)

        if (proportion < 1f) {
            val allLowControllPoints = BezierUtils.calculateAllListPoint(proportion, controllPointList)
            var pBezier: PointF? = null
            for (points in allLowControllPoints) {
                val index = allLowControllPoints.indexOf(points)
                linePaint.color = colors[index % 6]
                pointPaint.color = colors[index % 6]
                if (points.size == 2) {
                    // 一阶直线
                    val p1 = points[0]
                    val p2 = points[1]
                    pBezier = PointF((1 - proportion) * p1.x + proportion * p2.x, (1 - proportion) * p1.y + proportion * p2.y)
                    canvas?.drawPoint(p1.x, p1.y, pointPaint)
                    canvas?.drawPoint(p2.x, p2.y, pointPaint)
                    canvas?.drawLine(p1.x, p1.y, p2.x, p2.y, linePaint)
                } else {
                    var p1 = points[0]
                    canvas?.drawPoint(p1.x, p1.y, pointPaint)
                    for (p2 in points) {
                        canvas?.drawPoint(p2.x, p2.y, pointPaint)
                        canvas?.drawLine(p1.x, p1.y, p2.x, p2.y, linePaint)
                        p1 = p2
                    }
                }
            }

            pBezier?.let {
                pointPaint.color = Color.RED
                canvas?.drawPoint(it.x, it.y, pointPaint)
            }
        }


    }

    /**
     * 将计算好的 贝塞尔曲线的点 组装成路径
     */
    private fun prepareBezierPath() {
        bezierPath.reset()
        val pointList = BezierUtils.buildBezier(controllPointList, 120)
        for (point in pointList) {
            if (pointList.indexOf(point) == 0) {
                bezierPath.moveTo(point.x, point.y)
            } else {
                bezierPath.lineTo(point.x, point.y)
            }
        }
    }

    /**
     * 启动动画
     */
    private fun startAnimator() {
        if (!valueAnimator.isStarted) valueAnimator.start()
    }
}