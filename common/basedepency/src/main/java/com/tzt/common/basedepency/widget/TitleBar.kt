package com.tzt.common.basedepency.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.tzt.common.basedepency.R
import com.tzt.common.basedepency.dpToPx
import kotlinx.android.synthetic.main.title_bar.view.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/14
 */
class TitleBar: LinearLayout {
    private val mLeftViews: ArrayList<ViewInterface> = ArrayList()
    private val mRightViews: ArrayList<ViewInterface> = ArrayList()

    constructor(context: Context?): this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.title_bar, this)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun addLeftView(viewInterface: ViewInterface) {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40).toInt())
        viewInterface.view().setOnClickListener {
            viewInterface.click(it)
        }
        llLeft.addView(viewInterface.view(), params)
    }

    fun addRightView(viewInterface: ViewInterface) {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40).toInt())
        viewInterface.view().setOnClickListener {
            viewInterface.click(it)
        }
        llLeft.addView(viewInterface.view(), params)
    }
}