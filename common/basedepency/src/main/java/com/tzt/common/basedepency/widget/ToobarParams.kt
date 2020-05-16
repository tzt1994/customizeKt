package com.tzt.common.basedepency.widget

import android.graphics.Color
import com.tzt.common.basedepency.dpToPx


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/15
 */
class ToobarParams {
    //标题栏背景颜色
    var bgColor = Color.parseColor("#1E90FF")

    //标题栏高度
    var titleHeight: Int = dpToPx(48).toInt()

    //标题栏左边的内容
    val leftActions = ArrayList<Toobar.TooBarAction>()


    //标题栏标题
    var title: String

    //鼻涕蓝右边内容 可添加多个
    val rightActions =  ArrayList<Toobar.TooBarAction>()

    constructor(vararg leftAc: Toobar.TooBarAction, title: String) {
        leftActions.addAll(leftAc)
        this.title = title
    }

    constructor(title: String, vararg rightAc: Toobar.TooBarAction) {
        this.title = title
        rightActions.addAll(rightAc)
    }

    constructor(
        leftAc: Toobar.TooBarAction,
        title: String,
        right: Toobar.TooBarAction
    ) {
        leftActions.add(leftAc)
        this.title = title
        rightActions.add(right)
    }
}