package com.tzt.common.basedepency

import android.app.Activity
import android.util.Log

/**
 * Description: 页面控制器群
 *
 * @author tangzhentao
 * @since 2020/4/8
 */
object ActivityController {
    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun deleteActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAllActivity() {
        Log.v("TTTT", "长度 ${activities.size}")
        for(activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }

        activities.clear()

        // 杀死进程
//        android.os.Process.killProcess(android.os.Process.myPid())
    }
}