package com.tzt.customize.action.ui

import android.content.Intent
import android.net.Uri
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToolbarParams
import com.tzt.customize.action.databinding.ActivityHeartBinding


/**
 * Description: 心跳
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class HeartXfermodeActivity: BaseActivity<ActivityHeartBinding>() {

    override fun layoutBinding() = ActivityHeartBinding.inflate(layoutInflater, null, false)

    override fun getToolbarParams(): ToolbarParams {
        return ToolbarParams(
            createFinisIcon(),
            "心跳(Xfermode)",
            createOriginalIcon {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://juejin.im/post/5c6c20556fb9a04a0e2dc490#heading-25"))
                )
            }
        )
    }

    override fun initData() {
        mBinding.hbvHeartBeat.startAnimation()
    }

    override fun bindListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.hbvHeartBeat.stopAnimation()
    }
}