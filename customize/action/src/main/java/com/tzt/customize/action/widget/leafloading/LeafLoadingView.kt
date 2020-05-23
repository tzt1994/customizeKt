package com.tzt.customize.action.widget.leafloading

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.tzt.customize.action.R
import kotlin.math.acos
import kotlin.math.sin
import kotlin.properties.Delegates


/**
 * Description: 风扇Loading
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class LeafLoadingView: View {
    companion object {
        // 中等振幅大小
        const val MIDDLE_AMPLITUDE = 13
        // 不同类型之间的振幅差距
        const val AMPLITUDE_DISPARITY = 5
        // 总进度
        private const val TOTAL_PROGRESS = 100
        // 叶子飘动一个周期所花的时间
        const val LEAF_FLOAT_TIME: Long = 3000
        // 叶子旋转一周需要的时间
        const val LEAF_ROTATE_TIME: Long = 2000
    }

    // 背景颜色
    var mBgColor = Color.parseColor("#fdc946")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 进度颜色
    var mProgressColor = Color.parseColor("#ffa800")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    // 上下左边距(进度条距离view的编剧)
    private var mLeftMargin = 0f
    // 中等振幅大小
    var mMiddleAmplitude = MIDDLE_AMPLITUDE
    // 振幅差
    var mAmplitudeDisparity = AMPLITUDE_DISPARITY
    // 叶子飘动一个周期所花的时间
    var mLeafFloatTime = LEAF_FLOAT_TIME
    // 叶子旋转一周需要的时间
    var mLeafRotateTime = LEAF_ROTATE_TIME

    // 叶子图片
    private var mLeafBitmap: Bitmap
    // 要展示的叶子宽高
    private var mLeafWidth by Delegates.notNull<Int>()
    private var mLeafHeight by Delegates.notNull<Int>()

    // 整体背景矩形
    private lateinit var mAllBgRect: RectF
    // 图片画笔
    private val mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 矩形框画笔
    private val mRectFPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    // 进度矩形部分的矩形框
    private lateinit var mProgressRectF: RectF
    // 进度圆弧部分的矩形框
    private lateinit var mArcRectF: RectF

    // 当前进度
    var mProgress = 0
    set(value) {
        if (field != value) {
            field = value
            postInvalidate()
        }
    }

    // 上次刷新的时间
    private var lastMillis: Long

    // 所绘制的进度条部分的宽度
    private var mProgressWidth = 0f
    // 当前所在的绘制的进度条的位置
    private var mCurrentProgressPosition = 0f
    // 弧形的半径
    private var mArcRadius = 0f
    // 用于产生叶子信息
    private val mLeafFactory = LeafFactory()
    // 产生出的叶子信息
    private var mLeafList: List<Leaf>
    // 用于控制随机增加的时间不抱团
    private var mAddTime: Long = 0
    // arc的右上角的x坐标，也是矩形x坐标的起始点
    private var mArcRightLocation = 0f

    constructor(
        context: Context,
        @ColorInt bgColor: Int,
        @ColorInt progressColor: Int,
        progress: Int,
        middleAmplitude: Int,
        amplitudeDisparity: Int,
        leafFloatTime: Long,
        leafRotateTime: Long
    ): this(context, null) {
        this.mBgColor = bgColor
        this.mProgressColor = progressColor
        this.mProgress = progress
        this.mMiddleAmplitude = middleAmplitude
        this.mAmplitudeDisparity = amplitudeDisparity
        this.mLeafFloatTime = leafFloatTime
        this.mLeafRotateTime = leafRotateTime
    }

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        mLeafFloatTime =
            LEAF_FLOAT_TIME
        mLeafRotateTime =
            LEAF_ROTATE_TIME

        mLeafBitmap = BitmapFactory.decodeResource(resources, R.mipmap.leaf)
        mLeafList = mLeafFactory.generateLeafs()

        lastMillis = System.currentTimeMillis()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mLeftMargin = h / 10f
        mArcRadius = (h - 2 * mLeftMargin) / 2
        mProgressWidth = w - mLeftMargin - h / 2

        mAllBgRect = RectF(0f, 0f, w.toFloat(), h.toFloat())

        mProgressRectF = RectF(mLeftMargin + mArcRadius, mLeftMargin, mCurrentProgressPosition, h - mLeftMargin)
        mArcRectF = RectF(mLeftMargin, mLeftMargin, mLeftMargin + mArcRadius * 2, h - mLeftMargin)
        mArcRightLocation = mLeftMargin + mArcRadius

        mLeafWidth = h / 4
        mLeafHeight = (h / 4) * (6 / 11)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mBitmapPaint.color = mBgColor
        canvas?.drawRoundRect(mAllBgRect, height / 2f, height / 2f, mBitmapPaint)

        // 绘制进度条和叶子
        mRectFPaint.color = mProgressColor
        drawProcessAndLeafs(canvas)

        if (mProgress < TOTAL_PROGRESS) {
            postInvalidate()
        }
    }

    /**
     * 绘制进度条和叶子
     * @param canvas 画布
     */
    private fun drawProcessAndLeafs(canvas: Canvas?) {
        // mProgressWidth为进度条的宽度，根据当前进度算出进度条的位置
        mCurrentProgressPosition = mProgressWidth * (mProgress.toFloat() / TOTAL_PROGRESS.toFloat())
        // 即当前位置在图中所示1范围内
        if (mCurrentProgressPosition - mArcRadius < 1e-6) {
            // 绘制叶子
            drawLeafs(canvas)

            // 绘制棕色ARC
            // 单边角度
            val angle = Math.toDegrees(acos((mArcRadius - mCurrentProgressPosition) / mArcRadius).toDouble()).toFloat()
            // 起始角度
            val startAngle = 180 - angle
            // 扫过的角度
            val sweepAngle = 2 * angle
            canvas?.drawArc(mArcRectF, startAngle, sweepAngle, false, mRectFPaint)
        } else {
            drawLeafs(canvas)
            // 绘制棕色src
            canvas?.drawArc(mArcRectF, 80f, 200f, false, mRectFPaint)
            // 绘制棕色矩形
            mProgressRectF.left = mArcRightLocation
            mProgressRectF.right = mCurrentProgressPosition
            canvas?.drawRect(mProgressRectF, mRectFPaint)
        }
    }

    /**
     * 绘制叶子
     * @param canvas 画布
     */
    private fun drawLeafs(canvas: Canvas?) {
        mLeafRotateTime = if (mLeafRotateTime <= 0) LEAF_ROTATE_TIME else mLeafRotateTime
        val currentTime = System.currentTimeMillis()
        for (leaf in mLeafList) {
            if (currentTime > leaf.startTime && leaf.startTime != 0L) {
                getLeafLocation(leaf, currentTime)

                val matrix = Matrix()
                val scale =  mLeafWidth.toFloat() / mLeafBitmap.width
                matrix.setScale(scale, scale)
                val transX = mLeftMargin + leaf.x
                val transY = mLeftMargin + leaf.y
                matrix.postTranslate(transX, transY)
                val rotateFraction = ((currentTime - leaf.startTime) % mLeafRotateTime) / mLeafRotateTime.toFloat()
                val angle = rotateFraction * 360
                val rotate = if (leaf.rotateDirection == 0) angle + leaf.rotateAngle else -angle + leaf.rotateAngle
                matrix.postRotate(rotate, transX + mLeafWidth / 2, transY + mLeafHeight / 2)
                canvas?.save()
                canvas?.drawBitmap(mLeafBitmap, matrix, mBitmapPaint)
                canvas?.restore()
            } else {
                continue
            }
        }
    }

    /**
     * 获取当前叶子的位置
     * @param leaf 当前叶子
     * @param currentTime 当前时间
     */
    private fun getLeafLocation(leaf: Leaf, currentTime: Long) {
        val intervalTime = currentTime - leaf.startTime
        mLeafFloatTime = if (mLeafFloatTime <= 0) LEAF_FLOAT_TIME else mLeafFloatTime
        if (intervalTime <=0) {
            return
        } else if (intervalTime > mLeafFloatTime) {
            leaf.startTime = System.currentTimeMillis() + (0..mLeafFloatTime.toInt()).random()
        }

        val fraction = intervalTime.toFloat() / mLeafFloatTime
        leaf.x = mProgressWidth - mProgressWidth * fraction
        leaf.y = getLocationY(leaf)
    }

    /**
     * 根据叶子信息获取Y值
     * @param leaf 叶子
     * return Int 叶子的Y值
     */
    private fun getLocationY(leaf: Leaf): Float {
        // y = A(wx + Q) + h
        val w = (2 * Math.PI / mProgressWidth).toFloat()
        val a = when (leaf.type) {
            StartType.LITTLE -> mMiddleAmplitude - mAmplitudeDisparity
            StartType.BIG -> mMiddleAmplitude + mAmplitudeDisparity
            else -> mMiddleAmplitude
        }

        return a * sin(w * leaf.x) + mArcRadius / 3 * 2
    }


    /**
     * 叶子振幅
     */
    enum class StartType {
        LITTLE, MIDDLE, BIG
    }

    /**
     * 叶子
     */
    inner class Leaf {
        // 在绘制部分的位置
        var x = 0f
        var y = 0f
        // 控制叶子飘动的幅度
        var type: StartType =
            StartType.MIDDLE
        // 旋转角度
        var rotateAngle = 0
        // 旋转方向--0代表顺时针，1代表逆时针
        var rotateDirection = 0
        // 起始时间(ms)
        var startTime: Long = 0
    }

    inner class LeafFactory {
        // 最大叶子数
        private val maxSize = 6

        /**
         * 生成一个叶子信息
         */
        private fun generateLeaf(): Leaf {
            val leaf = Leaf()
            when((1..3).random()) {
                1 -> leaf.type =
                    StartType.LITTLE
                2 -> leaf.type =
                    StartType.BIG
            }
            // 随机起始的旋转角度
            leaf.rotateAngle = (0..360).random()
            // 随机旋转方向（顺时针或逆时针)
            leaf.rotateDirection = (0..1).random()
            // 为了产生交错的感觉，让开始的时间有一定的随机性
            mLeafFloatTime = if (mLeafFloatTime <= 0) LEAF_FLOAT_TIME else mLeafFloatTime
            mAddTime += (0..(mLeafFloatTime * 2).toInt()).random()
            leaf.startTime = System.currentTimeMillis() + mAddTime

            return leaf
        }

        /**
         * 根据叶子数量产生叶子信息
         */
        fun generateLeafs(): List<Leaf> {
            val leafs = ArrayList<Leaf>()
            for (i in 0 until maxSize) {
                leafs.add(generateLeaf())
            }

            return leafs
        }

        /**
         * 根据叶子数量产生叶子信息
         * @param number 叶子数量
         */
        fun generateLeafs(number: Int): List<Leaf> {
            val leafs = ArrayList<Leaf>()
            for (i in 0 until number) {
                leafs.add(generateLeaf())
            }

            return leafs
        }
    }
}