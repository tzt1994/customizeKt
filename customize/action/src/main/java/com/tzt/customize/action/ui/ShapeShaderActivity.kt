package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.action.R


/**
 * Description: shader实现的imageview
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class ShapeShaderActivity: BaseActivity() {

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "各种形状图片.BitmapShader"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_shape_shader
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}