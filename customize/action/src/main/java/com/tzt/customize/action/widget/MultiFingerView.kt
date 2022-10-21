package com.tzt.customize.action.widget
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.RequiresApi


/**
 * Description: 多指触摸
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class MultiFingerView  @JvmOverloads constructor (
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attributeSet, defStyleAttr){
    private var isScale = false
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private lateinit var bgBitmap: Bitmap
    private lateinit var bgCanvas: Canvas

    private var centerX = 0f
    private var centerY = 0f

    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)


    private val mScaleListener = @RequiresApi(Build.VERSION_CODES.FROYO)
    object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            detector.focusX
            return super.onScaleBegin(detector)
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            Log.v("AAAA", "缩放 ${detector.scaleFactor}")
            isScale = true
            return super.onScale(detector)
        }
    }

    private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.v("AAAA", "移动 $distanceX -$distanceY")
            if (!isScale) {
                centerX -= distanceX
                centerY -= distanceY
                invalidate()
            }

            isScale = false
            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    private val mScaleGestureDetector = ScaleGestureDetector(context, mScaleListener)
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private val mGestureDetector = GestureDetector(context, mGestureListener)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerX = measuredWidth / 2f
        centerY = measuredHeight / 2f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bgCanvas = Canvas(bgBitmap)
        bgCanvas.drawColor(Color.BLACK)
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.v("TTTTT", "主要手指按下")
                isScale = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.v("TTTTT", "非主要手指按下")
            }
            MotionEvent.ACTION_MOVE -> {
                isScale = false
                Log.v("AAAA", "手指一动${event.pointerCount}")
            }
            MotionEvent.ACTION_POINTER_UP -> {
                Log.v("TTTTT", "非主要手指抬起")
            }
            MotionEvent.ACTION_UP -> {
                Log.v("TTTTT", "主要手指抬起")
            }
        }

        mScaleGestureDetector.onTouchEvent(event)
        mGestureDetector.onTouchEvent(event)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        canvas.drawBitmap(bgBitmap, 0f, 0f, null)
        paint.apply {
            xfermode = mXfermode
            color = Color.TRANSPARENT
        }
        canvas.drawCircle(centerX, centerY, 100f, paint)
        paint.xfermode = null
        canvas.restoreToCount(layer)
    }
}