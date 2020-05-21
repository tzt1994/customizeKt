package com.tzt.common.basedepency.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.tzt.common.basedepency.R


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/15
 */
class Toobar : RelativeLayout, View.OnClickListener {
    /**
     * 图片宽度为 3/4的标题高度
     */

    var mHeight: Int = 0
        set(value) {
            field = value
            titleTv.setPadding(mHeight / 4, 0,mHeight / 4,0)
        }

    private var title: String = ""

    private val lefts = ArrayList<TooBarAction>()

    // 标题
    private lateinit var titleTv: TextView
    // 左右布局
    private lateinit var leftLayout: LinearLayout
    private lateinit var rightLayout: RelativeLayout

    constructor(context: Context?): this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {

        // 左边布局
        val leftParams = LayoutParams(-2, -1)
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE)
        leftLayout = LinearLayout(context)
        leftLayout.id = R.id.toobarLeftLayout
        addView(leftLayout, leftParams)

        // 标题
        val titleParams = LayoutParams(-2, -1)
        titleParams.addRule(RIGHT_OF, leftLayout.id)
        titleTv = TextView(context)
        titleTv.text = "标题"
        titleTv.setTextColor(Color.WHITE)
        titleTv.gravity = Gravity.CENTER
        titleTv.textSize = 20f
        addView(titleTv, titleParams)

//        // 右边布局
//        val rightParams = LayoutParams(-2, -1)
//        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE)
//        rightLayout = RelativeLayout(context)
//        addView(rightLayout, leftParams)
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        this.title = title
        titleTv.text = title
    }

    /**
     * 添加左边布局
     */
    fun addLeftAction(actions: ArrayList<TooBarAction>) {
        if (actions.size > 0) {
            lefts.addAll(actions)
            for (action in actions) {

                val params = LayoutParams(mHeight / 4 * 3, mHeight)
                if (action.getImageResource() > 0) {
                    val imageView = ImageView(context)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    imageView.setImageResource(action.getImageResource())
                    imageView.setPadding(mHeight / 4, mHeight / 4,0,mHeight / 4)
                    imageView.tag = action
                    imageView.setOnClickListener(this)

                    leftLayout.addView(imageView, params)
                }
            }
        }
    }

    /**
     * 添加右边布局
     */
    fun addRightAction(actions: ArrayList<TooBarAction>) {
        if (actions.size > 0) {
            val size = mHeight / 4 * 3

            for ( (index, action) in actions.withIndex()) {

                if (action.getImageResource() > 0) {
                    val params = LayoutParams(size, mHeight)
                    // 位于父控件右边
                    params.addRule(ALIGN_PARENT_RIGHT, TRUE)
                    params.rightMargin = index * size
                    val imageView = ImageView(context)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    imageView.setImageResource(action.getImageResource())
                    imageView.setPadding(0, mHeight / 4,mHeight / 4,mHeight / 4)
                    imageView.tag = action
                    imageView.setOnClickListener(this)

                    addView(imageView, params)
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
        open fun getImageResource(): Int {return  0}
        abstract fun click(view: View)
    }
}