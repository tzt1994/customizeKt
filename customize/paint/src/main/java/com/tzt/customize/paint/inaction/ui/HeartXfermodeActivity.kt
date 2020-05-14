package com.tzt.customize.paint.inaction.ui

import android.os.Bundle
import com.tzt.common.basedepency.BaseActivity
import com.tzt.customize.paint.R
import kotlinx.android.synthetic.main.activity_heart.*


/**
 * Description: 心跳
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class HeartXfermodeActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)

        hbvHeartBeat.startAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        hbvHeartBeat.stopAnimation()
    }
}