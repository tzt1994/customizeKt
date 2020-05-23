package com.tzt.customize.action.widget.leafloading

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.action.R


/**
 * Description: 风扇叶子loading
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class LeafLoadingLayout: RelativeLayout {

    var mBgColor = Color.parseColor("#fdc946")
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mBgColor  = mBgColor
            }
        }
    var mProgressColor = Color.parseColor("#ffa800")
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mProgressColor  = mProgressColor
            }
        }
    var mProgress = 0
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mProgress  = mProgress
                mFanView.mProgres = mProgress
            }
        }
    // 中等振幅大小
    var mMiddleAmplitude = LeafLoadingView.MIDDLE_AMPLITUDE
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mMiddleAmplitude  = mMiddleAmplitude
            }
        }
    // 振幅差
    var mAmplitudeDisparity = LeafLoadingView.AMPLITUDE_DISPARITY
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mAmplitudeDisparity  = mAmplitudeDisparity
            }
        }
    // 叶子飘动一个周期所花的时间
    var mLeafFloatTime = LeafLoadingView.LEAF_FLOAT_TIME
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mLeafFloatTime  = mLeafFloatTime
            }
        }
    // 叶子旋转一周需要的时间
    var mLeafRotateTime = LeafLoadingView.LEAF_ROTATE_TIME
        set(value) {
            if (field != value) {
                field = value
                mLeafView.mLeafRotateTime  = mLeafRotateTime
            }
        }

    var mFanBgColor = Color.parseColor("#fed255")
        set(value) {
            if (field != value) {
                field = value
                mFanView.mFanBgColor  = mFanBgColor
            }
        }
    var mFanBorderColor = Color.parseColor("#ffffff")
        set(value) {
            if (field != value) {
                field = value
                mFanView.mFanBorderColor  = mFanBorderColor
            }
        }
    var mFanTextColor = Color.WHITE
        set(value) {
            if (field != value) {
                field = value
                mFanView.mFanTextColor  = mFanTextColor
            }
        }
    var mFanTextSize =  dpToPx(12)
        set(value) {
            if (field != value) {
                field = value
                mFanView.mFanTextSize  = mFanTextSize
            }
        }
    var mBorderWidth = dpToPx(3)
        set(value) {
            if (field != value) {
                field = value
                mFanView.mBorderWidth  = mBorderWidth
            }
        }

    private val mLeafView: LeafLoadingView
    private val mFanView: FanView

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LeafLoadingLayout)
        mBgColor = typedArray.getColor(R.styleable.LeafLoadingLayout_lll_bg_color, mBgColor)
        mProgressColor = typedArray.getColor(R.styleable.LeafLoadingLayout_lll_progress_color, mProgressColor)
        mFanBgColor = typedArray.getColor(R.styleable.LeafLoadingLayout_lll_fan_bg_color, mFanBgColor)
        mFanBorderColor = typedArray.getColor(R.styleable.LeafLoadingLayout_lll_fan_border_color, mFanBorderColor)
        mBorderWidth = typedArray.getDimension(R.styleable.LeafLoadingLayout_lll_fan_border_width, mBorderWidth)
        mFanTextColor = typedArray.getColor(R.styleable.LeafLoadingLayout_lll_fan_text_color, mFanTextColor)
        mFanTextSize = typedArray.getDimension(R.styleable.LeafLoadingLayout_lll_fan_text_size, mFanTextSize)
        typedArray.recycle()
    }

    init {
        val leafParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        mLeafView = LeafLoadingView(context, mBgColor, mProgressColor, mProgress, mMiddleAmplitude, mAmplitudeDisparity, mLeafFloatTime, mLeafRotateTime)
        addView(mLeafView, leafParams)
        val fanParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        fanParams.addRule(ALIGN_PARENT_RIGHT, TRUE)
        mFanView = FanView(context, mProgress, mFanBgColor, mFanBorderColor, mFanTextColor, mFanTextSize, mBorderWidth)
        addView(mFanView, fanParams)
    }
}