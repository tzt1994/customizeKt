package com.tzt.customize.action.ui

import android.widget.Toast
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.action.R
import kotlinx.android.synthetic.main.activity_scale_view.*


/**
 * Description: 刻度尺
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class MutipleScaleActivity: BaseActivity() {

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "MutipleScaleView.刻度尺"
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_scale_view
    }

    override fun initData() {
        scale1.mUnitValue = 2f

        scale2.setOnValueChangedListener{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}