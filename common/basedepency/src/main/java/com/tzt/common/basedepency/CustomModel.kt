package com.tzt.common.basedepency

import android.view.View
import androidx.fragment.app.Fragment


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/4/29
 */

data class CustomModel(val title: String, val subTitle: String, val clazz: Class<out BaseActivity>)

data class CanvasDrawModel(val imageRes: Int, val title: String, val layoutRes: Int)

data class BezierModel(val title: String, val fragment: Fragment)

data class PaintModel(val title: String, val fragment: Fragment)

// paint数据类
data class PaintItemModel(val title: String, val view: View? = null)