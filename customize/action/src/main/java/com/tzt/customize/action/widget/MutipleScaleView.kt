package com.tzt.customize.action.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.action.R
import java.lang.NumberFormatException
import kotlin.math.abs

/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/28
 */
class MutipleScaleView: View {
    private val Tag = "MutipleScaleView-Log"

    companion object {
        /**
         * 刻度尺类型
         * SCALE_TEN 每10个刻度写一个刻度值
         * SCALE_FIVE 每5个人刻度写一个刻度值
         */
        const val SCALE_TEN = 1
        const val SCALE_FIVE = 2

        /**
         * 刻度模式
         * TYPE_IRREGULAR 不规则的刻度尺，需要传入一个string 列表
         * TYPE_RULE 规则的正常的刻度尺，需要设置最大值和最小值
         */
        const val TYPE_RULE = 1
        const val TYPE_IRREGULAR = 2
    }

    /**
     * 滑动控制类
     */
    private var mScroller: Scroller

    /**
     * 滑动速度计算类
     * 速度最大值
     * 速度最小值
     */
    private var mVelocityTracker: VelocityTracker? = null
    private val mMaxVeloctiy = ViewConfiguration.get(context).scaledMaximumFlingVelocity
    private val mMinVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    // 是否每10个刻度画一个刻度线
    var mOnlyTenScaleLineAble = false
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 刻度尺类型 规格和不规则
    var mScaleType = TYPE_RULE
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 刻度尺画刻度值规则
    var mScaleLineTextType = SCALE_TEN
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 点击选中
    var mClickSelectAble = false
        set(value) {
            if (field != value) {
                field = value
            }
        }
    // 不规则刻度尺数据
    var mIrregularList = arrayListOf("1", "3", "5", "10", "13", "16", "17")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 一个刻度代表的值
    var mUnitValue = 1f
        set(value) {
            if (field != value && field != 0f) {
                field = value
                postInvalidate()
            }
        }
    // 最小值
    var mMin = 0
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 最大值
    var mMax = 300
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    // 当前值，默认 100
    private var mCureentValue = if (mScaleType == TYPE_RULE) "100" else "10"
        set(value) {
            if (field != value) {
                field = value

                if (isRefesh) {
                    setValue()
                    postInvalidate()
                } else {
                    isRefesh = true
                }
            }
        }
    // 设置当前值是否需要重绘，给回调值用的
    private var isRefesh = false

    /**
     * 长线画笔
     * 断线画笔
     * 长线颜色宽度
     * 断线颜色宽度
     * 长线长
     */
    var mLongLineColor = Color.parseColor("#333333")
        set(value) {
            if (field != value) {
                field = value
                mLongLinePaint.color = mLongLineColor
                postInvalidate()
            }
        }
    var mLongLineWidth = dpToPx(2).toInt()
        set(value) {
            if (field != value) {
                field = value
                mLongLinePaint.strokeWidth = mLongLineWidth.toFloat()
                postInvalidate()
            }
        }
    var mShortLineColor = Color.parseColor("#666666")
        set(value) {
            if (field != value) {
                field = value
                mShortLinePaint.color = mShortLineColor
                postInvalidate()
            }
        }
    var mShortLineWidth = dpToPx(1).toInt()
        set(value) {
            if (field != value) {
                field = value
                mShortLinePaint.strokeWidth = mShowWidth.toFloat()
                postInvalidate()
            }
        }
    private var mLineHeight = dpToPx(24).toInt()
    var mLineSpace = dpToPx(8).toInt()
        set(value) {
            if (field != value) {
                field = value
                setSize()
                postInvalidate()
            }
        }
    private val mLongLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = mLongLineWidth.toFloat()
        color = mLongLineColor
    }
    private val mShortLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = mShortLineWidth.toFloat()
        color = mShortLineColor
    }

    /**
     * 每次可滑的最小刻度数量
     */
    var mRecentlyNums = 1
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
                moveRecently()
            }
        }

    // 选中指针画笔
    private val mIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mIndicatorSrc = R.mipmap.pointer
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    private var mIndicatorBitmap: Bitmap
    // 刻度值文字画笔
    private var mTextSize = dpToPx(14)
    var mTextColor = Color.parseColor("#333333")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    var mSelectColor = Color.parseColor("#ff8f00")
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = mTextSize
    }


    /**
     * 指针图片位置
     * 可滑动范围的宽高
     * 所有内容的长度
     * 左右最小值和最大值
     */
    private var mIndicatorX = 0
    private var mShowWidth = 0
    private var mShowHeight = 0
    private var mAllWidth = 0
    private var mLeftBorder = 0
    private var mRightBorder = 0

    private var onValueChangedListener: ((vlaue: String) -> Unit)? = null

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        mScroller = Scroller(context)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MutipleScaleView)
        mScaleType = typedArray.getInt(R.styleable.MutipleScaleView_msv_scale_type, mScaleType)
        mScaleLineTextType = typedArray.getInt(R.styleable.MutipleScaleView_msv_scale_line_able, mScaleLineTextType)
        mClickSelectAble = typedArray.getBoolean(R.styleable.MutipleScaleView_msv_click_select, mClickSelectAble)
        mIndicatorSrc = typedArray.getInt(R.styleable.MutipleScaleView_msv_indicator_src, mIndicatorSrc)
        mIndicatorBitmap = BitmapFactory.decodeResource(resources, mIndicatorSrc)
        val unitVal = typedArray.getFloat(R.styleable.MutipleScaleView_msv_unit, mUnitValue)
        Log.v(Tag, "xml最小值 $unitVal")
        mUnitValue = if (unitVal < 1e-6) {
            1f
        } else {
            unitVal
        }
        Log.v(Tag, "最小值 $mUnitValue")
        mMin = typedArray.getInt(R.styleable.MutipleScaleView_msv_min, mMin)
        mMax = typedArray.getInt(R.styleable.MutipleScaleView_msv_max, mMax)
        val value = typedArray.getString(R.styleable.MutipleScaleView_msv_value)?: ""
        // 重新计算默认值
        mCureentValue = if (mScaleType == TYPE_RULE) {
            try {
                val fValue = value.toFloat()
                when {
                    fValue <= mMin.toFloat() -> {
                        mMin.toString()
                    }
                    fValue >= mMax.toFloat() -> {
                        mMax.toString()
                    }
                    else -> {
                        value
                    }
                }
            } catch (e: NumberFormatException) {
                (mMin + (mMax - mMin) / 2).toString()
            }
        } else {
            if (mIrregularList.contains(value)) {
                mIrregularList[mIrregularList.indexOf(value)]
            } else {
                mIrregularList[mIrregularList.size / 2]
            }
        }
        val irregulars = typedArray.getString(R.styleable.MutipleScaleView_msv_irregulars)
        irregulars?.let {
            val scales = it.split(",")
            if (scales.isNotEmpty()) {
                for (scale in scales) {
                    mIrregularList.add(scale)
                }
            }
        }
        mSelectColor = typedArray.getColor(R.styleable.MutipleScaleView_msv_select_color, mSelectColor)
        mLongLineColor = typedArray.getColor(R.styleable.MutipleScaleView_msv_scale_long_line_color, mLongLineColor)
        mLongLineWidth = typedArray.getDimensionPixelSize(R.styleable.MutipleScaleView_msv_scale_long_line_width, mLongLineWidth)
        mShortLineColor = typedArray.getColor(R.styleable.MutipleScaleView_msv_scale_short_line_color, mShortLineColor)
        mShortLineWidth = typedArray.getDimensionPixelSize(R.styleable.MutipleScaleView_msv_scale_short_line_width, mShortLineWidth)
        mLineHeight = typedArray.getDimensionPixelSize(R.styleable.MutipleScaleView_msv_scale_line_height, mLineHeight)
        mLineSpace = typedArray.getDimensionPixelSize(R.styleable.MutipleScaleView_msv_scale_line_space, mLineSpace)
        mTextColor = typedArray.getColor(R.styleable.MutipleScaleView_msv_scale_text_color, mTextColor)
        mTextSize = typedArray.getDimension(R.styleable.MutipleScaleView_msv_scale_text_size, mTextSize)
        mRecentlyNums = typedArray.getInt(R.styleable.MutipleScaleView_msv_min_scrollable_nums, mRecentlyNums)
        typedArray.recycle()

        if (mScaleType == TYPE_IRREGULAR) {
            mRecentlyNums = 10
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = (paddingTop + paddingBottom + measureTextHeight() * 3 + mLineHeight / 2 * 3).toInt()

        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initSize(w, h)
        setSize()
        setValue()
    }

    /**
     * 测量文字高
     */
    private fun measureTextHeight(): Float {
        return mTextPaint.fontMetrics.descent - mTextPaint.fontMetrics.ascent
    }

    /**
     * 初始化一些尺寸值
     */
    private fun initSize(w: Int, h: Int) {
        mShowWidth = w - paddingLeft - paddingRight
        mShowHeight = h - paddingTop - paddingBottom
        mIndicatorX = w / 2
        mLeftBorder = paddingLeft
    }

    /**
     * 设置一些大小
     */
    private fun setSize() {
        mAllWidth = if (mScaleType == TYPE_RULE) {
            ((mMax - mMin) / mUnitValue).toInt() * mLineSpace

        } else {
            (mIrregularList.size - 1) * 10 * mLineSpace
        }
        mRightBorder = mLeftBorder + mAllWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLine(canvas)
        drawScale(canvas)
        drawIndicator(canvas)
    }

    /**
     * 画刻度线
     * @param canvas 画布
     */
    private fun drawLine(canvas: Canvas) {
        // 获取刻度线个数
        val lineTotal: Int = when(mScaleType) {
            TYPE_RULE -> {
                // 规则数据
                ((mMax - mMin) / mUnitValue).toInt()
            }
            TYPE_IRREGULAR -> {
                // 不规则数据
                (mIrregularList.size - 1) * 10
            }
            else -> 0
        }

        // 刻度线大于0才去画刻度线
        if (lineTotal > 0) {
            for (index in 0..lineTotal) {
                val startX = mLeftBorder + index * mLineSpace
                if (isInVisibleArea(startX)) {
                    // 在可视化范围才画
                    if (mOnlyTenScaleLineAble) {
                        // 只画10的刻度线
                        canvas.drawLine(startX.toFloat(), paddingTop + mLineHeight / 2f, startX.toFloat(), paddingTop + mLineHeight / 2f * 3, mLongLinePaint)
                    } else {
                        when {
                            index % 10 == 0 -> {
                                // 每10个刻度线
                                canvas.drawLine(startX.toFloat(), paddingTop + mLineHeight / 2f, startX.toFloat(), paddingTop + mLineHeight / 2f * 3, mLongLinePaint)
                            }
                            index % 5 == 0 -> {
                                // 每5个刻度线
                                canvas.drawLine(startX.toFloat(), paddingTop +mLineHeight / 2f, startX.toFloat(), paddingTop + mLineHeight / 2f + mLineHeight / 4 * 3, mLongLinePaint)
                            }
                            else -> {
                                // 普通刻度线
                                canvas.drawLine(startX.toFloat(), paddingTop + mLineHeight / 2f, startX.toFloat(), paddingTop + mLineHeight.toFloat(), mShortLinePaint)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 画刻度值
     * @param canvas 画布
     */
    private fun drawScale(canvas: Canvas) {
        val fontMetrics = mTextPaint.fontMetrics
        val baseline = paddingTop + mLineHeight / 2 * 3 + measureTextHeight() * 3 / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent

        // 获取刻度线个数
        when(mScaleType) {
            TYPE_RULE -> {
                // 规则数据
                val lineTotal = ((mMax - mMin) / mUnitValue).toInt()
                if (lineTotal > 0) {
                    for (index in 0..lineTotal) {
                        val  x = mLeftBorder + mLineSpace * index
                        if (isInVisibleArea(x)) {
                            if (mOnlyTenScaleLineAble) {
                                // 只画10的刻度值
                                if (index % 10 == 0) {
                                    mTextPaint.color = if (isSelectX(x)) mSelectColor else mTextColor
                                    canvas.drawText(calucateScaleValue(index) , x.toFloat(), baseline, mTextPaint)
                                }
                            } else {
                                when(mScaleLineTextType) {
                                    SCALE_FIVE -> {
                                        // 每5个刻度画一个刻度值
                                        if (index % 10 == 0) {
                                            mTextPaint.color = if (isSelectX(x)) mSelectColor else mTextColor
                                            canvas.drawText(calucateScaleValue(index) ,x.toFloat(), baseline, mTextPaint)
                                        } else if (index % 5 == 0) {
                                            mTextPaint.color = if (isSelectX(x)) mSelectColor else mTextColor
                                            canvas.drawText(calucateScaleValue(index) ,x.toFloat(), baseline, mTextPaint)
                                        }
                                    }
                                    SCALE_TEN -> {
                                        // 没10个画一个刻度
                                        if (index % 10 == 0) {
                                            mTextPaint.color = if (isSelectX(x)) mSelectColor else mTextColor
                                            canvas.drawText(calucateScaleValue(index) ,x.toFloat(), baseline, mTextPaint)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            TYPE_IRREGULAR -> {
                // 不规则数据
                val lineTotal = (mIrregularList.size - 1) * 10
                if (lineTotal > 0) {
                    for (index in 0..lineTotal) {
                        val x = mLeftBorder + index * mLineSpace
                        if (isInVisibleArea(x) && index % 10 == 0) {
                            mTextPaint.color = if (isSelectX(x)) mSelectColor else mTextColor
                            canvas.drawText(mIrregularList[index / 10], x.toFloat(), baseline, mTextPaint)
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据刻度线计算刻度值
     * @param index 第n个刻度线
     */
    private fun calucateScaleValue(index: Int): String {
        return if (issFloatUnit()) {
            (index * mUnitValue + mMin).toString()
        } else {
            (index * mUnitValue + mMin).toInt().toString()
        }
    }

    /**
     * 是否是小于1的最新小刻度值
     */
    private fun issFloatUnit(): Boolean {
        val units = mUnitValue.toString().split(".")
        return units[1].toInt() != 0
    }

    /**
     * 画指针图
     */
    private fun drawIndicator(canvas: Canvas) {
        val scale = (mLineSpace * 2 - dpToPx(1)) / mIndicatorBitmap.width
        val bw = mIndicatorBitmap.width * scale
        val bh = mIndicatorBitmap.height * scale
        val x = mIndicatorX + mScroller.currX
        val y = paddingTop.toFloat()
        val rectF = RectF(x - bw / 2, y, x + bw / 2, y + bh)
        canvas.drawBitmap(mIndicatorBitmap, null, rectF, mIndicatorPaint)
    }

    /**
     * 当前点是否在视图内
     * return boolean
     */
    private fun isInVisibleArea(x: Int): Boolean{
        val dx = x - mScroller.currX
        return dx in mLeftBorder..(mLeftBorder + mShowWidth)
    }

    /**
     * 是否是选中的值
     */
    private fun isSelectX(x: Int): Boolean {
        val sx = mIndicatorX + mScroller.currX
        return x == sx
    }

    private var lastX = 0f
    private var lastY = 0f
    private var isClisckAble = false
    private var lastTouchDownTime = 0L
    // 是否抬起了手指，默认true
    private var pressUp = true
    private var isFling = false
    // 是否是fling的滑动的结束
    private var isFilingFinsh = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }

        mVelocityTracker?.addMovement(event)
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchDownTime = System.currentTimeMillis()
                isClisckAble = true
                pressUp = false
                lastX = event.x
                lastY = event.y
                mVelocityTracker?.clear()
            }
            MotionEvent.ACTION_MOVE -> {
                isFilingFinsh = false
                pressUp = false
                val cuX = event.x
                val cuY = event.y
                // 便宜超过20px认为是滑动
                if (abs(cuX - lastX) > 20 || abs(cuY - lastY) > 20) {
                    isClisckAble = false
                }

                // 上下滑动拦截
                if (abs(cuX - lastX) < abs(cuY - lastY)) {
                    return false
                }

                val scrolledX = lastX - cuX
                doScroll(scrolledX.toInt(), 0)
                lastX = cuX
                lastY = cuY
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                pressUp = true
                val upTime = System.currentTimeMillis() - lastTouchDownTime
                if (isClisckAble && mClickSelectAble && upTime <= 200) {
                    // 没有滑动且按下和抬起不超过200毫秒认为是点击
                    val scrolledX = event.x - mIndicatorX
                    doScroll(scrolledX.toInt(), 300)
                } else {
                    //处理松手后的Fling 获取当前事件的速率，1毫秒运动了多少个像素的速率，1000表示一秒
                    mVelocityTracker?.computeCurrentVelocity(1000, mMaxVeloctiy.toFloat())
                    //获取横向速率
                    val velocityX = mVelocityTracker?.xVelocity?.toInt()?: 0
                    if (abs(velocityX) > mMinVelocity) {
                        //滑动速度大于最小速度 就滑动
                        isFilingFinsh = true
                        isFling = true
                        mScroller.fling(mScroller.currX, 0, -velocityX, 0,
                            mLeftBorder - mShowWidth / 4 * 3, mLeftBorder + mAllWidth - mShowWidth / 4,
                        0,0)
                        invalidate()
                    } else {
                        // 进行边界判断进行滑动
                        isFling = false
                        when {
                            mScroller.finalX < mLeftBorder - mShowWidth / 2 -> {
                                doScroll(mLeftBorder - mShowWidth / 2 - mScroller.finalX, 500)
                            }
                            mScroller.finalX > mLeftBorder + mAllWidth - mShowWidth / 2 -> {
                                doScroll(mLeftBorder + mAllWidth - mShowWidth / 2 - mScroller.finalX ,500)
                            }
                            else -> {
                                moveRecently()
                            }
                        }
                    }

                    // 释放
                    mVelocityTracker!!.recycle()
                    mVelocityTracker = null
                }
            }
        }

        return true
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScroller.currX == mScroller.finalX && isFling) {
                Log.v(Tag, "没结束")
                isFling = false
                moveRecently()
            }
            scrollTo(mScroller.currX, 0)
            invalidate()
        } else {
            Log.v(Tag, "结束")
            if (isFilingFinsh) {
                // fling滑动结束进行超过边界进行回弹
                when {
                    mScroller.finalX < mLeftBorder - mShowWidth / 2 -> {
                        Log.v(Tag, "超过最左边")
                        doScroll(mLeftBorder - mShowWidth / 2 - mScroller.finalX, 500)
                    }
                    mScroller.finalX > mLeftBorder + mAllWidth - mShowWidth / 2 -> {
                        Log.v(Tag, "超过最右边")
                        doScroll(mLeftBorder + mAllWidth - mShowWidth / 2 - mScroller.finalX ,500)
                    }
                    else -> {
                        // fling滑动结束值回调
                        if (pressUp) valueCallBack()
                    }
                }
                isFilingFinsh = false
            } else {
                // 正常的滑动结束 只回调
                if (pressUp) valueCallBack()
            }
        }
        super.computeScroll()
    }

    /**
     * 进行滑动
     * @param dx 相对于当钱偏移量
     * @param duration 滑动时长
     */
    private fun doScroll(dx: Int, duration: Int) {
        mScroller.startScroll(mScroller.finalX, mScroller.finalY, dx, 0, duration)
        invalidate()
    }

    /**
     * 滑动结束进行值回调
     */
    private fun valueCallBack() {
        // 当前居中的x值
        val selectX = mIndicatorX + mScroller.currX
        val value = when (mScaleType) {
            TYPE_RULE -> {
                ((selectX - mMin) / mLineSpace).toString()
            }
            TYPE_IRREGULAR -> {
                var index = (selectX - mMin) / (mLineSpace * 10)
                if (index < 0) {
                    index = 0
                } else if (index >= mIrregularList.size) {
                    index = mIrregularList.size - 1
                }

                mIrregularList[index]
            }
            else -> ""
        }
        if (value != mCureentValue) {
            isRefesh = false
            mCureentValue = value
            onValueChangedListener?.let {
                it(mCureentValue)
            }
        }
    }

    /**
     * 获取在左右边界里的偏移量
     */
    private fun getScrolledX(): Int{
        return when {
            mScroller.finalX < (mLeftBorder - mShowWidth / 2) -> {
                mLeftBorder - mShowWidth / 2
            }
            mScroller.finalX > (mLeftBorder + mAllWidth - mShowWidth / 2) -> {
                mLeftBorder + mAllWidth + mShowWidth / 2
            }
            else -> {
                mScroller.finalX
            }
        }
    }

    /**
     * 滑动到最近的刻度
     */
    private fun moveRecently() {
        val scrolledX = getScrolledX()
        val distance = (mIndicatorX + scrolledX) % (mLineSpace * mRecentlyNums)
        if (distance == 0 && scrolledX == mScroller.finalX) return
        if (distance >= mLineSpace * mRecentlyNums / 2) {
            doScroll(mLineSpace * mRecentlyNums - distance, 300)
        } else {
            doScroll(-distance, 300)
        }
    }

    /**
     * 设置当前值
     */
    private fun setValue() {
        var dx = -1
        when(mScaleType) {
            TYPE_RULE -> {
                var value: Float
                try {
                    value = mCureentValue.toFloat()
                    Log.v(Tag, "值 $value")
                    when {
                        value <= mMin.toFloat() -> {
                            value = mMin.toFloat()
                        }
                        value >= mMax.toFloat() -> {
                            value = mMax.toFloat()
                        }
                    }
                } catch (e: NumberFormatException) {
                    value = mMin + (mMax - mMin) / 2f
                }

                dx = (mLeftBorder + (value - mMin) / mUnitValue * mLineSpace).toInt() - mIndicatorX
            }
            TYPE_IRREGULAR -> {
                val index = if (mIrregularList.contains(mCureentValue)) {
                    mIrregularList.indexOf(mCureentValue)
                } else {
                    mIrregularList.size / 2
                }

                dx = mLeftBorder + index * 10 * mLineSpace - mIndicatorX
            }
        }

        if (dx != 0 && dx != mScroller.finalX) {
            mScroller.startScroll(mScroller.finalX, mScroller.finalY, dx, 0, 500)
        }
    }

    /**
     * 设置数据回调
     */
    fun setOnValueChangedListener(block: (value: String) -> Unit) {
        this.onValueChangedListener = block
    }
}