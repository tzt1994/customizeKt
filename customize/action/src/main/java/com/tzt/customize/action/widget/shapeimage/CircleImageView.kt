package com.tzt.customize.action.widget.shapeimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.action.R


/**
 * Description: xfermode实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class CircleImageView: AppCompatImageView{

    // 边框颜色
    var borderColor = Color.WHITE
        set(value) {
            if (field != value) {
                field = value

                borderPaint.color = field
                postInvalidate()
            }
        }
    // 边框宽度
    var borderWidth = dpToPx(2)
        set(value) {
            if (field != value) {
                field = value

                borderPaint.strokeWidth = field
                postInvalidate()
            }
        }
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = borderColor
        strokeWidth = borderWidth
    }

    private val bitMapPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView)
        borderColor = typedArray.getColor(R.styleable.CircleImageView_civ_border_color, borderColor)
        borderWidth = typedArray.getDimension(R.styleable.CircleImageView_civ_border_width, borderWidth)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = MeasureSpec.getSize(widthMeasureSpec)
        var h = MeasureSpec.getSize(heightMeasureSpec)

        if (w < h) {
            h = w
        } else {
            w = h
        }
        setMeasuredDimension(w, h)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        canvas?.save()
        val bitmap = (drawable as BitmapDrawable).bitmap
        val matrix = setBitmapMatrixAndPath(width.toFloat(), height.toFloat(), bitmap)
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        shader.setLocalMatrix(matrix)
        bitMapPaint.shader = shader
        canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, width.toFloat() / 2, bitMapPaint)
        canvas?.restore()

        canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, (width - borderWidth) / 2, borderPaint)
    }

    /**
     * 设置图片变化的matrix, 裁剪路径，边框路径
     *
     */
    private fun setBitmapMatrixAndPath(w: Float, h: Float, bitmap: Bitmap): Matrix {
        // 图片变化的matrix
        val matrix = Matrix()

        // 图片缩放比例
        val scale= if (bitmap.width < bitmap.height) {
            w / bitmap.width.toFloat()
        } else {
            h / bitmap.height.toFloat()
        }
        // 缩放后的图片宽高
        val bw = scale * bitmap.width
        val bh = scale * bitmap.height
        matrix.setScale(scale, scale)
        matrix.postTranslate((w - bw) / 2, (h - bh) / 2)

        return matrix
    }
}