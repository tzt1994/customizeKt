package com.tzt.customize.action.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.tzt.common.basedepency.dpToPx


/**
 * Description: 刮刮卡
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ScrapingCardView: AppCompatImageView{
    private val path = Path()

    private lateinit var mCoatingLayerBitmap: Bitmap

    private var isInit = false

    private var showAll = false

    private lateinit var mCanvas: Canvas

    private val xfermodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#A9A9A9")
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(10)
        strokeJoin = Paint.Join.ROUND
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (showAll) return

        if (!isInit) {
            mCoatingLayerBitmap = createCoatingLayer(width, height)
            mCanvas = Canvas(mCoatingLayerBitmap)

            isInit = true
        }

        val layer = canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), xfermodePaint, Canvas.ALL_SAVE_FLAG)
        canvas?.drawBitmap(mCoatingLayerBitmap, 0f, 0f, xfermodePaint)
        xfermodePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas?.drawPath(path, xfermodePaint)
        mCanvas.drawPath(path, xfermodePaint)
        xfermodePaint.xfermode = null
        canvas?.restoreToCount(layer?: 0)
    }

    private var preX = 0f
    private var preY = 0f
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                preX = event.x
                preY = event.y
                path.moveTo(preX, preY)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (preX + event.x) / 2
                val endY = (preY + event.y) / 2
                path.quadTo(preX, preY, endX, endY)
                preX = endX
                preY = endY
            }
            MotionEvent.ACTION_UP -> {
                // 滑动结束
                post(calculatePixelsRunnable)
            }
        }

        postInvalidate()
        return true
    }

    /**
     * 常见灰色遮盖层
     * @param w 宽
     * @param h 高
     */
    private fun createCoatingLayer(w: Int, h: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#A9A9A9")
        }
        canvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), paint)
        return bitmap
    }

    private val calculatePixelsRunnable = Runnable {
        val totalPixel = width * height * 1f
        val pixel = IntArray(width * height)
        mCoatingLayerBitmap.getPixels(pixel, 0, width, 0, 0, width, height)

        var cleanPixel = 0
        for (col in 0 until height) {
            for (row in 0 until width) {
                if (pixel[col * width + row] == 0) {
                    cleanPixel++
                }
            }
        }

        val result = cleanPixel / totalPixel

        if (result >= 0.5f) {
            showAll = true
            postInvalidate()
        }
    }

    fun recycle() {
        mCoatingLayerBitmap.recycle()
    }
}