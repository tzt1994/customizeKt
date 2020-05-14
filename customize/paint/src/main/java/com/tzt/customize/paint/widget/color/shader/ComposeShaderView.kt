package com.tzt.customize.paint.widget.color.shader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R
import kotlin.math.max

/**
 * Description:混合着色器
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ComposeShaderView: View{

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 30f
        color = Color.parseColor("#666666")
        textAlign = Paint.Align.CENTER
    }

    private var mode = PorterDuff.Mode.SRC_OVER

    var debug = true

    constructor(context: Context, porterDuffMode: PorterDuff.Mode, debug: Boolean): this(context, null) {
        this.mode = porterDuffMode
        this.debug = debug
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 第一个头像shader
        val bitmapA = BitmapFactory.decodeResource(resources, R.mipmap.batman)
        val shaderA = BitmapShader(bitmapA, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        // 第二个logo shader
        val bitmapB = BitmapFactory.decodeResource(resources, R.mipmap.batman_logo)
        val shaderB = BitmapShader(bitmapB, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val size = if (debug) 200f else width * 1f
        val scaleA = max(size / bitmapA.width, size / bitmapA.height)
        val matrixA = Matrix()
        matrixA.setScale(scaleA, scaleA)
        shaderA.setLocalMatrix(matrixA)
        val scaleB = max(size / bitmapB.width, size / bitmapB.height)
        val matrixB = Matrix()
        matrixB.setScale(scaleB, scaleB)
        shaderB.setLocalMatrix(matrixB)
        val composeShader = ComposeShader(shaderA, shaderB, mode)
        val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = composeShader
        }
        if (debug) {
            canvas?.drawColor(Color.parseColor("#f1f1f1"))
            canvas?.drawText("PorterDuff.Mode.SRC_OVER", width / 2f, 45f, textPaint)
            val rectf1 = RectF(30f, 80f, 230f, 280f)
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
            canvas?.drawCircle(130f, 180f, 100f, paint)
            canvas?.drawText("图形范围", 130f, 400f, textPaint)
            canvas?.drawText("Canvas.drawCircle()", 130f, 450f, textPaint)
            canvas?.drawText("+", 305f, 180f, textPaint)

            val rectf2 = RectF(330f, 80f, 530f, 280f)
            canvas?.drawBitmap(bitmapA, null, rectf2, null)
            canvas?.drawText("BitmapShader", 430f, 400f, textPaint)
            canvas?.drawText("(dst shader)", 430f, 450f, textPaint)
            canvas?.drawText("+", 555f, 180f, textPaint)

            val rectf3 = RectF(580f, 80f, 780f, 280f)
            paint.apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            canvas?.drawRect(rectf3, paint)
            canvas?.drawBitmap(bitmapB, null, rectf3, null)
            canvas?.drawText("BitmapShader", 680f, 400f, textPaint)
            canvas?.drawText("(src shader)", 680f, 450f, textPaint)
            canvas?.drawText("=", 805f, 180f, textPaint)

            val rectf4 = RectF(830f, 80f, 1030f, 280f)
            paint.apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            canvas?.drawRect(rectf4, paint)

            // 画布平移保证裁剪圆形图片正常
            canvas?.save()
            canvas?.translate(830f, 80f)
            canvas?.drawCircle(100f, 100f, 100f, shaderPaint)
            canvas?.restore()
            canvas?.drawText("绘制效果", 930f, 400f, textPaint)
        } else {
            canvas?.drawRect(0f, 0f, width * 1f, height * 1f, shaderPaint)
        }
    }
}