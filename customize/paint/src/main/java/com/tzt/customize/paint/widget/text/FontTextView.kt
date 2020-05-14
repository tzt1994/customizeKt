package com.tzt.customize.paint.widget.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Description: 尺寸
 * @author tangzhentao
 * @since 2020/5/6
 */
class FontTextView: View{
    companion object {
        const val FONT_SPACING = 1
        const val FONT_METRICS = 2
        const val TEXT_BOUNDS = 3
        const val MEASURE_TEXT = 4
        const val TEXT_WIDTHS = 5
        const val BREAK_TEXT = 6
        const val RUN_ADVANCE = 7
        const val HAS_GLYPH = 8
    }

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        strokeCap = Paint.Cap.SQUARE
    }

    private var linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    private var type = FONT_METRICS

    constructor(context: Context, type: Int = FONT_METRICS): this(context, null) {
        this.type = type
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when (type) {
            FONT_SPACING -> {
                // 获取推荐的行距
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.FILL
                    textSize = 60f
                    textAlign = Paint.Align.LEFT
                }
                linePaint.color = Color.BLACK

                val textWidth = paint.measureText("Hello TangZhenTao")
                canvas?.drawLine(width / 4f, height / 2f - paint.fontSpacing, width / 4f + textWidth, height / 2f - paint.fontSpacing, linePaint)
                canvas?.drawText("Hello TangZhenTao", width / 4f, height / 2f - paint.fontSpacing, paint)
                canvas?.drawLine(width / 4f, height / 2f, width / 4f + textWidth, height / 2f, linePaint)
                canvas?.drawText("你好 汤振涛", width / 4f, height / 2f, paint)
                canvas?.drawLine(width / 4f, height / 2f + paint.fontSpacing, width / 4f + textWidth, height / 2f +  paint.fontSpacing, linePaint)
                canvas?.drawText("ABCDEFGHIJKLMN", width / 4f, height / 2f + paint.fontSpacing, paint)

                linePaint.color = Color.RED
                canvas?.drawLine(width / 4f + textWidth ,height / 2f - paint.fontSpacing, width / 4f + paint.measureText("Hello TangZhenTao") ,height / 2f, linePaint)
                paint.apply {
                    color = Color.RED
                    textAlign = Paint.Align.LEFT
                    textSize = 36f
                }
                canvas?.drawText("linespacing", width / 4f + textWidth + 10f, height / 2f - paint.fontSpacing / 2f, paint)
            }
            FONT_METRICS -> {
                paint.apply {
                    style = Paint.Style.FILL
                    textSize = 100f
                    textAlign = Paint.Align.CENTER
                }
                val text = "My text line 1."
                val fontMetrics = paint.fontMetrics
                val baseline = height / 2f
                val top = baseline + fontMetrics.top
                val ascent = baseline + fontMetrics.ascent
                val descent = baseline + fontMetrics.descent
                val bottom = baseline + fontMetrics.bottom
                canvas?.drawText(text, width / 3f * 2, baseline, paint)

                // 画线的名称
                linePaint.color = Color.parseColor("#9400D3")
                pointPaint.color = Color.parseColor("#9400D3")
                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textSize = 36f
                    textAlign = Paint.Align.LEFT
                    color = Color.parseColor("#9400D3")
                }
                canvas?.drawLine(width / 3f, top, width * 1f, top, linePaint)
                canvas?.drawPoint(100f, height / 2f - 100, pointPaint)
                canvas?.drawText("Top", 150f, height / 2f - 90, textPaint)

                linePaint.color = Color.parseColor("#00FF00")
                pointPaint.color = Color.parseColor("#00FF00")
                textPaint.color = Color.parseColor("#00FF00")
                canvas?.drawLine(width / 3f, ascent, width * 1f, ascent, linePaint)
                canvas?.drawPoint(100f, height / 2f - 50, pointPaint)
                canvas?.drawText("Ascent", 150f, height / 2f - 40, textPaint)

                linePaint.color = Color.RED
                pointPaint.color = Color.RED
                textPaint.color = Color.RED
                canvas?.drawLine(width / 3f, baseline, width * 1f, baseline, linePaint)
                canvas?.drawPoint(100f, height / 2f, pointPaint)
                canvas?.drawText("Baseline", 150f, height / 2f + 10, textPaint)

                linePaint.color = Color.parseColor("#0000FF")
                pointPaint.color = Color.parseColor("#0000FF")
                textPaint.color = Color.parseColor("#0000FF")
                canvas?.drawLine(width / 3f, descent, width * 1f, descent, linePaint)
                canvas?.drawPoint(100f, height / 2f + 50, pointPaint)
                canvas?.drawText("Descent", 150f, height / 2f + 60, textPaint)

                linePaint.color = Color.parseColor("#FFA500")
                pointPaint.color = Color.parseColor("#FFA500")
                textPaint.color = Color.parseColor("#FFA500")
                canvas?.drawLine(width / 3f, bottom, width * 1f, bottom, linePaint)
                canvas?.drawPoint(100f, height / 2f + 100, pointPaint)
                canvas?.drawText("Bottom", 150f, height / 2f + 110, textPaint)
            }
            TEXT_BOUNDS -> {
                // 文字的显示范围矩形
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.FILL
                    textSize = 100f
                    textAlign = Paint.Align.LEFT
                }
                canvas?.drawText("Hello TangZhenTao", width / 8f, height / 2f, paint)
                val bounds = Rect()
                paint.getTextBounds("Hello TangZhenTao", 0, "Hello TangZhenTao".length, bounds)
                linePaint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 1f
                }
                bounds.left += width / 8
                bounds.top += height / 2
                bounds.right += width / 8
                bounds.bottom += height / 2
                canvas?.drawRect(bounds, linePaint)
            }
            MEASURE_TEXT -> {
                // 文字的占用宽度
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.FILL
                    textSize = 100f
                    textAlign = Paint.Align.LEFT
                }
                canvas?.drawText("Hello TangZhenTao", width / 8f, height / 2f, paint)
                val textWidth = paint.measureText("Hello TangZhenTao")
                linePaint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 1f
                }

                canvas?.drawLine(width / 8f, height / 2f, width / 8f + textWidth, height / 2f,  linePaint)
            }
            TEXT_WIDTHS -> {}
            BREAK_TEXT -> {
                // 设置截断文字的宽度对应的文字个数
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.FILL
                    textSize = 50f
                    textAlign = Paint.Align.LEFT
                }
                var mersuewCount: Int
                val measureWidth = floatArrayOf(0f)
                val text = "Hello TangZhenTao"

                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.BLACK
                    textSize = 36f
                }
                mersuewCount = paint.breakText(text, 0, text.length, true, 300f, measureWidth)
                canvas?.drawText(text, 0, mersuewCount, width / 8f, height / 5f, paint)
                canvas?.drawText("截取文字个数: $mersuewCount", width / 8f * 5, height / 5f, textPaint)
                mersuewCount = paint.breakText(text, 0, text.length, true, 400f, measureWidth)
                canvas?.drawText(text, 0, mersuewCount, width / 8f, height / 5f * 2, paint)
                canvas?.drawText("截取文字个数: $mersuewCount", width / 8f * 5, height / 5f * 2, textPaint)
                mersuewCount = paint.breakText(text, 0, text.length, true, 500f, measureWidth)
                canvas?.drawText(text, 0, mersuewCount, width / 8f, height / 5f * 3, paint)
                canvas?.drawText("截取文字个数: $mersuewCount", width / 8f * 5, height / 5f * 3, textPaint)
                mersuewCount = paint.breakText(text, 0, text.length, true, 600f, measureWidth)
                canvas?.drawText(text, 0, mersuewCount, width / 8f, height / 5f * 4, paint)
                canvas?.drawText("截取文字个数: $mersuewCount", width / 8f * 5, height / 5f * 4, textPaint)
            }
            RUN_ADVANCE -> {
                // 计算出某个字符处光标的 x 坐标
                paint.apply {
                    color = Color.BLACK
                    style = Paint.Style.FILL
                    textSize = 50f
                    textAlign = Paint.Align.LEFT
                }
                linePaint.apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                    strokeWidth = 1f
                }
                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.BLACK
                    textSize = 36f
                }
                val text = "Hello HenCoder \uD83C\uDDE8\uD83C\uDDF3"
                val length = text.length
                var advance = paint.getRunAdvance(text, 0, length, 0, length, false, length)
                canvas?.drawText(text, width / 8f, height / 7f, paint)
                canvas?.drawLine(width / 8f + advance, height / 7f +  paint.fontMetrics.top, width / 8f + advance, height / 7f +  paint.fontMetrics.bottom, linePaint)
                canvas?.drawText("offset = lenght", width / 8f * 5, height / 7f, textPaint)

                advance = paint.getRunAdvance(text, 0, length, 0, length, false, length - 1)
                canvas?.drawText(text, width / 8f, height / 7f * 2, paint)
                canvas?.drawLine(width / 8f + advance, height / 7f * 2 +  paint.fontMetrics.top, width / 8f + advance, height / 7f * 2 +  paint.fontMetrics.bottom, linePaint)
                canvas?.drawText("offset = lenght - 1", width / 8f * 5, height / 7f * 2, textPaint)

                advance = paint.getRunAdvance(text, 0, length, 0, length, false, length - 2)
                canvas?.drawText(text, width / 8f, height / 7f * 3, paint)
                canvas?.drawLine(width / 8f + advance, height / 7f * 3 +  paint.fontMetrics.top, width / 8f + advance, height / 7f * 3 +  paint.fontMetrics.bottom, linePaint)
                canvas?.drawText("offset = lenght - 2", width / 8f * 5, height / 7f * 3, textPaint)

                advance = paint.getRunAdvance(text, 0, length, 0, length, false, length - 3)
                canvas?.drawText(text, width / 8f, height / 7f * 4, paint)
                canvas?.drawLine(width / 8f + advance, height / 7f * 4 +  paint.fontMetrics.top, width / 8f + advance, height / 7f * 4 +  paint.fontMetrics.bottom, linePaint)
                canvas?.drawText("offset = lenght - 3", width / 8f * 5, height / 7f * 4, textPaint)

                advance = paint.getRunAdvance(text, 0, length, 0, length, false, length - 4)
                canvas?.drawText(text, width / 8f, height / 7f * 5, paint)
                canvas?.drawLine(width / 8f + advance, height / 7f * 5 +  paint.fontMetrics.top, width / 8f + advance, height / 7f * 5 +  paint.fontMetrics.bottom, linePaint)
                canvas?.drawText("offset = lenght - 4", width / 8f * 5, height / 7f * 5, textPaint)

                advance = paint.getRunAdvance(text, 0, length, 0, length, false, length - 5)
                canvas?.drawText(text, width / 8f, height / 7f * 6, paint)
                canvas?.drawLine(width / 8f + advance, height / 7f * 6 +  paint.fontMetrics.top, width / 8f + advance, height / 7f * 6 +  paint.fontMetrics.bottom, linePaint)
                canvas?.drawText("offset = lenght - 5", width / 8f * 5, height / 7f * 6, textPaint)
            }
            HAS_GLYPH -> {}
        }

    }
}