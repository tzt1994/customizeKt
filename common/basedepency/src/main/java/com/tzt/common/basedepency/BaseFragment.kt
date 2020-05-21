package com.tzt.common.basedepency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
abstract class BaseFragment: Fragment() {
    open lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResID(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindListener()
    }

    /**
     * 布局
     * return 布局ID Int
     */
    abstract fun layoutResID(): Int

    /**
     * 数据初始化
     */
    open fun initData() {}

    /**
     * 监听设置
     */
    open fun bindListener() {}
}