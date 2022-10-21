package com.tzt.customize.action.widget
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


/**
 * Description: 橡皮
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class EraserView  @JvmOverloads constructor (
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attributeSet, defStyleAttr){
    var isPen = true

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        pathEffect = CornerPathEffect(5f)
    }

    private val eraserPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        pathEffect = PathDashPathEffect(
            Path().apply {
                moveTo(-20f, -20f)
                lineTo(20f, -20f)
                lineTo(20f, 20f)
                lineTo(-20f, 20f)
                lineTo(-20f, -20f)
            },
            5f, 0f, PathDashPathEffect.Style.TRANSLATE
        )
    }

    private val eraserAllPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = Color.GRAY
//        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    private val penPoints = ArrayList<PointF>()
    private var penPath = Path()

    private val eraserPoints = ArrayList<PointF>()
    private val eraserPath = Path()
    private val eraserAllPath = Path()
    private val intersectPath = Path()
    private val intersectAnPath = Path()
    private val pathMeasure = PathMeasure()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isPen) drawLine(event) else drawEraser(event)
        return true
    }

    private fun drawLine(event: MotionEvent) {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.v("TTTTT", "主要手指按下")
                penPoints.clear()
                penPoints.add(PointF(event.x, event.y))
                penPath.reset()
                penPath.moveTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                Log.v("TTTTT", "手指一动${event.pointerCount}")
                penPoints.add(PointF(event.x, event.y))
                penPath.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                Log.v("TTTTT", "主要手指抬起")
                penPath.lineTo(event.x, event.y)
                invalidate()
            }
        }
    }

    private fun drawEraser(event: MotionEvent) {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.v("TTTTT", "主要手指按下")
                eraserPoints.clear()
                eraserPoints.add(PointF(event.x, event.y))
                eraserPath.reset()
                eraserPath.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.v("TTTTT", "手指一动${event.pointerCount}")
                eraserPoints.add(PointF(event.x, event.y))
                eraserPath.lineTo(event.x, event.y)
                eraserAllPath.reset()
                eraserPaint.getFillPath(eraserPath, eraserAllPath)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                Log.v("TTTTT", "主要手指抬起")
                eraserPath.lineTo(event.x, event.y)
                eraserAllPath.reset()
                eraserPaint.getFillPath(eraserPath, eraserAllPath)
                calPath()
                invalidate()
            }
        }
    }

    private fun calPath() {
        intersectPath.reset()
        val result = intersectPath.op(eraserAllPath, penPath, Path.Op.INTERSECT)
        Log.v("分段数据", "是否有交集$result-- ${intersectPath.isEmpty}")
//        intersectAnPath.reset()
//        intersectAnPath.op(eraserAllPath, penPath, Path.Op.INTERSECT)
        pathMeasure.setPath(intersectPath, false)
        var i = 1
        while (pathMeasure.nextContour()) {
            Log.v("分段数据", "第${i}段-长度${pathMeasure.length}")
            i++
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        canvas.drawPath(penPath, paint)
        canvas.drawPath(eraserAllPath, eraserAllPaint)
        paint.color = Color.RED
        canvas.drawPath(intersectPath, paint)
//        paint.color = Color.BLUE
//        canvas.drawPath(intersectAnPath, paint)
        paint.color = Color.BLACK
        canvas.restoreToCount(layer)
    }
}