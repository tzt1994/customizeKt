package com.tzt.customize.action.ui

import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.action.R
import kotlinx.android.synthetic.main.activity_heart.*


/**
 * Description: 心跳
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class HeartXfermodeActivity: BaseActivity() {

    override var layoutResID = R.layout.activity_heart

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            title = "心跳(Xfermode)"
        )
    }

    override fun initData() {
        hbvHeartBeat.startAnimation()
    }

    override fun bindListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        hbvHeartBeat.stopAnimation()
    }
}