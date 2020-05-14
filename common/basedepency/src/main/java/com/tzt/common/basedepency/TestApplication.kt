package com.tzt.common.basedepency

import android.app.Application
import android.content.Context


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/28
 */
class TestApplication: Application() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}