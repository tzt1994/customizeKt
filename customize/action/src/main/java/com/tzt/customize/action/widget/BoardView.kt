package com.tzt.customize.action.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


/**
 * Description:
 *
 * @author tangzhentao
 * @since 1:39 PM 3/22/21
 */

class BoardView @JvmOverloads constructor (
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
): SurfaceView(context, attributeSet, defStyleAttr), SurfaceHolder.Callback, Runnable {
    private lateinit var cacheCanvas: Canvas
    private lateinit var cacheBitmap: Bitmap

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 3f
    }

    private val points = ArrayList<PointF>()

    private var mIsDrawing = false

    private val path = Path()

    init {
        holder.addCallback(this)
        isFocusable = true
        keepScreenOn = true
        isFocusableInTouchMode = true
        setZOrderOnTop(true)
//        setZOrderMediaOverlay(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cacheBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        cacheCanvas = Canvas(cacheBitmap)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                points.add(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_MOVE -> {
                path.quadTo(points.last().x, points.last().y, (event.x + points.last().x) / 2, (event.y + points.last().y) / 2)
                points.add(PointF(event.x, event.y))
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                path.lineTo(event.x, event.y)
                points.add(PointF(event.x, event.y))
            }
        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mIsDrawing = true
        Thread(this).start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mIsDrawing = false
    }

    override fun run() {
        while (mIsDrawing) {
            val canvas = holder.lockCanvas()
            Log.v("TTTTT", "在绘制")
            canvas?.let {
                it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                it.drawPath(path, paint)
                holder.unlockCanvasAndPost(it)
            }
        }
    }
}