package com.tzt.customize.propertyanimation.fragment

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.propertyanimation.R
import kotlinx.android.synthetic.main.fragment_type_evaluator.*


/**
 * Description: 自定义属性属性动画
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class TypeEvaluatorFragment: BaseFragment() {
    private lateinit var colorSystemAnimator: ObjectAnimator
    private lateinit var colorCustomAnimator: ObjectAnimator
    private lateinit var pointAnimator: ObjectAnimator

    override fun layoutResID(): Int {
        return R.layout.fragment_type_evaluator
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        colorSystemAnimator = ObjectAnimator.ofArgb(cpvSystem, "color", Color.parseColor("#ffff0000"), Color.parseColor("#ff00ff00")).apply {
            duration = 2000
        }
        colorCustomAnimator = ObjectAnimator.ofObject(cpvCustom, "color", ColorEvaluator(), Color.parseColor("#ffff0000"), Color.parseColor("#ff00ff00")).apply {
            duration = 2000
        }
        pointAnimator = ObjectAnimator.ofObject(ppvCustom, "position", PointFEvaluator(), PointF(0f, 0f), PointF(1f, 1f)).apply {
            duration = 2000
        }

    }

    override fun bindListener() {
        btnAnimate.setOnClickListener {
            colorSystemAnimator.start()
            colorCustomAnimator.start()
            pointAnimator.start()
        }
    }

    inner class ColorEvaluator: TypeEvaluator<Int> {
        private val startHsv = FloatArray(3)
        private val endHsv = FloatArray(3)
        private val outHsv = FloatArray(3)

        override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
            // 把 ARGB 转换成 HSV
            Color.colorToHSV(startValue, startHsv)
            Color.colorToHSV(endValue, endHsv)

            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180f) {
                endHsv[0] -= 360f
            } else if (endHsv[0] - startHsv[0] < -180f) {
                endHsv[0] += 360f
            }

            outHsv[0] = startHsv[0] + fraction * (endHsv[0] - startHsv[0])
            if (outHsv[0] > 360f) {
                outHsv[0] -= 360f
            } else if (outHsv[0] < 0) {
                outHsv[0] += 360f
            }
            outHsv[1] = startHsv[1] + fraction * (endHsv[1] - startHsv[1])
            outHsv[2] = startHsv[2] + fraction * (endHsv[2] - startHsv[2])
            val alpha = startValue shr 24 + ((endValue shr 24 - startValue shr 24) * fraction).toInt()

            return Color.HSVToColor(alpha, outHsv)
        }

    }

    inner class PointFEvaluator: TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val newPoint = PointF(0f, 0f)
            newPoint.x = startValue.x  + fraction * (endValue.x - startValue.x)
            newPoint.y = startValue.y  + fraction * (endValue.y - startValue.y)
            return newPoint
        }
    }
}