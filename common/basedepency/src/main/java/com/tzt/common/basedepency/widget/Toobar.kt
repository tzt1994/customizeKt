package com.tzt.common.basedepency.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/15
 */
class Toobar : LinearLayout, View.OnClickListener{
    var mHeight: Int = 0

    // 标题
    private lateinit var titleTv: TextView
    // 左右布局
    private lateinit var leftLayout: LinearLayout
    private lateinit var rightLayout: LinearLayout

    constructor(context: Context?): this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        orientation = HORIZONTAL

        // 左边布局
        val leftParams = LayoutParams(0, -1)
        leftParams.weight = 1f
        leftLayout = LinearLayout(context)
        addView(leftLayout, leftParams)

        // 标题
        val titleParams = LayoutParams(-2, -1)
        titleTv = TextView(context)
        titleTv.text = "标题"
        titleTv.setTextColor(Color.WHITE)
        titleTv.gravity = Gravity.CENTER
        titleTv.textSize = 20f
        addView(titleTv, titleParams)

        // 右边布局
        val rightParams = LayoutParams(0, -1)
        rightParams.weight = 1f
        rightLayout = LinearLayout(context)
        // 用来占据剩余空间
        val nullParams = LayoutParams(0, -1)
        nullParams.weight = 1f
        rightLayout.addView(LinearLayout(context), nullParams)
        addView(rightLayout, leftParams)
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        titleTv.text = title
    }

    /**
     * 添加左边布局
     */
    fun addLeftAction(actions: ArrayList<TooBarAction>) {
        if (actions.size > 0) {
            for (action in actions) {
                val params = LayoutParams(mHeight, mHeight)
                if (action.getView() != null) {
                    val view = action.getView()
                    view?.tag = action
                    view?.setOnClickListener(this)

                    leftLayout.addView(action.getView(), params)
                }

                if (action.getImageResource() > 0) {
                    val imageView = ImageView(context)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    imageView.setImageResource(action.getImageResource())
                    imageView.setPadding(mHeight / 4, mHeight / 4,mHeight / 4,mHeight / 4)
                    imageView.tag = action
                    imageView.setOnClickListener(this)

                    leftLayout.addView(imageView, params)
                }
            }
        }
    }

    fun addRightAction(actions: ArrayList<TooBarAction>) {
        if (actions.size > 0) {
            for (action in actions) {
                val params = LayoutParams(mHeight, mHeight)
                if (action.getView() != null) {
                    val view = action.getView()
                    view?.tag = action
                    view?.setOnClickListener(this)

                    rightLayout.addView(action.getView(), params)
                }

                if (action.getImageResource() > 0) {
                    val imageView = ImageView(context)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    imageView.setImageResource(action.getImageResource())
                    imageView.setPadding(mHeight / 4, mHeight / 4,mHeight / 4,mHeight / 4)
                    imageView.tag = action
                    imageView.setOnClickListener(this)

                    rightLayout.addView(imageView, params)
                }
            }
        }
    }

    override fun removeAllViews() {
        super.removeAllViews()
        leftLayout.removeAllViews()
        rightLayout.removeAllViews()
    }

    override fun onClick(v: View?) {
        val action = v?.tag as TooBarAction
        action.click(v)
    }

    abstract class TooBarAction{
        open fun getView(): View? { return  null }
        open fun getImageResource(): Int {return  0}
        abstract fun click(view: View)
    }
}