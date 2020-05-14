package com.tzt.customize.path.bezier.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.tzt.customize.path.R

/**
 * Description:乘风破浪的小船
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class ShipBezierView: CoordinateView {
    // 小船浪花的宽度
    private val BOAT_LENGTH = 200

    // 浪花的宽度
    private var WAVE_LENGTH = 0

    // 小船浪花的高度
    private val BOAT_WAVE_HEIGHT = 20

    // 波浪高度
    private val WAVE_HEIGHT = 35

    // 浪花每次的偏移量
    private val WAVE_OFFSET = 5

    private var isInit = false

    private var mWidth = 0
    private var mHeight = 0

    private lateinit var mWavePaint: Paint

    // 海浪的路径
    private lateinit var mWavePath: Path

    // 小船的浪路径
    private lateinit var mBoatWavePath: Path

    // 小船的路径
    private lateinit var mBoatPath: Path

    private lateinit var mBoatPathMeasure: PathMeasure

    // 小船的浪色值
    private var mBoatBlue = 0

    // 浪花的色值
    private var mWaveBlue = 0

    // 小船当前所处的值
    private var mCurValue = 0f

    // 浪花当前的偏移量
    private var mCurWaveOffset = 0

    // 小船的浪花偏移量
    private var mBoatWaveOffset = 0

    // 用于变换小船的
    private lateinit var mMatrix: Matrix

    // 小船的图片
    private lateinit var mBoatBitmap: Bitmap

    private lateinit var mAnimator: ValueAnimator

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initAll()
    }

    fun initAll() {
        mWavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBoatPath = Path()
        mWavePath = Path()
        mBoatWavePath = Path()
        mBoatPathMeasure = PathMeasure()
        mBoatBlue = Color.parseColor("#ff00ee")
        mWaveBlue = Color.parseColor("#ffddaa")
        mMatrix = Matrix()

        // 加载图片
        val options = BitmapFactory.Options()
        options.inSampleSize = 1
        mBoatBitmap = BitmapFactory.decodeResource(resources, R.mipmap.boat, options)

        mAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 4000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                mCurValue = animatedValue as Float
                mCurWaveOffset = (mCurWaveOffset + WAVE_OFFSET) % mWidth
                mBoatWaveOffset = (mBoatWaveOffset + WAVE_OFFSET / 2) % mWidth
                postInvalidate()
            }
        }
    }

    /**
     * @param path       路径
     * @param length     浪花的宽度
     * @param height     浪花的高度
     * @param isClose    是否要闭合
     * @param lengthTime 浪花长的倍数
     */
    private fun initPath(
        path: Path,
        length: Int,
        height: Int,
        isClose: Boolean,
        lengthTime: Float
    ) {
        // 初始化 小船的路径
        path.moveTo((-length).toFloat(), (mHeight / 2).toFloat())
        var i = -length
        while (i < mWidth * lengthTime + length) {

            // rQuadTo 和 quadTo 区别在于
            // rQuadTo 是相对上一个点 而 quadTo是相对于画布
            path.rQuadTo(
                (length / 4).toFloat(),
                (-height).toFloat(),
                (length / 2).toFloat(),
                0f
            )
            path.rQuadTo(
                (length / 4).toFloat(),
                height.toFloat(),
                (length / 2).toFloat(),
                0f
            )
            i += length
        }
        if (isClose) {
            path.rLineTo(0f, (mHeight / 2).toFloat())
            path.rLineTo((-(mWidth * 2 + 2 * length)).toFloat(), 0f)
            path.close()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isInit) {
            isInit = true
            mWidth = measuredWidth
            mHeight = measuredHeight
            WAVE_LENGTH = mWidth / 3

            // 初始化 小船的浪路径
            initPath(mBoatWavePath, WAVE_LENGTH, BOAT_WAVE_HEIGHT, true, 2f)

            // 初始化 浪的路径
            initPath(mWavePath, WAVE_LENGTH, WAVE_HEIGHT, true, 2f)

            // 初始化 小船的路径
            initPath(mBoatPath, WAVE_LENGTH, BOAT_WAVE_HEIGHT, false, 1f)

            // 让 PathMeasure 与 Path 关联
            mBoatPathMeasure.setPath(mBoatPath, false)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val length = mBoatPathMeasure.length
        mBoatPathMeasure.getMatrix(
            length * mCurValue,
            mMatrix,
            PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG
        )
        mMatrix.preTranslate(
            (-mBoatBitmap.width / 2).toFloat(),
            (-mBoatBitmap.height * 5 / 6).toFloat()
        )
        canvas?.drawBitmap(mBoatBitmap, mMatrix, null)

        // 画船的浪花
        canvas?.save()
        canvas?.translate((-mBoatWaveOffset).toFloat(), 0f)
        mWavePaint.color = mBoatBlue
        canvas?.drawPath(mBoatWavePath, mWavePaint)
        canvas?.restore()

        // 画浪花
        canvas?.save()
        canvas?.translate((-mCurWaveOffset).toFloat(), 0f)
        mWavePaint.color = mWaveBlue
        canvas?.drawPath(mWavePath, mWavePaint)
        canvas?.restore()
    }

    fun startAnimator() {
        mAnimator.start()
    }

    fun stopAnimator() {
        mAnimator.cancel()
    }
}