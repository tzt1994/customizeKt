package com.tzt.customize.paint.widget.effect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description: 获取实际path
 * @author tangzhentao
 * @since 2020/5/6
 */
class GetPathView: View{

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color= Color.BLACK
        textAlign= Paint.Align.CENTER
    }

    // 默认path路径获取
    private var pathable = true

    // 是否展示真实路径 默认false
    private var real = false

    private var mPathEffect: PathEffect? = null

    constructor(
        context: Context,
        path: Boolean = true,
        real: Boolean = false,
        pathEffect: PathEffect? = null
    ): this(context, null) {
        this.pathable = path
        this.real = real
        this.mPathEffect = pathEffect
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (pathable) {
            // path
            paint.apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 20f
                pathEffect = mPathEffect
            }
            val path = Path()
            path.reset()
            path.moveTo(width / 12f, height / 4f)
            path.lineTo(width / 6f, height / 4f * 3)
            path.lineTo(width / 3f, height / 6f)
            path.lineTo(width / 12f * 7f, height / 2f)
            path.lineTo(width / 4f * 3f,height / 9f)
            path.lineTo(width / 12 * 11f,height / 3f * 2)
            if (real) {
                val realPath = Path()
                paint.getFillPath(path, realPath)
                paint.strokeWidth = 1f
                canvas?.drawPath(realPath, paint)
            } else {
                canvas?.drawPath(path, paint)
            }
        } else {
            // 文字
            textPaint.textSize = 36f
            canvas?.drawText("文字绘制效果", width / 4f * 3, height / 4f, textPaint)
            canvas?.drawText("文字对应的Path", width / 4f * 3, height / 2f, textPaint)
            textPaint.textSize = 50f
            val name = "Hello TangZhenTao"
            canvas?.drawText(name, width / 4f, height / 4f, textPaint)

            val dstPath = Path()
            textPaint.getTextPath(name, 0, name.length, width / 4f, height / 2f, dstPath)

            paint.apply {
                style = Paint.Style.STROKE
                color = Color.BLACK
            }
            canvas?.drawPath(dstPath, paint)
        }
    }
}