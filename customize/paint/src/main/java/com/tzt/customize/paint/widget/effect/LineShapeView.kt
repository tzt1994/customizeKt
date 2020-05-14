package com.tzt.customize.paint.widget.effect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.common.basedepency.angleByMiter
import com.tzt.common.basedepency.angleByPoint


/**
 * Description:线条形状
 * @author tangzhentao
 * @since 2020/5/6
 */
class LineShapeView: View{
    companion object {
        const val STROKE_WIDTH = 1
        const val STROKE_CAP = 2
        const val STROKE_JOIN = 3
        const val STROKE_MITER = 4
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 36f
        textAlign = Paint.Align.CENTER
    }

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mLineType =
        STROKE_WIDTH
    private var mStrokeCap = Paint.Cap.BUTT
    private var mStrokeMiter = 4f


    constructor(
        context: Context,
        lineType: Int = STROKE_WIDTH,
        strokeCap: Paint.Cap = Paint.Cap.BUTT,
        strokeMiter: Float = 4f
    ): this(context, null) {
        this.mLineType = lineType
        this.mStrokeCap = strokeCap
        this.mStrokeMiter = strokeMiter
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when(mLineType) {
            STROKE_WIDTH -> {
                // 线宽
                val size = width / 3
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 1f
                }
                canvas?.drawText("1", size / 2f, 75f, textPaint)
                canvas?.drawCircle(size / 2f, height / 2f, height / 2f - 150f, paint)
                paint.strokeWidth = 5f
                canvas?.drawText("5", size / 2f * 3, 75f, textPaint)
                canvas?.drawCircle(size / 2f * 3, height / 2f, height / 2f - 150f, paint)
                paint.strokeWidth = 40f
                canvas?.drawText("40", size / 2f * 5, 75f, textPaint)
                canvas?.drawCircle(size / 2f * 5, height / 2f, height / 2f - 150f, paint)
            }
            STROKE_CAP -> {
                // 线头
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 40f
                    strokeCap = mStrokeCap
                }
                canvas?.drawLine(width / 4f, height / 2f, width / 4f * 3, height / 2f, paint)
                val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.RED
                    strokeWidth = 1f
                    pathEffect = DashPathEffect(floatArrayOf(15f, 8f), 10f)
                }
                canvas?.drawLine(width / 4f, 200f, width / 4f, height - 200f, dashPaint)
                canvas?.drawLine(width / 4f * 3, 200f, width / 4f * 3, height - 200f, dashPaint)
            }
            STROKE_JOIN -> {
                // 拐角的形状
                val size = width / 3
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 40f
                    strokeJoin = Paint.Join.MITER
                }
                val path = Path()
                // 尖角
                path.reset()
                path.moveTo(20f, 100f)
                path.lineTo(size - 80f, 100f)
                path.lineTo(100f, height - 300f)
                canvas?.drawPath(path, paint)

                // 平角
                paint.strokeJoin = Paint.Join.BEVEL
                path.reset()
                path.moveTo(size + 20f, 100f)
                path.lineTo(size * 2 - 80f, 100f)
                path.lineTo(size + 100f, height - 300f)
                canvas?.drawPath(path, paint)

                // 圆角
                paint.strokeJoin = Paint.Join.ROUND
                path.reset()
                path.moveTo(size * 2 + 20f, 100f)
                path.lineTo(size * 3 - 80f, 100f)
                path.lineTo(size * 2 + 100f, height - 300f)
                canvas?.drawPath(path, paint)

                textPaint.color = Color.parseColor("#006400")
                canvas?.drawText("MITER(尖角)", size / 2f,height - 150f, textPaint)
                canvas?.drawText("BEVEL(平角)", size / 2f * 3,height - 150f, textPaint)
                canvas?.drawText("ROUND(圆角)", size / 2f * 5,height - 150f, textPaint)
            }
            STROKE_MITER -> {
                // Miter型拐角的延长线的最大值
                val size = width / 2
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 40f
                    strokeJoin = Paint.Join.MITER
                    strokeMiter = mStrokeMiter
                }

                val path = Path()
                // 大于的角度
                path.reset()
                path.moveTo(100f, 100f)
                path.lineTo(size - 100f, 100f)
                path.lineTo(100f, height - 300f)
                canvas?.drawPath(path, paint)

                // 大于的角度
                path.reset()
                path.moveTo(size + 100f, 100f)
                path.lineTo(size * 2 - 100f, 100f)
                path.lineTo(size + 100f, 200f)
                canvas?.drawPath(path, paint)

                textPaint.color = Color.parseColor("#006400")
                canvas?.drawText("miter = $mStrokeMiter   最小角度值 = ${angleByMiter(mStrokeMiter)}", width / 2f, 50f, textPaint)
                canvas?.drawText("尖角度数${angleByPoint(height - 400.0,size - 200.0)}°", size / 2f,height - 100f, textPaint)
                canvas?.drawText("尖角度数${angleByPoint(100.0, size - 200.0)}°", size / 2f * 3,height - 100f, textPaint)
            }
            else -> {}
        }
    }
}