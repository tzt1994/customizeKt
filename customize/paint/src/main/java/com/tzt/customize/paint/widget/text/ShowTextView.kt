package com.tzt.customize.paint.widget.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import java.util.*


/**
 * Description: 显示效果类
 * @author tangzhentao
 * @since 2020/5/6
 */
class ShowTextView: View{
    companion object {
        const val TEXT_SIZE = 1
        const val TEXT_TYPE_FACE = 2
        const val FAKE_BOLD_TEXT = 3
        const val STRIKE_THRU_TEXT = 4
        const val UNDER_LINE_TEXT = 5
        const val TEXT_SKEW_X = 6
        const val TEXT_SCALE_X = 7
        const val LETTER_SPACING = 8
        const val FONT_FEATURE_SETTINGS = 9
        const val TEXT_ALIGN = 10
        const val TEXT_LOCALE = 11
        const val HINTING = 12
        const val ELEGANT_TEXT_HEIGHT = 13
        const val SUBPIXEL_TEXT = 14
        const val LINEAR_TEXT = 15
    }

    private var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    private var type = TEXT_SIZE

    constructor(context: Context, type: Int = TEXT_SIZE): this(context, null) {
        this.type = type
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when (type) {
            TEXT_SIZE -> {
                // 文字大小
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 36f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                canvas?.drawText("18f", 80f, height / 5f, textPaint)
                canvas?.drawText("36f", 80f, height / 5f * 2, textPaint)
                canvas?.drawText("60f", 80f, height / 5f * 3, textPaint)
                canvas?.drawText("84f", 80f, height / 5f * 4, textPaint)
                textPaint.textAlign = Paint.Align.LEFT
                textPaint.textSize = 18f
                canvas?.drawText(text, 150f, height / 5f, textPaint)
                textPaint.textSize = 36f
                canvas?.drawText(text, 150f, height / 5f * 2, textPaint)
                textPaint.textSize = 60f
                canvas?.drawText(text, 150f, height / 5f * 3, textPaint)
                textPaint.textSize = 84f
                canvas?.drawText(text, 150f, height / 5f * 4, textPaint)

            }
            TEXT_TYPE_FACE -> {
                // 字体
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 50f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }

                textPaint.typeface = Typeface.DEFAULT
                canvas?.drawText(text, width / 2f, height / 4f, textPaint)
                textPaint.typeface = Typeface.SERIF
                canvas?.drawText(text, width / 2f, height / 4f * 2, textPaint)
                textPaint.typeface = Typeface.createFromAsset(context.assets, "Satisfy-Regular.ttf")
                canvas?.drawText(text, width / 2f, height / 4f * 3, textPaint)
            }
            FAKE_BOLD_TEXT -> {
                // 伪粗体
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                textPaint.isFakeBoldText = false
                canvas?.drawText(text, width / 2f, height / 3f, textPaint)
                textPaint.isFakeBoldText = true
                canvas?.drawText(text, width / 2f, height / 3f * 2, textPaint)
            }
            STRIKE_THRU_TEXT -> {
                // 删除线
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                textPaint.isStrikeThruText = false
                canvas?.drawText(text, width / 2f, height / 3f, textPaint)
                textPaint.isStrikeThruText = true
                canvas?.drawText(text, width / 2f, height / 3f * 2, textPaint)
            }
            UNDER_LINE_TEXT -> {
                // 下划线
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                textPaint.isUnderlineText = false
                canvas?.drawText(text, width / 2f, height / 3f, textPaint)
                textPaint.isUnderlineText = true
                canvas?.drawText(text, width / 2f, height / 3f * 2, textPaint)
            }
            TEXT_SKEW_X -> {
                // 文字倾斜度
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                textPaint.textSkewX = 0f
                canvas?.drawText(text, width / 2f, height / 4f, textPaint)
                textPaint.textSkewX = -0.5f
                canvas?.drawText(text, width / 2f, height / 4f * 2, textPaint)
                textPaint.textSkewX = 0.5f
                canvas?.drawText(text, width / 2f, height / 4f * 3, textPaint)
            }
            TEXT_SCALE_X -> {
                // 文字变胖变瘦
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                textPaint.textScaleX = 1f
                canvas?.drawText(text, width / 2f, height / 4f, textPaint)
                textPaint.textScaleX = 0.8f
                canvas?.drawText(text, width / 2f, height / 4f * 2, textPaint)
                textPaint.textScaleX = 1.2f
                canvas?.drawText(text, width / 2f, height / 4f * 3, textPaint)
            }
            LETTER_SPACING -> {
                // 字符间距 默认值是 0
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textPaint.letterSpacing = 0f
                    canvas?.drawText(text, width / 2f, height / 4f, textPaint)
                    textPaint.letterSpacing = 0.2f
                    canvas?.drawText(text, width / 2f, height / 4f * 2, textPaint)
                    textPaint.letterSpacing = 0.4f
                    canvas?.drawText(text, width / 2f, height / 4f * 3, textPaint)
                }
            }
            FONT_FEATURE_SETTINGS -> {
                // 用 CSS 的 font-feature-settings 的方式来设置文字
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textPaint.fontFeatureSettings = null
                    canvas?.drawText(text, width / 2f, height / 3f, textPaint)
                    textPaint.fontFeatureSettings = "smcp"
                    canvas?.drawText(text, width / 2f, height / 3f * 2, textPaint)
                }
            }
            TEXT_ALIGN -> {
                // 文字对齐方式
                val text = "Hello TangZhenTao"
                textPaint.apply {
                    textSize = 64f
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.RED
                    textSize = 36f
                    textAlign = Paint.Align.CENTER
                }
                val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.RED
                    strokeWidth = 20f
                    strokeCap = Paint.Cap.ROUND
                    style = Paint.Style.STROKE
                }

                textPaint.textAlign = Paint.Align.LEFT
                canvas?.drawPoint(width / 4f, height / 4f, pointPaint)
                canvas?.drawText("LEFT: (x y)", width / 4f, height / 4f + 50f, paint)
                canvas?.drawText(text, width / 4f, height / 4f, textPaint)
                textPaint.textAlign = Paint.Align.CENTER
                canvas?.drawPoint(width / 2f, height / 4f * 2, pointPaint)
                canvas?.drawText("CENTER: (x y)", width / 2f, height / 4f * 2 + 50f, paint)
                canvas?.drawText(text, width / 2f, height / 4f * 2, textPaint)
                textPaint.textAlign = Paint.Align.RIGHT
                canvas?.drawPoint(width / 4f * 3, height / 4f * 3, pointPaint)
                canvas?.drawText("RIGHT: (x y)", width / 4f * 3, height / 4f * 3 + 50f, paint)
                canvas?.drawText(text, width / 4f * 3, height / 4f * 3, textPaint)
            }
            TEXT_LOCALE -> {
                // 区域语言
                val text = "雨骨底条今直沿微写"
                textPaint.apply {
                    textSize = 64f
                    textAlign = Paint.Align.CENTER
                    color = Color.BLACK
                    style = Paint.Style.FILL
                }

                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.BLACK
                    textSize = 36f
                }
                canvas?.drawText("Locale.CHINA", 0f, height / 4f, paint)
                canvas?.drawText("Locale.TAIWAN", 0f, height / 4f * 2, paint)
                canvas?.drawText("Locale.JAPAN", 0f, height / 4f * 3, paint)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    textPaint.textLocale = Locale.CHINA
                    canvas?.drawText(text, width / 2f, height / 4f, textPaint)
                    textPaint.textLocale = Locale.TAIWAN
                    canvas?.drawText(text, width / 2f, height / 4f * 2, textPaint)
                    textPaint.textLocale = Locale.JAPAN
                    canvas?.drawText(text, width / 2f, height / 4f * 3, textPaint)
                }
            }
            HINTING -> { }
            ELEGANT_TEXT_HEIGHT -> { }
            SUBPIXEL_TEXT -> { }
            LINEAR_TEXT -> { }
        }

    }
}