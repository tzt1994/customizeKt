package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivityEraserBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2/4/21
 */
class EraserActivity: BaseActivity<ActivityEraserBinding>(){
    override fun layoutBinding() = ActivityEraserBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(createFinisIcon(), "橡皮擦除")

    override fun initData() {
        mBinding.apply {
            btnPen.setOnClickListener { evEraser.isPen = true }
            btnEraser.setOnClickListener { evEraser.isPen = false}
        }
    }
}