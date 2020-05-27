package com.tzt.custom.canvas.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.tzt.common.basedepency.dpToPx
import kotlin.math.abs


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class ChartView: View {
    private val TAG = "BarChart"

    /**
     * 内圆的颜色
     */
    private val INNER_DOT_COLOR = "#99E35B5B"

    /**
     * 外圆的颜色
     */
    private val OUTER_DOT_COLOR = "#28E35B5B"

    /**
     * 柱的颜色
     */
    private val BAR_COLOR = "#bb434343"

    /**
     * 文字颜色
     */
    private val TEXT_COLOR = "#64C5C5C5"

    /**
     * 动画时长
     */
    private val ANIM_DURATION = 2000

    /**
     * 柱子的数据
     */
    private val mBarInfoList: MutableList<BarInfo>? = ArrayList()

    /**
     * 描述字体的大小
     */
    private var mDescTextSize = 0f

    /**
     * 点的内半径
     */
    private var mDotInnerRadius = 0f

    /**
     * 点的外半径
     */
    private var mDotOuterRadius = 0f

    /**
     * 底部边距
     */
    private var mBottomSpacing = 0f

    /**
     * 柱与文字的距离
     */
    private var mBarTextSpacing = 0f

    /**
     * 柱子与柱子的间隔
     */
    private var mBarInterval = 0f

    /**
     * 柱子与上边距的距离
     */
    private var mTopSpacing = 0f

    /**
     * 柱子的高度
     */
    private var mBarHeight = 0f

    /**
     * 每根柱子的宽度
     */
    private var mBarWidth = 0f

    /**
     * 有数据的画布宽
     */
    private var mCanvasWidth = 0f

    /**
     * 用户可见的视图宽
     */
    private var mViewWidth = 0f

    /**
     * 柱子路径
     */
    private lateinit var mBarPath: Path

    /**
     * 画笔
     */
    private var mPaint: Paint? = null

    /**
     * 当前动画的进度
     */
    private var mAnimRate = 0f

    /**
     * 柱子颜色
     */
    private var mBarColor = 0

    /**
     * 内圆颜色
     */
    private var mInnerDotColor = 0

    /**
     * 外圆颜色
     */
    private var mOuterDotColor = 0

    /**
     * 字体大小
     */
    private var mTextColor = 0

    /**
     * 最后触碰的x坐标
     */
    private var mLastTouchX = 0f

    /**
     * 动画
     */
    private lateinit var mAnim: ValueAnimator

    /**
     * 滑动速度追踪
     */
    private var mVelocityTracker: VelocityTracker? = null

    /**
     * 滑动的最大速度
     */
    private var mMaximumVelocity = 0

    /**
     * 滑动的最小速度
     */
    private var mMinimumVelocity = 0

    /**
     * 滑动线程
     */
    private var mFling: FlingRunnable? = null

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        isClickable = true
        mDescTextSize = dpToPx(12)
        mDotInnerRadius = dpToPx(3.5f)
        mDotOuterRadius = dpToPx(5)
        mBarInterval = dpToPx(40)
        mBottomSpacing = dpToPx(10)
        mBarTextSpacing = dpToPx(12)
        mTopSpacing = dpToPx(10)
        mBarWidth = dpToPx(1.25f)
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mBarPath = Path()
        mBarColor = Color.parseColor(BAR_COLOR)
        mInnerDotColor = Color.parseColor(INNER_DOT_COLOR)
        mOuterDotColor = Color.parseColor(OUTER_DOT_COLOR)
        mTextColor = Color.parseColor(TEXT_COLOR)
        mMaximumVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
        mMinimumVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
        mFling = FlingRunnable(context)
        mAnim = ValueAnimator.ofFloat(0f, 1f)
        mAnim.duration = ANIM_DURATION.toLong()
        mAnim.addUpdateListener { animation ->
            mAnimRate = animation.animatedValue as Float
            postInvalidate()
        }
    }

    /**
     * 设置动画数据
     *
     * @param barInfoList
     */
    fun setBarInfoList(barInfoList: List<BarInfo>?) {
        mBarInfoList!!.clear()
        mBarInfoList.addAll(barInfoList!!)
        mCanvasWidth = (mBarInfoList.size + 1) * mBarInterval

        // 停止正在执行的动画
        if (mAnim.isRunning) {
            mAnim.cancel()
        }

        // 停止滚动
        if (mFling != null) {
            mFling!!.stop()
        }

        // 重置动画进度
        mAnimRate = 0f

        // 滚回最开始的坐标
        scrollTo(0, 0)

        // 提交刷新
        postInvalidate()
    }

    /**
     * 启动动画
     */
    fun start() {
        if (mBarInfoList == null || mBarInfoList.size == 0) {
            Log.e(TAG, "启动动画前，请先设置数据")
            return
        }
        mAnimRate = 0f
        if (mAnim.isRunning) {
            mAnim.cancel()
        }
        mAnim.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 柱子的高度 = 控件高度 - 上内边距 - 下内边距 - 字体大小 - 字体与柱子的间距
        mBarHeight = h - mTopSpacing - mBottomSpacing - mDescTextSize - mBarTextSpacing
        mViewWidth = w.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#66000000"))
        drawBar(canvas)
        drawDot(canvas)
        drawText(canvas)
    }

    /**
     * 控制屏幕不越界
     *
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        // 当数据的长度不足以滑动时，不做滑动处理
        if (mCanvasWidth < mViewWidth) {
            return true
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)
        if (MotionEvent.ACTION_DOWN == event.action) {
            mLastTouchX = event.x
            mFling!!.stop()
        } else if (MotionEvent.ACTION_MOVE == event.action) {
            // 滑动的距离
            val scrollLengthX = event.x - mLastTouchX
            // getScrollX() 小于0，说明画布右移了
            // getScrollX() 大于0，说明画布左移了
            val endX = scrollX - scrollLengthX
            if (scrollLengthX > 0) {    // 画布往右移动 -->

                // 注意：这里的等号不能去除，否则会有闪动
                if (endX <= 0) {
                    scrollTo(0, 0)
                } else {
                    scrollBy((-scrollLengthX).toInt(), 0)
                }
            } else if (scrollLengthX < 0) {                    // 画布往左移动  <--
                if (endX >= mCanvasWidth - mViewWidth) {     // 需要考虑是否右越界
                    scrollTo((mCanvasWidth - mViewWidth).toInt(), 0)
                } else {
                    scrollBy((-scrollLengthX).toInt(), 0)
                }
            }
            mLastTouchX = event.x
        } else if (MotionEvent.ACTION_UP == event.action) {
            // 计算当前速度， 1000表示每秒像素数等
            mVelocityTracker!!.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())

            // 获取横向速度
            val velocityX = mVelocityTracker!!.xVelocity.toInt()

            // 速度要大于最小的速度值，才开始滑动
            if (abs(velocityX) > mMinimumVelocity) {
                val initX = scrollX
                val maxX = (mCanvasWidth - mViewWidth).toInt()
                if (maxX > 0) {
                    mFling!!.start(initX, velocityX, initX, maxX)
                }
            }
            if (mVelocityTracker != null) {
                mVelocityTracker!!.recycle()
                mVelocityTracker = null
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 画柱
     *
     * @param canvas
     */
    private fun drawBar(canvas: Canvas) {
        mBarPath.reset()
        for (i in mBarInfoList!!.indices) {
            val x = (i + 1) * mBarInterval
            if (isInVisibleArea(x)) {
                mBarPath.moveTo(x, mTopSpacing)
                mBarPath.lineTo(x, mBarHeight + mTopSpacing)
            }
        }
        mPaint!!.color = mBarColor
        mPaint!!.strokeWidth = mBarWidth
        mPaint!!.style = Paint.Style.STROKE
        canvas.drawPath(mBarPath, mPaint!!)
    }

    /**
     * 画数据点
     *
     * @param canvas
     */
    private fun drawDot(canvas: Canvas) {
        mPaint!!.style = Paint.Style.FILL
        for (i in mBarInfoList!!.indices) {
            val x = (i + 1) * mBarInterval
            if (isInVisibleArea(x)) {
                val barInfo = mBarInfoList[i]
                val curBarDotY =
                    (mBarHeight * (1 - barInfo.percent * mAnimRate) + mTopSpacing).toFloat()

                // 画外圆
                mPaint!!.color = mOuterDotColor
                canvas.drawCircle(x, curBarDotY, mDotOuterRadius, mPaint!!)

                // 画内圆
                mPaint!!.color = mInnerDotColor
                canvas.drawCircle(x, curBarDotY, mDotInnerRadius, mPaint!!)
            }
        }
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private fun drawText(canvas: Canvas) {
        val textY = mTopSpacing + mBarHeight + mBarTextSpacing + mDescTextSize / 2
        for (i in mBarInfoList!!.indices) {
            val x = (i + 1) * mBarInterval
            if (isInVisibleArea(x)) {
                val barInfo = mBarInfoList[i]
                mPaint!!.color = mTextColor
                mPaint!!.textSize = mDescTextSize
                mPaint!!.textAlign = Paint.Align.CENTER
                canvas.drawText(barInfo.desc, x, textY, mPaint!!)
            }
        }
    }

    /**
     * 是否在可视的范围内
     *
     * @param x
     * @return true：在可视的范围内；false：不在可视的范围内
     */
    private fun isInVisibleArea(x: Float): Boolean {
        val dx = x - scrollX
        return -mBarInterval <= dx && dx <= mViewWidth + mBarInterval
    }

    /**
     * author : Jiang zinc
     * email : 56002982@qq.com
     * time : 2019/3/1 下午5:08
     * desc : 柱形图的数据
     * version :
     */
    class BarInfo(
        /**
         * 该柱的描述
         */
        val desc: String,
        /**
         * 该柱的占比
         */
        val percent: Double
    )

    /**
     * 滚动线程
     */
    inner class FlingRunnable constructor(context: Context?) : Runnable {
        private val mScroller: Scroller = Scroller(context, null, false)
        private var mInitX = 0
        private var mMinX = 0
        private var mMaxX = 0
        private var mVelocityX = 0
        fun start(
            initX: Int,
            velocityX: Int,
            minX: Int,
            maxX: Int
        ) {
            mInitX = initX
            mVelocityX = velocityX
            mMinX = minX
            mMaxX = maxX

            // 先停止上一次的滚动
            if (!mScroller.isFinished) {
                mScroller.abortAnimation()
            }

            // 开始 fling
            mScroller.fling(
                initX, 0, velocityX,
                0, 0, maxX, 0, 0
            )
            post(this)
        }

        override fun run() {

            // 如果已经结束，就不再进行
            if (!mScroller.computeScrollOffset()) {
                return
            }

            // 计算偏移量
            val currX = mScroller.currX
            var diffX = mInitX - currX
            Log.i(TAG, "run: [currX: $currX][diffX: $diffX][initX: $mInitX][minX: $mMinX][maxX: $mMaxX][velocityX: $mVelocityX]".trimIndent())

            // 用于记录是否超出边界，如果已经超出边界，则不再进行回调，即使滚动还没有完成
            var isEnd = false
            if (diffX != 0) {

                // 超出右边界，进行修正
                if (scrollX + diffX >= mCanvasWidth - mViewWidth) {
                    diffX = ((mCanvasWidth - mViewWidth - scrollX).toInt())
                    isEnd = true
                }

                // 超出左边界，进行修正
                if (scrollX <= 0) {
                    diffX = -getScrollX()
                    isEnd = true
                }
                if (!mScroller.isFinished) {
                    scrollBy(diffX, 0)
                }
                mInitX = currX
            }
            if (!isEnd) {
                post(this)
            }
        }

        /**
         * 进行停止
         */
        fun stop() {
            if (!mScroller.isFinished) {
                mScroller.abortAnimation()
            }
        }

    }
}