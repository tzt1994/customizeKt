package com.tzt.common.basedepency

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/8
 */
open class BaseActivity : AppCompatActivity() {
    protected lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        Log.v("TTTT", "添加了 ${javaClass.simpleName}")
        ActivityController.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.deleteActivity(this)
    }
}