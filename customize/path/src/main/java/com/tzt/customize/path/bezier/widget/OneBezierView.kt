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
class OneBezierView: CoordinateView {
    private lateinit var pointPaint: Paint
    private lateinit var linePaint: Paint
    private lateinit var textPaint: Paint

    private lateinit var point1: PointF
    private lateinit var point2: PointF
    private var halfH: Float = 0.0f
    private var halfW: Float = 0.0f

    private var valueAnimator: ValueAnimator? = null

    // 比例
    private var proportion = 0.0f

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    fun initAll() {
        point1 = PointF(-450f, -550f)
        point2 = PointF(500f, 550f)

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 36f
            color = Color.GRAY
            textAlign = Paint.Align.LEFT
        }

        pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 10f
            color = Color.RED
        }

        startAnimator()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        halfH = height / 2f
        halfW = width / 2f
        // 画布原点平移
        canvas?.translate(halfW, halfH)
        canvas?.drawLine(point1.x, point1.y, point2.x, point2.y, linePaint)
        canvas?.drawCircle(point1.x, point1.y, 10f, pointPaint)
        canvas?.drawCircle(point2.x, point2.y, 10f, pointPaint)

        val ty = point2.y - point1.y
        val tx = point2.x - point1.x
        pointPaint.color = Color.BLUE
        canvas?.drawCircle(point1.x + tx * proportion, point1.y + ty * proportion, 10f, pointPaint)

        textPaint.textSize = 36f
        textPaint.textAlign = Paint.Align.LEFT
        canvas?.drawText("u = $proportion", -halfW / 5 * 4, halfH / 5 * 4, textPaint)
    }

    /**
     * 启动动画
     */
    private fun startAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0f ,1f)
        valueAnimator?.duration = 4000
        valueAnimator?.repeatCount = 0
        valueAnimator?.addUpdateListener {
            proportion = it.animatedValue as Float
            postInvalidate()
        }
        valueAnimator?.start()
    }

    /**
     * 停止动画
     */
    fun stopAnimator() {
        valueAnimator?.let {
            if (it.isRunning) {
                it.pause()
            }
        }
    }
}