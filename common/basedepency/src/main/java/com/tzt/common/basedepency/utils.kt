package com.tzt.common.basedepency

import androidx.core.graphics.ColorUtils
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */

/**
 * dp 转为px
 * @param dp Int
 */
fun dpToPx(dp: Int): Float{
    val scale = TestApplication.context.resources.displayMetrics.density
    return dp * scale + 0.5f
}

/**
 * dp 转为px
 * @param dp Float
 */
fun dpToPx(dp: Float): Float{
    val scale = TestApplication.context.resources.displayMetrics.density
    return dp * scale + 0.5f
}

/**
 * 获取屏幕宽度
 */
fun screenWidth() = TestApplication.context.resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
fun screenHeight() = TestApplication.context.resources.displayMetrics.heightPixels

/**
 * 根据miter值算角度
 * @param miter strokeMiter
 */
fun angleByMiter(miter: Float): Float {
    // miter = 1 / sin(angel / 2)
    // miter = a / b(b是y坐标值，a平方 = x平方 +  b平法)
    // b = 100 则 a = b * miter
    // atan2(double y, double x)
    // 点到圆心(角度)的距离
    return (atan2(100.0, sqrt((100.0 * miter).pow(2) - 100.0.pow(2))) * 180 / Math.PI * 2).toFloat()
}

/**
 * 根据miter值算角度
 * @param y 点到圆心的纵坐标值
 * @param x 点到圆心的横坐标值
 */
fun angleByPoint(y: Double, x: Double): Float {
    // atan2(double y, double x)
    // x, y点到圆心(角度)的距离
    return (atan2(y, x) * 180 / Math.PI).toFloat()
}

/**
 * 是否是亮色
 * @param color 16进制颜色值
 * @return true亮色 false暗色
 */
fun isLightColor(color: Int) =  ColorUtils.calculateLuminance(color) >= 0.5