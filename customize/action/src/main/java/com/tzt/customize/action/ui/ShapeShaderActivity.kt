package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivityShapeShaderBinding


/**
 * Description: shader实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class ShapeShaderActivity: BaseActivity<ActivityShapeShaderBinding>() {

    override fun layoutBinding() = ActivityShapeShaderBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "各种形状图片.BitmapShader"
    )

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}