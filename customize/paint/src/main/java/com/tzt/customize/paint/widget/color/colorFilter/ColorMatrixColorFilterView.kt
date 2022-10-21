package com.tzt.customize.paint.widget.color.colorFilter

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tzt.customize.paint.R
import com.tzt.customize.paint.data.ColorMatrix
import com.tzt.customize.paint.data.ColorMatrixMode
import kotlin.math.max


/**
 * Description:颜色矩阵过滤器
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ColorMatrixColorFilterView: View{
    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.test_house)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 格式
    var model = ColorMatrixMode.NONE
        set(value) {
            if (field != value) {
                field = value
                if (model == ColorMatrixMode.NONE) {
                    brightness = 0
                    contrast = 1.0f
                    saturation = 1.0f
                }
                needPostInvalidate()
            }
        }

    // 是否需要动画
    var enableAnimaiton = true
        set(value) {
            if (field != value) {
                field = value
            }
        }

    // 动画间隔
    var animatorDuration = 500L
        set(value) {
            if (field != value) {
                field = value
            }
        }

    // 亮度：int型，范围[-255,255]，取0时没有效果，默认值为0
    var brightness = 0
        set(value) {
            if (field != value) {
                field = value
                needPostInvalidate()
            }
        }

    // 对比度：float型，范围[0, +∞)，取1.0时没有效果，默认值1.0。
    var contrast = 1.0f
        set(value) {
            if (field != value) {
                field = value
                needPostInvalidate()
            }
        }


    // 饱和度：float型，范围[0, +∞)，取1.0时没有效果，默认值1.0。
    var saturation = 1.0f
        set(value) {
            if (field != value) {
                field = value
                needPostInvalidate()
            }
        }

    private var oldMatrix = ColorMatrix.common()
    private var startMatrix = oldMatrix.clone()
    private var cuMatrix = oldMatrix.clone()
    private var newMatrix = ColorMatrix.common()
    
    private val valueAnimator = ValueAnimator.ofFloat(0f, 1f)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        valueAnimator.apply { 
            duration = animatorDuration
            repeatCount = 0
            addUpdateListener { 
                val progress = animatedValue as Float
                for (i in 0..19) {
                    cuMatrix[i] = startMatrix[i] * (1 - progress) + newMatrix[i] * progress
                }

                oldMatrix = cuMatrix.clone()
                postInvalidate()
            }
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator) {
                    startMatrix = oldMatrix.clone()
                }

                override fun onAnimationEnd(animation: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationCancel(animation: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.colorFilter = ColorMatrixColorFilter(cuMatrix)
        val bitmapMatrix = Matrix()
        val scale = max(width * 1f / bitmap.width, height * 1f / bitmap.height)
        bitmapMatrix.setScale(scale, scale)
        canvas?.drawBitmap(bitmap, bitmapMatrix, paint)
    }
    
    private fun startAnimaiton() {
        valueAnimator.duration = animatorDuration
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        valueAnimator.start()
    }

    /**
     * 是否刷新view
     */
    private fun needPostInvalidate() {
        newMatrix = calculateMatrix(model, brightness, contrast, saturation)
        if (enableAnimaiton) {
            startAnimaiton()
        } else {
            cuMatrix = newMatrix.clone()
            postInvalidate()
        }
    }

    /**
     * 计算目标颜色矩阵
     */
    private fun calculateMatrix(
        mode: Int,
        brightness: Int,
        contrast: Float,
        saturation: Float
    ): FloatArray {
        return applyBrightnessAndContrast(getMatrixByMode(mode, saturation), brightness, contrast)
    }

    /**
     * 根据亮度和对比度获取目标颜色矩阵
     */
    private fun applyBrightnessAndContrast(
        matrix: FloatArray,
        brightness: Int,
        contrast: Float
    ): FloatArray {
        val t = (1.0f - contrast) / 2.0f * 255.0f
        for (i in 0..2) {
            for (j in i * 5 until i * 5 + 3) {
                matrix[j] *= contrast
            }
            matrix[5 * i + 4] += t + brightness
        }
        return matrix
    }

    /**
     *根据风格和饱和度获取目标颜色矩阵
     */
    private fun getMatrixByMode(mode: Int, saturation: Float): FloatArray {
        var targetMatrix: FloatArray = ColorMatrix.common()
        when (mode) {
            ColorMatrixMode.NONE -> targetMatrix = ColorMatrix.common()
            ColorMatrixMode.GREY_SCALE -> targetMatrix = ColorMatrix.greyScale()
            ColorMatrixMode.INVERT -> targetMatrix = ColorMatrix.invert()
            ColorMatrixMode.RGB_TO_BGR -> targetMatrix = ColorMatrix.rgbToBgr()
            ColorMatrixMode.SEPIA -> targetMatrix = ColorMatrix.sepia()
            ColorMatrixMode.BRIGHT -> targetMatrix = ColorMatrix.bright()
            ColorMatrixMode.BLACK_AND_WHITE -> targetMatrix = ColorMatrix.blackAndWhite()
            ColorMatrixMode.VINTAGE_PINHOLE -> targetMatrix = ColorMatrix.vintagePinhole()
            ColorMatrixMode.KODACHROME -> targetMatrix = ColorMatrix.kodachrome()
            ColorMatrixMode.TECHNICOLOR -> targetMatrix = ColorMatrix.technicolor()
            ColorMatrixMode.SATURATION -> targetMatrix = getSaturationMatrix(saturation)
        }
        return targetMatrix
    }

    private fun getSaturationMatrix(saturation: Float): FloatArray {
        val lumR = 0.3086f
        val lumG = 0.6094f
        val lumB = 0.0820f
        val sr = (1 - saturation) * lumR
        val sg = (1 - saturation) * lumG
        val sb = (1 - saturation) * lumB
        val result: FloatArray = ColorMatrix.common()
        result[0] = sr + saturation
        result[1] = sg
        result[2] = sb
        result[5] = sr
        result[6] = saturation + sg
        result[7] = sb
        result[10] = sr
        result[11] = sg
        result[12] = saturation + sb
        return result
    }
}