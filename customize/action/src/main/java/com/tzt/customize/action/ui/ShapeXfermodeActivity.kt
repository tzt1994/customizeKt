package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivityShapeXfermodeBinding


/**
 * Description: xfermode实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class ShapeXfermodeActivity: BaseActivity<ActivityShapeXfermodeBinding>() {

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "各种形状图片.Xfermode"
    )
    override fun layoutBinding() = ActivityShapeXfermodeBinding.inflate(layoutInflater, null, false)

    override fun initData() {

    }
}