package com.tzt.customize.paint.widget.color.shader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R
import kotlin.math.max

/**
 * Description:图片着色器
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class BitmapShaderView: View{

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 36f
        color = Color.parseColor("#666666")
        textAlign = Paint.Align.CENTER
    }

    private var tileMode = Shader.TileMode.CLAMP

    private var debug = true

    constructor(context: Context, tile: Shader.TileMode, debug: Boolean): this(context, null) {
        this.tileMode = tile
        this.debug = debug
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.batman)
        val bitmapShader = BitmapShader(bitmap, tileMode, tileMode)
        if (debug) {
            canvas?.drawColor(Color.parseColor("#f1f1f1"))
            val rectf1 = RectF(50f, 50f, 350f, 350f)
            paint.apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            canvas?.drawRect(rectf1, paint)
            paint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 10f)
            paint.apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 3f
            }
            canvas?.drawRoundRect(rectf1, 150f, 150f, paint)
            canvas?.drawText("图形范围", 200f, 500f, textPaint)
            canvas?.drawText("Canvas.drawCircle()", 200f, 550f, textPaint)
            canvas?.drawText("+", 375f, 210f, textPaint)

            val rectf2 = RectF(400f, 50f, 700f, 350f)
            canvas?.drawBitmap(bitmap, null, rectf2, null)
            canvas?.drawText("Bitmap", 550f, 500f, textPaint)
            canvas?.drawText("Paint.setShader()", 550f, 550f, textPaint)
            canvas?.drawText("=", 725f, 210f, textPaint)


            val rectf3 = RectF(750f, 50f, 1050f, 350f)
            paint.apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            canvas?.drawRect(rectf3, paint)
            val scale = max(300f / bitmap.width, 300f / bitmap.height)
            val matrix = Matrix()
            matrix.setScale(scale, scale)
            bitmapShader.setLocalMatrix(matrix)
            val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = bitmapShader
            }
            // 画布平移保证裁剪圆形图片正常
            canvas?.save()
            canvas?.translate(750f, 50f)
            canvas?.drawCircle(150f, 150f, 150f, shaderPaint)
            canvas?.restore()
            canvas?.drawText("绘制效果", 900f, 525f, textPaint)
        } else {
            paint.shader = bitmapShader
            val rectf = RectF(0f, 0f, width * 1f, height * 1f)
            canvas?.drawRect(rectf, paint)
        }
    }
}