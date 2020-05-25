package com.tzt.customize.action.widget.shapeimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.action.R


/**
 * Description: shader实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ShapeShaderImageView: AppCompatImageView{
    // 裁剪路径
    private val clipPath = Path()
    // 边框路径
    private val borderPath = Path()
    // 边框颜色宽度
    private var borderColor = Color.WHITE
        set(value) {
            if (field != value) {
                field = value

                borderPaint.color = field
                postInvalidate()
            }
        }
    private var borderWidth = dpToPx(2)
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
        strokeJoin = Paint.Join.MITER
    }

    @ColorInt
    var bgColor = Color.TRANSPARENT
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    private val bitMapPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    var cornerRadius = dpToPx(5)
    // 设置圆角，宽长一致为圆角，不一致为椭圆角，宽长都大于0才有角, 宽长最大值为view宽高的一半。
    var radiusX = -1f
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    var radiusY = -1f
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    // 设置四边的圆角,默认设置
    var cornerTopLeftAble = true
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    var cornerTopRightAble = true
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    var cornerBottomLeftAble = true
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    var cornerBottomRightAble = true
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    // 左上角取消圆角是用来填充边框的空白问题
    private val suppleRectF = RectF()

    // 是否设置了大小
    private var isSetSize: Boolean = false

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ShapeShaderImageView)
        cornerTopLeftAble = typedArray.getBoolean(R.styleable.ShapeShaderImageView_shiv_top_left, true)
        cornerTopRightAble = typedArray.getBoolean(R.styleable.ShapeShaderImageView_shiv_top_right, true)
        cornerBottomLeftAble = typedArray.getBoolean(R.styleable.ShapeShaderImageView_shiv_bottom_left, true)
        cornerBottomRightAble = typedArray.getBoolean(R.styleable.ShapeShaderImageView_shiv_bottom_right, true)
        bgColor = typedArray.getColor(R.styleable.ShapeShaderImageView_shiv_bg_color, bgColor)
        borderColor = typedArray.getColor(R.styleable.ShapeShaderImageView_shiv_border_color, borderColor)
        borderWidth = typedArray.getDimension(R.styleable.ShapeShaderImageView_shiv_border_width, borderWidth)
        cornerRadius = typedArray.getDimension(R.styleable.ShapeShaderImageView_shiv_radius, cornerRadius)
        radiusX = typedArray.getDimension(R.styleable.ShapeShaderImageView_shiv_radius_x, radiusX)
        radiusY = typedArray.getDimension(R.styleable.ShapeShaderImageView_shiv_radius_y, radiusY)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 没有设置具体的大小
        isSetSize = !(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(bgColor)

        // BitmapShader实现
        canvas?.save()
        val bitmap = (drawable as BitmapDrawable).bitmap
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val matrix = setBitmapMatrixAndPath(width.toFloat(), height.toFloat(), bitmap)
        bitmapShader.setLocalMatrix(matrix)
        bitMapPaint.shader = bitmapShader
        canvas?.drawPath(clipPath, bitMapPaint)
        canvas?.restore()

        borderPaint.style = Paint.Style.STROKE
        canvas?.drawPath(borderPath, borderPaint)
        if (!cornerTopLeftAble) {
            borderPaint.style = Paint.Style.FILL
            canvas?.drawRect(suppleRectF, borderPaint)
        }
    }

    /**
     * 设置图片变化的matrix, 裁剪路径，边框路径
     *
     */
    private fun setBitmapMatrixAndPath(w: Float, h: Float, bitmap: Bitmap): Matrix {
        // 图片变化的matrix
        val matrix = Matrix()
        // 图片缩放比例
        val scaleX: Float
        val scaleY: Float
        // 缩放后的图片宽高
        val bw: Float
        val bh: Float
        // 移动圆点
        var transX = 0f
        var transY = 0f
        if (isSetSize) {
            when(scaleType) {
                ScaleType.FIT_XY -> {
                    // 不管图片大小，填充整个view
                    scaleX = w / bitmap.width
                    scaleY = h / bitmap.height
                    matrix.setScale(scaleX, scaleY)
                    setPath(borderWidth, borderWidth, w - borderWidth, h - borderWidth)
                }
                ScaleType.FIT_CENTER -> {
                    // fitCenter图片按比例缩放至View的宽度或者高度（取宽和高的最小值），居中显示
                    val scale: Float
                    if (w < h) {
                        scale = w / bitmap.width
                        transY = (h - bitmap.height * scale) / 2
                    } else {
                        scale = h / bitmap.height
                        transX = (w - bitmap.width * scale) / 2
                    }
                    matrix.setScale(scale, scale)
                    matrix.postTranslate(transX, transY)
                    bw = bitmap.width * scale
                    bh = bitmap.height * scale
                    val left = if (transX < 0) borderWidth else transX + borderWidth
                    val top = if (transY < 0) borderWidth else transY + borderWidth
                    val right = if (transX < 0) w - borderWidth else transX + bw -borderWidth
                    val bottom = if (transY < 0) h - borderWidth else transY + bh - borderWidth
                    setPath(left, top, right, bottom)
                }
                ScaleType.FIT_START -> {
                    // 图片按比例缩放至View的宽度或者高度（取宽和高的最小值），然后居上或者居左显示
                    val scale = if (w < h) {
                        w / bitmap.width
                    } else {
                        h / bitmap.height
                    }
                    matrix.setScale(scale, scale)
                    bw = bitmap.width * scale
                    bh = bitmap.height * scale
                    val left = borderWidth
                    val top = borderWidth
                    val right = if (w < bw) w - borderWidth else bw - borderWidth
                    val bottom = if (h < bh) h - borderWidth else bh - borderWidth
                    setPath(left, top, right, bottom)
                }
                ScaleType.FIT_END -> {
                    // 图片按比例缩放至View的宽度或者高度（取宽和高的最小值），然后居下或者居右显示
                    val scale: Float
                    if (w < h) {
                        scale = w / bitmap.width
                        transY = h - bitmap.height * scale
                    } else {
                        scale = h / bitmap.height
                        transX = w - bitmap.width * scale
                    }
                    matrix.setScale(scale, scale)
                    matrix.postTranslate(transX, transY)
                    bw = bitmap.width * scale
                    bh = bitmap.height * scale
                    val left = if (transX < 0) borderWidth else transX + borderWidth
                    val top = if (transY < 0) borderWidth else transY + borderWidth
                    val right = if (transX < 0) w - borderWidth else transX + bw - borderWidth
                    val bottom = if (transY < 0) h - borderWidth else transY + bh - borderWidth
                    setPath(left, top, right, bottom)
                }
                ScaleType.CENTER -> {
                    // 按照图片原始大小，居中显示，多余部分裁剪
                    transX = (w - bitmap.width) / 2
                    transY = (h - bitmap.height) / 2
                    matrix.postTranslate(transX, transY)
                    setPath(if (transX < 0) borderWidth else transX + borderWidth,
                        if (transY < 0) borderWidth else transY + borderWidth,
                        if (transX < 0) w - borderWidth else transX + bitmap.width - borderWidth,
                        if (transY < 0) h - borderWidth else transY + bitmap.height - borderWidth)
                }
                ScaleType.CENTER_INSIDE -> {
                    // centerInside的目标是将原图完整的显示出来，故按比例缩放原图，居中显示
                    val scale: Float
                    if (w < h) {
                        scale = w / bitmap.width
                        transY = (h - bitmap.height * scale) / 2
                    } else {
                        scale = h / bitmap.height
                        transX = (w - bitmap.width * scale) / 2
                    }
                    matrix.setScale(scale, scale)
                    matrix.postTranslate(transX, transY)
                    bw = bitmap.width * scale
                    bh = bitmap.height * scale
                    val left = if (transX < 0) borderWidth else transX + borderWidth
                    val top = if (transY < 0) borderWidth else transY + borderWidth
                    val right = if (transX < 0) w - borderWidth else transX + bw - borderWidth
                    val bottom = if (transY < 0) h - borderWidth else transY + bh -borderWidth
                    setPath(left, top, right, bottom)
                }
                ScaleType.CENTER_CROP -> {
                    // centerCrop的目标是将ImageView填充满，故按比例缩放原图，居中显示
                    val scale: Float
                    if (w > h) {
                        scale = w / bitmap.width
                        transY = (h - bitmap.height * scale) / 2
                    } else {
                        scale = h / bitmap.height
                        transX = (w -bitmap.width * scale) / 2
                    }
                    matrix.setScale(scale, scale)
                    matrix.postTranslate(transX, transY)
                    bw = bitmap.width * scale
                    bh = bitmap.height * scale
                    val left = if (transX < 0) borderWidth else transX + borderWidth
                    val top = if (transY < 0) borderWidth else transY + borderWidth
                    val right = if (transX < 0) w - borderWidth else transX + bw - borderWidth
                    val bottom = if (transY < 0) h - borderWidth else transY + bh -borderWidth
                    setPath(left, top, right, bottom)
                }
                ScaleType.MATRIX -> {
                    // 按照原图大小从左上角绘制，多余部分裁剪
                    bw = if (w < bitmap.width) w else bitmap.width.toFloat()
                    bh = if (h < bitmap.height) h else bitmap.height.toFloat()
                    setPath(borderWidth, borderWidth, bw - borderWidth, bh - borderWidth)
                }
                else -> {}
            }
        } else {
            scaleX = w / bitmap.width
            scaleY = h / bitmap.height
            matrix.setScale(scaleX, scaleY)

            setPath(borderWidth, borderWidth, w - borderWidth, h -borderWidth)
        }

        return matrix
    }

    /**
     * 设置裁剪路径和边框路径
     * @param left 裁剪框的left
     * @param top 裁剪框的top
     * @param right 裁剪框的right
     * @param bottom 裁剪框的bottom
     */
    private fun setPath(left: Float, top: Float, right: Float, bottom: Float) {
        clipPath.reset()
        borderPath.reset()
        val w = right - left
        val h = bottom - top
        setRadius(w, h)
        val borderLeft = left - borderWidth / 2
        val borderTop = top -  borderWidth / 2
        val borderRight = right +  borderWidth / 2
        val borderBottom = bottom + borderWidth / 2
        val borderRadiusX = radiusX + borderWidth / 2
        val borderRadiusY = radiusY + borderWidth / 2
        val bw = borderRight - borderLeft
        val bh = borderBottom - borderTop

        suppleRectF.left = borderLeft - borderWidth / 2
        suppleRectF.top = borderTop - borderWidth / 2
        suppleRectF.right = borderLeft
        suppleRectF.bottom = borderTop
        // 圆角或椭圆角的矩形
        val topLeftRectF = RectF()
        val topRightRectF = RectF()
        val bottomLeftRectF = RectF()
        val bottomRightRectF = RectF()

        if (radiusX <= 0 && radiusY <= 0) {
            // 没有圆角
            clipPath.addRect(left, top, right, bottom, Path.Direction.CW)
            borderPath.addRect(borderLeft, borderTop, borderRight, borderBottom, Path.Direction.CW)
        } else {
            // 有圆角
            if (cornerTopLeftAble) {
                // 裁剪
                // 左上角
                topLeftRectF.left = left
                topLeftRectF.top = top
                topLeftRectF.right = left + radiusX * 2
                topLeftRectF.bottom = top + radiusY * 2
                clipPath.addArc(topLeftRectF, 180f, 90f)

                // 边框
                // 左上角
                topLeftRectF.left = borderLeft
                topLeftRectF.top = borderTop
                topLeftRectF.right = borderLeft + borderRadiusX * 2
                topLeftRectF.bottom = borderTop + borderRadiusY * 2
                borderPath.moveTo(borderLeft, borderTop + borderRadiusY)
                borderPath.addArc(topLeftRectF, 180f, 90f)
                borderPath.moveTo(borderLeft + borderRadiusX, borderTop)
            } else {
                clipPath.moveTo(left, top)
                borderPath.moveTo(borderLeft, borderTop)
            }
            clipPath.lineTo(if (cornerTopRightAble) right - radiusX else right , top)
            if (bw != borderRadiusX * 2) {
                borderPath.lineTo(if (cornerTopRightAble) borderRight - borderRadiusX else borderRight , borderTop)
            }

            if (cornerTopRightAble) {
                // 右上角
                topRightRectF.left = right - radiusX * 2
                topRightRectF.top = top
                topRightRectF.right = right
                topRightRectF.bottom = top + radiusY * 2
                clipPath.addArc(topRightRectF, 270f, 90f)

                // 右上角
                topRightRectF.left = borderRight - borderRadiusX * 2
                topRightRectF.top = borderTop
                topRightRectF.right = borderRight
                topRightRectF.bottom = borderTop + borderRadiusY * 2
                borderPath.addArc(topRightRectF, 270f, 90f)
                borderPath.moveTo(borderRight, borderTop + borderRadiusY)
            }

            clipPath.lineTo(right, if (cornerBottomRightAble) bottom - radiusY else bottom)
            if (bh != borderRadiusY * 2) {
                borderPath.lineTo(borderRight, if (cornerBottomRightAble) borderBottom - borderRadiusY else borderBottom)
            }

            if (cornerBottomRightAble) {
                // 右下角
                bottomRightRectF.left = right - radiusX * 2
                bottomRightRectF.top = bottom - radiusY * 2
                bottomRightRectF.right = right
                bottomRightRectF.bottom = bottom
                clipPath.addArc(bottomRightRectF, 0f, 90f)

                // 右下角
                bottomRightRectF.left = borderRight - borderRadiusX * 2
                bottomRightRectF.top = borderBottom - borderRadiusY * 2
                bottomRightRectF.right = borderRight
                bottomRightRectF.bottom = borderBottom
                borderPath.addArc(bottomRightRectF, 0f, 90f)
                borderPath.moveTo(borderRight - borderRadiusX ,borderBottom)
            }
            clipPath.lineTo(if (cornerBottomLeftAble) left + radiusX else left, bottom)
            if (bw != borderRadiusX * 2) {
                borderPath.lineTo(if (cornerBottomLeftAble) borderLeft + borderRadiusX else borderLeft, borderBottom)
            }

            if (cornerBottomLeftAble) {
                // 左下角
                bottomLeftRectF.left = left
                bottomLeftRectF.top = bottom - radiusY * 2
                bottomLeftRectF.right = left + radiusX * 2
                bottomLeftRectF.bottom = bottom
                clipPath.addArc(bottomLeftRectF, 90f, 90f)


                // 左下角
                bottomLeftRectF.left = borderLeft
                bottomLeftRectF.top = borderBottom - borderRadiusY * 2
                bottomLeftRectF.right = borderLeft + borderRadiusX * 2
                bottomLeftRectF.bottom = borderBottom
                borderPath.addArc(bottomLeftRectF, 90f, 90f)
                borderPath.moveTo(borderLeft, borderBottom - borderRadiusY)
            }
            clipPath.lineTo(left, if (cornerTopLeftAble) top + radiusY else top)
            if (cornerTopLeftAble) {
                clipPath.lineTo(left, top + radiusY)
            }
            if (cornerTopRightAble) {
                clipPath.lineTo(right - radiusX, top)
            }
            if (cornerBottomRightAble) {
                clipPath.lineTo(right, bottom - radiusY)
            }
            if (cornerBottomLeftAble) {
                clipPath.lineTo(left + radiusX, bottom)
            }


            if (bh != borderRadiusY * 2) {
                borderPath.lineTo(borderLeft, if (cornerTopLeftAble) borderTop + borderRadiusY else borderTop)
            }
        }
    }

    /**
     * 设置圆角值
     */
    private fun setRadius(w: Float, h: Float) {
        if (radiusX < 0 || radiusY < 0) {
            if (cornerRadius < 0) {
                cornerRadius = 0f
            }

            radiusX = cornerRadius
            radiusY = cornerRadius
        }

        if (radiusX > w / 2) {
            radiusX = w / 2
        }
        if (radiusY > h / 2) {
            radiusY = h / 2
        }
    }
}