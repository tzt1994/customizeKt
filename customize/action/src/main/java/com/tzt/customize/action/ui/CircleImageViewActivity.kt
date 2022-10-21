package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivityCircleBinding


/**
 * Description: xfermode实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class CircleImageViewActivity: BaseActivity<ActivityCircleBinding>() {

    override fun getToolbarParams() = ToolbarParams(createFinisIcon(), "圆形图片")

    override fun layoutBinding() = ActivityCircleBinding.inflate(layoutInflater, null, false)

    override fun initData() {

    }
}