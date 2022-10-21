package com.tzt.customize.action.ui

import android.widget.Toast
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivityScaleViewBinding


/**
 * Description: 刻度尺
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class MutipleScaleActivity: BaseActivity<ActivityScaleViewBinding>() {

    override fun getToolbarParams() = ToolbarParams(
        createFinisIcon(),
        "MutipleScaleView.刻度尺"
    )

    override fun layoutBinding() = ActivityScaleViewBinding.inflate(layoutInflater, null, false)

    override fun initData() {
        mBinding.scale1.mUnitValue = 2f

        mBinding.scale2.setOnValueChangedListener{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}