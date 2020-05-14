package com.tzt.customize.paint.widget.effect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Description: 阴影
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ShadowLayerView: View{

    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 50f
        color = Color.BLACK
    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        textPaint.setShadowLayer(10f, 0f, 0f, Color.RED)
        canvas?.drawText("Hello TangZhenTao ", 100f, height / 8f, textPaint)
        textPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            clearShadowLayer()
            setShadowLayer(15f, 2f, 2f, Color.GREEN)
        }
        canvas?.drawCircle(width / 2f,  height / 2f , height / 4f, textPaint)
    }
}