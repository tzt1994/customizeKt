package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.action.R


/**
 * Description: xfermode实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class CircleImageViewActivity: BaseActivity() {

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "圆形图片"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_circle
    }

    override fun initData() {

    }
}