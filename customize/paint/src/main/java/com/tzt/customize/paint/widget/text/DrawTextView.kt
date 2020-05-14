package com.tzt.customize.paint.widget.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View


/**
 * Description: drawText()
 * @author tangzhentao
 * @since 2020/5/6
 */
class DrawTextView: View{
    companion object {
        const val DRAW_TEXT = 1
        const val DRAW_TEXT_RUN = 2
        const val DRAW_TEXT_PATH = 3
        const val STATIC_LAYOUT = 4
    }

    private var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    private var type = DRAW_TEXT

    constructor(context: Context, type: Int = DRAW_TEXT): this(context, null) {
        this.type = type
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when (type) {
            DRAW_TEXT -> {
                val text = "Hello TangZhenTao"
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textSize = 100f
                    Paint.Align.CENTER
                }
                canvas?.drawText(text, 100f, height / 2f, paint)

                paint.apply {
                    color = Color.RED
                    textSize = 36f
                    textAlign = Paint.Align.CENTER
                }
                canvas?.drawText("(x, y)", 100f, height / 2f + 50f, paint)
                paint.apply {
                    style = Paint.Style.STROKE
                    strokeWidth = 10f
                    strokeCap = Paint.Cap.ROUND
                }
                canvas?.drawPoint(100f, height / 2f, paint)
            }
            DRAW_TEXT_RUN -> {
                textPaint.textSize = 60f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    canvas?.drawTextRun("عربى", 0, "عربى".length, 0, "عربى".length, 200f, height / 4f, true,textPaint)
                    canvas?.drawTextRun("رب", 0, "رب".length, 0, "رب".length, 200f, height / 4f * 2, false,textPaint)
                    canvas?.drawTextRun("رب", 0, "رب".length, 0, "رب".length, 200f, height / 4f * 3, true,textPaint)
                }
            }
            DRAW_TEXT_PATH -> {
                textPaint.textSize = 36f
                val path = Path()
                path.reset()
                path.moveTo(width / 12f, height / 4f)
                path.lineTo(width / 6f, height / 4f * 3)
                path.lineTo(width / 3f, height / 6f)
                path.lineTo(width / 12f * 7f, height / 2f)
                path.lineTo(width / 4f * 3f,height / 9f)
                path.lineTo(width / 12 * 11f,height / 3f * 2)
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.BLACK
                    style = Paint.Style.STROKE
                }
                canvas?.drawPath(path, paint)
                canvas?.drawTextOnPath("Hello TangZhenTao Hello TangZhenTao", path, 0f, 0f, textPaint)
            }
            STATIC_LAYOUT -> {
                val paint = TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
                    color = Color.BLACK
                    textSize = 36f
                }
                val staticLayout1 = StaticLayout("Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                paint, 600, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
                val staticLayout2 = StaticLayout("a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz",
                    paint, 600, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
                canvas?.save()
                canvas?.translate(50f, 100f)
                staticLayout1.draw(canvas)
                canvas?.translate(0f, 200f)
                staticLayout2.draw(canvas)
                canvas?.restore()
            }
        }

    }
}