package com.tzt.customize.action.ui

import android.widget.Toast
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivitySurfaceViewBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2/4/21
 */
class SurfaceViewActivity: BaseActivity<ActivitySurfaceViewBinding>(){
    override fun layoutBinding() = ActivitySurfaceViewBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "surface view"
    )

    override fun initData() {
        mBinding.btnStart.setOnClickListener { Toast.makeText(this, "测试测试测试", Toast.LENGTH_SHORT).show() }
    }

    override fun onStart() {
        super.onStart()
    }
}