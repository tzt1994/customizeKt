package com.tzt.common.basedepency.widget

import android.graphics.Color
import com.tzt.common.basedepency.dpToPx


/**
 * Description: 标题栏参数
 *
 * @author tangzhentao
 * @since 2020/5/15
 */
class ToolbarParams {

    companion object {
        private val defaultColor = Color.parseColor("#1E90FF")
    }

    //标题栏背景颜色
    var backgroundColor: Int = defaultColor

    //标题栏高度
    var toobarHeight: Int = 0

    //标题栏昨天布局可以添加多个
    val leftActions = ArrayList<Toobar.TooBarAction>()


    //标题栏标题
    var title: String

    //标题栏右边布局 可添加多个
    val rightActions =  ArrayList<Toobar.TooBarAction>()

    /**
     *
     * 添加多个左边可以多次调用ToobarParams.leftActions.add()
     */

    /**
     * 默认一个左边添加多个右边内容
     */
    constructor(left: Toobar.TooBarAction, title: String, vararg rights: Toobar.TooBarAction, bgColor: Int = defaultColor, titleHeight: Int = dpToPx(48).toInt()) {
        this.title = title
        this.backgroundColor = bgColor
        this.toobarHeight = titleHeight
        leftActions.add(left)
        rightActions.addAll(rights)
    }

    /**
     * 默认一个左边添加一个右边
     */
    constructor(left: Toobar.TooBarAction, title: String, right: Toobar.TooBarAction, bgColor: Int = defaultColor, titleHeight: Int = dpToPx(48).toInt()) {
        this.title = title
        this.backgroundColor = bgColor
        this.toobarHeight = titleHeight
        leftActions.add(left)
        rightActions.add(right)
    }

    /**
     * 无左边添加多个右边内容
     */
    constructor(title: String, vararg rights: Toobar.TooBarAction, bgColor: Int = defaultColor, titleHeight: Int = dpToPx(48).toInt()) {
        this.title = title
        this.backgroundColor = bgColor
        this.toobarHeight = titleHeight
        rightActions.addAll(rights)
    }

    /**
     * 无左边添加一个右边
     */
    constructor(title: String, right: Toobar.TooBarAction, bgColor: Int = defaultColor, titleHeight: Int = dpToPx(48).toInt()) {
        this.title = title
        this.backgroundColor = bgColor
        this.toobarHeight = titleHeight
        rightActions.add(right)
    }
}