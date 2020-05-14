package com.tzt.customize.paint.widget.effect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Description:抗锯齿
 * @author tangzhentao
 * @since 2020/5/6
 */
class AntiAliasView: View{

    private var paint: Paint = Paint()

    private var mAntiAlias = true

    constructor(context: Context, antiAlias: Boolean): this(context, null) {
        this.mAntiAlias = antiAlias
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            isAntiAlias = mAntiAlias
            style = Paint.Style.FILL
            color = Color.BLACK
        }

        val size = width / 3f - 100f
        canvas?.drawRect(50f, 50f, size + 50f, size + 50f, paint)
        canvas?.drawCircle(100f + size / 2 * 3f, 50f + size / 2, size / 2f, paint)
        paint.apply {
            textSize = 60f

        }
        canvas?.drawText("TangZhenTao", 150f + size * 2, 75f + size / 2, paint)
    }
}